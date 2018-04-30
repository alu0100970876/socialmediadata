import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.omg.SendingContext.RunTime;

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

	private static final String UNKNOWN_SYMBOL = "<unk>";
	private static final int K = 1;
	private static String VOCABULARY_FILENAME = "vocabulary.txt";
	private static String[] CORPUS_FILENAMES = new String[] { "corpus/corpus_action.txt", "corpus/corpus_dialog.txt",
			"corpus/corpus_information.txt" };

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length != 1) {
			throw new IllegalArgumentException("You must specify the input txt");
		}

		final String CLEANED_FILE_NAME = "cleaned_" + args[0];
		String cleanedFile = cleanFile(args[0]);
		BufferedWriter writer = new BufferedWriter(new FileWriter(CLEANED_FILE_NAME));
		writer.write(cleanedFile);
		writer.close();

		// Execute vocabulary generator
		// Runtime.getRuntime().exec("rm " + VOCABULARY_FILENAME);
		// Process p = Runtime.getRuntime()
		// .exec("python src/VocabularyGenerator.py " + CLEANED_FILE_NAME + " " +
		// VOCABULARY_FILENAME);
		// p.waitFor();

		// Probability estimate
		BufferedReader vocabularyReader = new BufferedReader(new FileReader(VOCABULARY_FILENAME));
		TreeMap<String, Double> vocabularyFrequencyMap = new TreeMap<String, Double>();
		while (vocabularyReader.ready()) {
			vocabularyFrequencyMap.put(vocabularyReader.readLine().trim(), 0.0);
		}
		vocabularyReader.close();

		for (String corpusFileName : CORPUS_FILENAMES) {
			setCorpusFrequency(corpusFileName, vocabularyFrequencyMap);
		}

		// for (Map.Entry<String, Double> entry : vocabularyFrequencyMap.entrySet()) {
		// System.out.println(entry);
		// }
	}

	/**
	 * @param corupusFileName
	 * @param vocabularyFrequencyMap
	 * @throws IOException
	 */
	private static void setCorpusFrequency(String corpusFileName, TreeMap<String, Double> vocabularyFrequencyMap)
			throws IOException {
		BufferedReader corpusReader = new BufferedReader(new FileReader(corpusFileName));

		int wordCounter = 0;
		while (corpusReader.ready()) {
			String[] wordsInLine = corpusReader.readLine().split("\\s");
			for (String word : wordsInLine) {
				if (vocabularyFrequencyMap.containsKey(word)) {
					vocabularyFrequencyMap.put(word, vocabularyFrequencyMap.get(word) + 1);
					wordCounter++;
				}
			}
		}
		corpusReader.close();

		int minorThanK = 0;
		BufferedWriter corpusWriter = new BufferedWriter(new FileWriter(corpusFileName.split("\\.")[0] + "_learning.txt"));
		corpusWriter.write("Number of words in the corpus: " + wordCounter + System.lineSeparator());
		for (Map.Entry<String, Double> entry : vocabularyFrequencyMap.entrySet()) {
			if (entry.getValue() >= K) {
				corpusWriter.write(
						"Word: " + entry.getKey() + " Frequency: " + entry.getValue() + " LogProb: TODO" + System.lineSeparator());
			}
			else {
				minorThanK++;
			}
		}
		corpusWriter
				.write("Word: " + UNKNOWN_SYMBOL + " Frequency: " + minorThanK + " LogProb: TODO" + System.lineSeparator());
		corpusWriter.close();
	}

	/**
	 * Clean a file passed by argument.
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	private static String cleanFile(String inputFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		String cleanedFile = "";

		while (reader.ready()) {
			cleanedFile += cleanLine(reader.readLine()) + System.lineSeparator();
		}

		reader.close();
		return cleanedFile;
	}

	/**
	 * Clean the line according to the characters we can find in the text.
	 * 
	 * @param readLine
	 *          Line to clean
	 * @return Cleaned Line
	 */
	private static String cleanLine(String readLine) {
		/** LINKS REMOVED */
		readLine = readLine.replaceAll("http[^\\s]*", " ");

		/** REPLACES */
		readLine = readLine.replaceAll(",", " ");
		readLine = readLine.replaceAll(";", " ");
		readLine = readLine.replaceAll("-", " ");
		readLine = readLine.replaceAll("\\.", " ");
		readLine = readLine.replaceAll("\\?", " ");
		readLine = readLine.replaceAll("\\!", " ");
		readLine = readLine.replaceAll(":", " ");
		readLine = readLine.replaceAll("^ ", "");
		readLine = readLine.replaceAll("‰Ûª", "");
		readLine = readLine.replaceAll("‰Û_", "%");

		readLine = readLine.replaceAll("&amp", "&");
		readLine = readLine.replaceAll("&gt", ">");
		readLine = readLine.replaceAll("&lt", "<");
		readLine = readLine.replaceAll("w/", "with");
		readLine = readLine.replaceAll("\\s{2}\\s*", " ");
		readLine = readLine.replaceAll("Ì©", "e");

		/** REMOVES */
		readLine = readLine.replaceAll("\"", "");
		readLine = readLine.replaceAll("'", "");
		readLine = readLine.replaceAll("'s", "");
		readLine = readLine.replaceAll("å£", "");
		readLine = readLine.replaceAll("ì_", "");
		readLine = readLine.replaceAll("òa", "");
		readLine = readLine.replaceAll("ì¢", "");
		readLine = readLine.replaceAll("‰ââ", "");
		readLine = readLine.replaceAll("‰ÛÒ", "");
		readLine = readLine.replaceAll("‰Û÷", "");
		readLine = readLine.replaceAll("Ì©", "");
		readLine = readLine.replaceAll("‰Û", "");
		readLine = readLine.replaceAll("‰ÛÏ", "");
		readLine = readLine.replaceAll("åÊ", "");

		return readLine;
	}
}
