/**
 * Token.java
 *
 * @author Ángel Igareta (angel@igareta.com)
 * @author Miguel Jimenez 
 * @version 1.0
 * @since 21-04-2018
 */

/**
 * Token class.
 */
public class Token implements Comparable<Token> {
	private String tokenID;
	private double frequency;

	public Token(String tokenID) {
		setTokenID(tokenID);
		setFrequency(0.0);
	}

	/**
	 * @return the tokenID
	 */
	public String getTokenID() {
		return tokenID;
	}

	/**
	 * @return the frequency
	 */
	public double getFrequency() {
		return frequency;
	}

	/**
	 * Increment the frequency in 1
	 */
	public void incrementFrequency() {
		setFrequency(getFrequency() + 1.0);
	}

	/**
	 * @param tokenID
	 *          the tokenID to set
	 */
	private void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	/**
	 * @param frequency
	 *          the frequency to set
	 */
	private void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Token anotherToken) {
		return getTokenID().compareTo(anotherToken.getTokenID());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getTokenID();
	}
}
