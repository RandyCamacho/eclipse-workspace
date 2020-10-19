
/**
 * A node for the fact containing an identifier
 *
 */
public class NodeFactId extends NodeFact 
{
	private String id;
	private NodeUnaryMinus unary;

	/**
     * Initialize Node pos and String id. 
     * @param pos
     * @param id
     */
	public NodeFactId(int pos, String id, NodeUnaryMinus unary)
	{
		this.pos = pos;
		this.id = id;
		this.unary=unary;
	}

	/**
     * Evaluates FactId
     * @param env
     * @return double
     * @throws EvalException
     */
	public double eval(Environment env) throws EvalException {
		return unary == null ? env.get(pos,id) : -1*env.get(pos,id);
	    }
}
