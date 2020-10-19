/**
 * Make the exception when syntax error occurs.
 */
public class SyntaxException extends Exception {

    private int pos;
    private Token expected;
    private Token found;

    /**
     * Initializes SyntaxException int pos, Token Expected, Token Found. 
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
     * toString for syntax error
     * @return string of syntax error
     */
    public String toString() {
	return "syntax error"
	    +", pos="+pos
	    +", expected="+expected
	    +", found="+found;
    }

}
