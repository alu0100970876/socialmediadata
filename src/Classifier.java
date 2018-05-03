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

	public String classifyLine(String lineToClassify) {
		ArrayList<Token> tokensToClassify = Parser.tokenizeLine(lineToClassify);
		
		// CAUTION! -> Putting -1 and Math.abs
		double actionCorpusProbability = -1.0;
		double dialogCorpusProbability = -1.0;
		double informationCorpusProbability = -1.0;

		for (Token token : tokensToClassify) {
			actionCorpusProbability *= Math.abs(actionCorpus.getProbabilityOf(token));
			dialogCorpusProbability *= Math.abs(dialogCorpus.getProbabilityOf(token));
			informationCorpusProbability *= Math.abs(informationCorpus.getProbabilityOf(token));
		}
		System.out.println("Action Probability: " + actionCorpusProbability);
		System.out.println("Dialog Probability: " + dialogCorpusProbability);
		System.out.println("Information Probability: " + informationCorpusProbability);
		

		double maximumProbability = Math.max(actionCorpusProbability, dialogCorpusProbability);
		maximumProbability = Math.max(maximumProbability, informationCorpusProbability);		

		if (maximumProbability == actionCorpusProbability) {
			return "ACTION";
		}
		else if (maximumProbability == dialogCorpusProbability) {
			return "DIALOG";
		}
		else if (maximumProbability == informationCorpusProbability) {
			return "INFORMATION";
		}
		else {
			throw new IllegalArgumentException("Error classifying.");
		}
	}

}
