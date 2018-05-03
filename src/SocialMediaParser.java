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
		TreeSet<Token> tokenList = new TreeSet<Token>();
		while (reader.ready()) {
			ArrayList<Token> lineTokenized = Parser.tokenizeLine(reader.readLine());
			tokenList.addAll(lineTokenized);
		}
		reader.close();

		// Write vocabulary including the words in the corpus.
		BufferedWriter vocabularyWriter = new BufferedWriter(new FileWriter(VOCABULARY_FILENAME));
		for (Token token : tokenList) {
			vocabularyWriter.write(token.toString() + System.lineSeparator());
		}
		vocabularyWriter.close();

		Corpus actionCorpus = new Corpus(CORPUS_ACTION_FILENAME, tokenList);
		Corpus dialogCorpus = new Corpus(CORPUS_DIALOG_FILENAME, tokenList);
		Corpus informationCorpus = new Corpus(CORPUS_INFORMATION_FILENAME, tokenList);

		actionCorpus.writeCorpus(CORPUS_ACTION_FILENAME);
		dialogCorpus.writeCorpus(CORPUS_DIALOG_FILENAME);
		informationCorpus.writeCorpus(CORPUS_INFORMATION_FILENAME);
		
		// TODO Falta * P(I) -> // P(I|W) -> P(w1|I) x .. P(w2|I) x P(I) # Pero en logaritmos
		Classifier classifier = new Classifier(actionCorpus, dialogCorpus, informationCorpus);
		System.out.println(classifier.classifyLine("Nice photo, thanks for giving back! RT @Kentoro47 @ChildrensAidNYC #CitiVolunteers #wagonroadcamp http://t.co/XypKiZk8o2"));		
	}
}
