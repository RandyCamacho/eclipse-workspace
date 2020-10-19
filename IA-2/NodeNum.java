/**
 * A node for storing numbers
 * @author randy camacho
 */
public class NodeNum extends Node
{
	private String digit;
	NodeUnaryMinus unary;
	
	public NodeNum(String digit)
	{
		this.digit=digit;
	}
	
	/**
	 * Evaluates the nodeNum 
	 */
	@Override
	public Double eval(Environment env) throws EvalException
	{
		return Double.parseDouble(unary == null ? digit : "-"+digit);
	}

	/**
	 * sets unary minus if needed
	 */
	public void setUnary(NodeUnaryMinus unary)
	{
		this.unary=unary;
	}
}
