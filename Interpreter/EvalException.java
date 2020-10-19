/**
 * Evaluates exceptions when accessing 
 * an undefined variable. 
 * 
 */
public class EvalException extends Exception {

    private int pos;
    private String msg;
    
    /**
     * Initialize position and message. 
     * @param pos
     * @param msg
     */
    public EvalException(int pos, String msg) {
	this.pos=pos;
	this.msg=msg;
    }
    
    /**
     * toString method returning the position
     * and message of the evaluated exception. 
     * @return String
     */
    public String toString() {
	return "eval error"
	    +", pos="+pos
	    +", "+msg;
    }

}
