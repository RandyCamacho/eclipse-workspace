/**
 * UnaryMinus Class. 
 */
public class NodeFactUnaryMinus extends NodeFact{
	
	NodeExpr expr;

    /**
     * Initialize
     * 
     */
	public NodeFactUnaryMinus(int pos, NodeExpr expr) {
		this.pos = pos;
		this.expr = expr;
	}

    /**
     * Evaluate NodeFactUnaryMinus
     * 
     */
	public double eval(Environment env) throws EvalException {
		
		return -expr.eval(env);
	}

}
