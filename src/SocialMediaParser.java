import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * SocialMediaParser.java
 *
 * @author Ángel Igareta
 * @author Miguel Jimenez 
 * @version 1.0
 * @since 21-04-2018
 */

/**
 * Parser designed to clean a file and calculate it's vocabulary.
 */
public class SocialMediaParser {

	private static final String VOCABULARY_FILENAME = "vocabulary.txt";
	private static final String CORPUS_ACTION_FILENAME = "corpus/corpus_action.txt";
	private static final String CORPUS_DIALOG_FILENAME = "corpus/corpus_dialog.txt";
	private static final String CORPUS_INFORMATION_FILENAME = "corpus/corpus_information.txt";
	private static final String FILE_TO_CLASSIFY = "general_corpus.csv";

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length != 1) {
			throw new IllegalArgumentException("You must specify the input txt");
		}

		// Read general corpus and tokenize it.
		BufferedReader reader = new BufferedReader(new FileReader(args[0]));
		int totalNumberOfWords = 0;
		TreeSet<Token> tokenList = new TreeSet<Token>();
		while (reader.ready()) {
			ArrayList<Token> lineTokenized = Parser.tokenizeLine(reader.readLine());
			tokenList.addAll(lineTokenized);
			totalNumberOfWords += lineTokenized.size();
		}
		reader.close();

		// Write vocabulary including the words in the corpus.
		BufferedWriter vocabularyWriter = new BufferedWriter(new FileWriter(VOCABULARY_FILENAME));
		for (Token token : tokenList) {
			vocabularyWriter.write(token.toString() + System.lineSeparator());
		}
		vocabularyWriter.close();

		Corpus actionCorpus = new Corpus(CORPUS_ACTION_FILENAME, tokenList, totalNumberOfWords);
		Corpus dialogCorpus = new Corpus(CORPUS_DIALOG_FILENAME, tokenList, totalNumberOfWords);
		Corpus informationCorpus = new Corpus(CORPUS_INFORMATION_FILENAME, tokenList, totalNumberOfWords);

		actionCorpus.writeCorpus(CORPUS_ACTION_FILENAME);
		dialogCorpus.writeCorpus(CORPUS_DIALOG_FILENAME);
		informationCorpus.writeCorpus(CORPUS_INFORMATION_FILENAME);

		classify(actionCorpus, dialogCorpus, informationCorpus);
	}

	/**
	 * @param informationCorpus
	 * @param dialogCorpus
	 * @param actionCorpus
	 * @throws IOException
	 * 
	 */
	private static void classify(Corpus actionCorpus, Corpus dialogCorpus, Corpus informationCorpus) throws IOException {
		Classifier classifier = new Classifier(actionCorpus, dialogCorpus, informationCorpus);

		// Test Classifier
		BufferedReader solutionReader = new BufferedReader(new FileReader(FILE_TO_CLASSIFY));
		boolean verbose = false;
		int correctClassified = 0;
		int totalWords = 0;
		while (solutionReader.ready()) {
			String rawLine = solutionReader.readLine();
			String correctCategory = rawLine.split(",")[0].trim();
			String lineToClassify = rawLine.split(",")[1].trim();

			String classifiedCategory = classifier.classifyLine(lineToClassify, verbose);
			if (classifiedCategory.equals(correctCategory)) {
				correctClassified++;
			}
			totalWords++;
		}
		solutionReader.close();

		double correctPercentage = (double) correctClassified / (double) totalWords;
		System.out.println("Evaluation percentage: " + String.format("%,.2f", correctPercentage * 100) + "%");
	}
}
