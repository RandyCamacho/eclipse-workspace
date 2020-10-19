/**
 * NodeFactExpr Class 
 */
public class NodeFactExpr extends NodeFact {

    private NodeExpr expr;
    private NodeUnaryMinus unary;

    /**
	 * Create the expression with the given name from the 
	 * data file located.
	 * 
	 * @param expr
	 */
    public NodeFactExpr(NodeExpr expr, NodeUnaryMinus unary)
	{
		this.expr=expr;
		this.unary=unary;
	}

    /**
	 * Evaluate the fact expression
	 * 
	 */
    public Double eval(Environment env) throws EvalException {
		return unary == null? expr.eval(env) : -1*expr.eval(env);
    }

}
