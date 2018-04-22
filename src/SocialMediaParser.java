import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException("You must specify the input txt");
		}

		String cleanedFile = cleanFile(args[0]);

		BufferedWriter writer = new BufferedWriter(new FileWriter("cleaned_"+ args[0]));
		writer.write(cleanedFile);

		writer.close();
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
