/**
 * NodeFactNum Class
 */
public class NodeFactNum extends NodeFact {

    private String num;

    /**
     * Initialize String num. 
     * @param num
     */
    public NodeFactNum(String num) {
	this.num=num;
    }

    /**
     * Evaluates NodeFact num
     * @param env
     * @throws EvalException
     * @return the integer value represented by the num in decimal double.
     */
    public double eval(Environment env) throws EvalException {
	return Double.parseDouble(num);
    }

}
