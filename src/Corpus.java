import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Corpus.java
 *
 * @author Ángel Igareta (angel@igareta.com)
 * @author Miguel Jimenez 
 * @version 1.0
 * @since 21-04-2018
 */

/**
 * Corpus
 */
public class Corpus {

	private static final Token UNKNOWN_TOKEN = new Token("_unk_");
	private int numberOfWordsInVocabulary;
	private int numberOfWordsInCorpus;
	private double corpusProbability;
	private TreeMap<Token, Double> tokenFrequencyMap; // Frequency of token in this corpus

	/**
	 * @param corpusActionFilename
	 * @param tokenList
	 * @throws IOException
	 */
	public Corpus(String corpusFilename, TreeSet<Token> vocabularyTokenList, int totalNumberOfWords) throws IOException {
		setNumberOfWordsInCorpus(0);
		setNumberOfWordsInVocabulary(vocabularyTokenList.size());
		initializeTokenFrequencyMap(vocabularyTokenList);

		// Read probability
		BufferedReader corpusReader = new BufferedReader(new FileReader(corpusFilename));
		while (corpusReader.ready()) {
			ArrayList<Token> tokenizedLine = Parser.tokenizeLine(corpusReader.readLine());
			for (Token token : tokenizedLine) {
				if (!this.tokenFrequencyMap.containsKey(token)) {
					System.err.println("Token doesn't exist in vocabulary: " + token);
				}
				else {
					this.tokenFrequencyMap.put(token, tokenFrequencyMap.get(token) + 1.0);
					this.numberOfWordsInCorpus++;
				}
			}
		}
		setCorpusProbability(Math.log((double) getNumberOfWordsInCorpus() / (double) totalNumberOfWords));
		corpusReader.close();
	}

	/**
	 * @param vocabularyTokenList
	 */
	private void initializeTokenFrequencyMap(TreeSet<Token> vocabularyTokenList) {
		setTokenFrequencyMap(new TreeMap<Token, Double>());
		for (Token token : vocabularyTokenList) {
			this.tokenFrequencyMap.put(token, 0.0);
		}
		this.tokenFrequencyMap.put(UNKNOWN_TOKEN, 0.0);
	}

	/**
	 * @throws IOException
	 * 
	 */
	public void writeCorpus(String corpusFileName) throws IOException {
		BufferedWriter corpusWriter = new BufferedWriter(new FileWriter(corpusFileName.split("\\.")[0] + "_learning.txt"));
		corpusWriter.write(this.toString());
		corpusWriter.close();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String corpusString = "";
		corpusString += "Number of words in the corpus: " + getNumberOfWordsInCorpus() + System.lineSeparator();
		corpusString += "Number of words in the vocabulary: " + getNumberOfWordsInVocabulary() + System.lineSeparator();
		corpusString += "Probability of corpus: " + getCorpusProbability() + System.lineSeparator();

		for (Map.Entry<Token, Double> entry : getTokenFrequencyMap().entrySet()) {
			corpusString += "Word: " + entry.getKey() + " Frequency: " + entry.getValue() + " LogProb: "
					+ getProbabilityOf(entry.getKey()) + System.lineSeparator();
		}

		return corpusString;
	}

	/**
	 * @param key
	 * @return
	 */
	public double getProbabilityOf(Token token) {
		if (getTokenFrequencyMap().containsKey(token)) { // If word in the vocabulary
			double denominator = getNumberOfWordsInCorpus() + getNumberOfWordsInVocabulary();
			double probabilityWithSmoothing = (this.tokenFrequencyMap.get(token) + 1.0) / denominator;
			return Math.log(probabilityWithSmoothing);
		}
		else { // Else unknown
			return getProbabilityOf(UNKNOWN_TOKEN);
		}
	}

	/**
	 * @return the tokenFrequencyMap
	 */
	private TreeMap<Token, Double> getTokenFrequencyMap() {
		return tokenFrequencyMap;
	}

	/**
	 * @param tokenFrequencyMap
	 *          the tokenFrequencyMap to set
	 */
	private void setTokenFrequencyMap(TreeMap<Token, Double> tokenFrequencyMap) {
		this.tokenFrequencyMap = tokenFrequencyMap;
	}

	/**
	 * @return the numberOfWordsInVocabulary
	 */
	private int getNumberOfWordsInVocabulary() {
		return numberOfWordsInVocabulary;
	}

	/**
	 * @return the numberOfWordsInCorpus
	 */
	private int getNumberOfWordsInCorpus() {
		return numberOfWordsInCorpus;
	}

	/**
	 * @param numberOfWordsInVocabulary
	 *          the numberOfWordsInVocabulary to set
	 */
	private void setNumberOfWordsInVocabulary(int numberOfWordsInVocabulary) {
		this.numberOfWordsInVocabulary = numberOfWordsInVocabulary;
	}

	/**
	 * @param numberOfWordsInCorpus
	 *          the numberOfWordsInCorpus to set
	 */
	private void setNumberOfWordsInCorpus(int numberOfWordsInCorpus) {
		this.numberOfWordsInCorpus = numberOfWordsInCorpus;
	}

	/**
	 * @return the corpusProbability
	 */
	public double getCorpusProbability() {
		return corpusProbability;
	}

	/**
	 * @param corpusProbability the corpusProbability to set
	 */
	public void setCorpusProbability(double corpusProbability) {
		this.corpusProbability = corpusProbability;
	}

}
