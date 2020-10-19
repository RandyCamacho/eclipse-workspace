
/**
 * Make the exception when syntax error occurs.
 *
 */
public class SyntaxException extends Exception {

	private int pos;
	private Token expected;
	private Token found;

	/**
	 * Basic constructor with three parameters(int, Token, Token)
	 * 
	 * @param pos
	 * @param expected
	 * @param found
	 */
	public SyntaxException(int pos, Token expected, Token found) {
		this.pos=pos;
		this.expected=expected;
		this.found=found;
	}

	/**
	 * The message when the syntax errors are showed.
	 */
	public String toString() {
		return "syntax error"
				+", pos="+pos
				+", expected="+expected
				+", found="+found;
	}

}
