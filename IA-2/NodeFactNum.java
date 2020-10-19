/**
 * NodeFactNum Class
 */
public class NodeFactNum extends NodeFact {

    private NodeNum num;

    /**
     * Initialize String num. 
     * @param num
     */
    public NodeFactNum(NodeNum num) {
	this.num=num;
    }

    /**
     * Evaluates NodeFact num
     * @param env
     * @throws EvalException
     * @return the integer value represented by the num in decimal double.
     */
    public Double eval(Environment env) throws EvalException
	{
		return num.eval(env);
	}

}
