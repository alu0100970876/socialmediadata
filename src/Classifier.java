import java.util.ArrayList;

/**
 * Classifier.java
 *
 * @author Ángel Igareta (angel@igareta.com)
 * @version 1.0
 * @since 02-05-2018
 */

/**
 * [DESCRIPTION]
 */
public class Classifier {

	private Corpus actionCorpus;
	private Corpus dialogCorpus;
	private Corpus informationCorpus;

	/**
	 * @param actionCorpus
	 * @param dialogCorpus
	 * @param informationCorpus
	 */
	public Classifier(Corpus actionCorpus, Corpus dialogCorpus, Corpus informationCorpus) {
		this.actionCorpus = actionCorpus;
		this.dialogCorpus = dialogCorpus;
		this.informationCorpus = informationCorpus;
	}

	public String classifyLine(String lineToClassify, boolean verbose) {
		ArrayList<Token> tokensToClassify = Parser.tokenizeLine(lineToClassify);
		
		// CAUTION! -> Putting -1 and Math.abs
		double actionCorpusProbability = actionCorpus.getCorpusProbability();
		double dialogCorpusProbability = dialogCorpus.getCorpusProbability();
		double informationCorpusProbability = informationCorpus.getCorpusProbability();

		for (Token token : tokensToClassify) {
			actionCorpusProbability += actionCorpus.getProbabilityOf(token);
			dialogCorpusProbability += dialogCorpus.getProbabilityOf(token);
			informationCorpusProbability += informationCorpus.getProbabilityOf(token);
		}
		if (verbose) {
			System.out.println("Action Probability: " + actionCorpusProbability);
			System.out.println("Dialog Probability: " + dialogCorpusProbability);
			System.out.println("Information Probability: " + informationCorpusProbability);			
		}

		double maximumProbability = Math.max(actionCorpusProbability, dialogCorpusProbability);
		maximumProbability = Math.max(maximumProbability, informationCorpusProbability);		

		if (maximumProbability == actionCorpusProbability) {
			return "A";
		}
		else if (maximumProbability == dialogCorpusProbability) {
			return "D";
		}
		else if (maximumProbability == informationCorpusProbability) {
			return "I";
		}
		else {
			throw new IllegalArgumentException("Error classifying.");
		}
	}

}
