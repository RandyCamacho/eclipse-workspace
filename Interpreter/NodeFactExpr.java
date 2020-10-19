/**
 * NodeFactExpr Class 
 */
public class NodeFactExpr extends NodeFact {

    private NodeExpr expr;

    /**
	 * Create the expression with the given name from the 
	 * data file located.
	 * 
	 * @param expr
	 */
    public NodeFactExpr(NodeExpr expr) {
	this.expr=expr;
    }

    /**
	 * Evaluate the fact expression
	 * 
	 */
    public double eval(Environment env) throws EvalException {
	return expr.eval(env);
    }

}
