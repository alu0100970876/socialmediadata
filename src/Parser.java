import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Parser.java
 *
 * @author Ángel Igareta (angel@igareta.com)
 * @author Miguel Jimenez 
 * @version 1.0
 * @since 21-04-2018
 */

/**
 * Parser abstract class.
 */
public abstract class Parser {

	/**
	 * Tokenize a line.
	 * 
	 * @param rawLine
	 * @return
	 */
	public static ArrayList<Token> tokenizeLine(String rawLine) {
		rawLine = cleanLine(rawLine);

		String[] rawTokenList = rawLine.split("\\s");
		ArrayList<Token> tokenList = new ArrayList<Token>();
		for (int i = 0; i < rawTokenList.length; ++i) {
			if (!rawTokenList[i].trim().isEmpty()) { // If not empty
				tokenList.add(new Token(rawTokenList[i].trim()));
			}
		}
		return tokenList;
	}

	/**
	 * @param rawLine
	 * @return
	 */
	private static String cleanLine(String rawLine) {
		/** WORDS IN LOWERCASE */
		rawLine = rawLine.toLowerCase();

		/** LINKS REMOVED */
		rawLine = rawLine.replaceAll("http[^\\s]*", " _url_ ");
		rawLine = rawLine.replaceAll("[0-9]+", "_number_");

		/** Separate hasthags and mentions */
		rawLine = rawLine.replaceAll("([^\\s])#", "$1 #");
		rawLine = rawLine.replaceAll("([^\\s])@", "$1 @");

		/** REPLACES */
		rawLine = rawLine.replaceAll("‰Û_", "%");
		rawLine = rawLine.replaceAll("‰ûª", "'");
		rawLine = rawLine.replaceAll(Pattern.quote("ì¢‰ââ‰ã¢"), "'");
		rawLine = rawLine.replaceAll(Pattern.quote("åê"), " ");
		rawLine = rawLine.replaceAll(Pattern.quote("‰û"), " ");
		rawLine = rawLine.replaceAll(Pattern.quote("äå"), " ");
		rawLine = rawLine.replaceAll(Pattern.quote("©"), " ");
		rawLine = rawLine.replaceAll(Pattern.quote("å¨"), " ");
		rawLine = rawLine.replaceAll(Pattern.quote("‰û"), " ");
		rawLine = rawLine.replaceAll(Pattern.quote("ï"), " ");
		rawLine = rawLine.replaceAll(Pattern.quote("å"), " ");
		rawLine = rawLine.replaceAll(Pattern.quote("‰ââ"), " ");
		rawLine = rawLine.replaceAll(Pattern.quote("ò"), " ");
		rawLine = rawLine.replaceAll(Pattern.quote("ó"), " ");
		rawLine = rawLine.replaceAll(Pattern.quote("ì"), " ");

		/** REMOVE ACENTUATION */
		rawLine = rawLine.replaceAll(Pattern.quote("á"), "a");
		rawLine = rawLine.replaceAll(Pattern.quote("è"), "a");

		/** REMOVES */
		rawLine = rawLine.replaceAll(",", " ");
		rawLine = rawLine.replaceAll(";", " ");
		rawLine = rawLine.replaceAll("-", " ");
		rawLine = rawLine.replaceAll("\\+", " ");
		rawLine = rawLine.replaceAll("-", " ");
		rawLine = rawLine.replaceAll("\\*", " ");
		rawLine = rawLine.replaceAll("/", " ");
		rawLine = rawLine.replaceAll("÷", " ");
		rawLine = rawLine.replaceAll("±", " ");
		rawLine = rawLine.replaceAll("\\.", " ");
		rawLine = rawLine.replaceAll("\\?", " ");
		rawLine = rawLine.replaceAll("\\!", " ");
		rawLine = rawLine.replaceAll("\\(", " ");
		rawLine = rawLine.replaceAll("\\)", " ");
		rawLine = rawLine.replaceAll("\\[", " ");
		rawLine = rawLine.replaceAll("\\]", " ");
		rawLine = rawLine.replaceAll(":", " ");
		rawLine = rawLine.replaceAll("\"", " ");

		rawLine = rawLine.replaceAll("'([^s]|s[^\\s])", "$1");

		// TODO - remove 's?
		rawLine = rawLine.replaceAll("'s", "");

		return rawLine;
	}
}
