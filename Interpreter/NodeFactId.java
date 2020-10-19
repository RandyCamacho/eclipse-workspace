
/**
 * A node for the fact containing an identifier
 *
 */
public class NodeFactId extends NodeFact 
{
	private String id;

	/**
     * Initialize Node pos and String id. 
     * @param pos
     * @param id
     */
	public NodeFactId(int pos, String id) 
	{
		this.pos=pos;
		this.id=id;
	}

	/**
     * Evaluates FactId
     * @param env
     * @return double
     * @throws EvalException
     */
	public double eval(Environment env) throws EvalException 
	{
		return env.get(pos,id);
	}
}
