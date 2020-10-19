/**
 * Unary Minus class 
 * @author randy camacho
 */
public class NodeUnaryMinus extends Node
{
	private String unaryMinus;

	public NodeUnaryMinus(String unaryMinus)
	{
		this.unaryMinus = unaryMinus;
	}

	@Override
	public double eval(Environment env) throws EvalException
	{
		return env.put(unaryMinus, pos);
	}
}
