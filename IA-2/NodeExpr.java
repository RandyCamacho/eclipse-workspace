/**
 * NodeExpr Class
 */
public class NodeExpr extends Node {

    private NodeTerm term;
    private NodeAddop addop;
    private NodeExpr expr;

    /**
     * Initializes NodeTerm, NodeAddop, and NodeExpr. 
     * @param NodeTerm term
     * @param NodeAddop addop
     * @param NodeExpr expr
     */
    public NodeExpr(NodeTerm term, NodeAddop addop, NodeExpr expr) {
	this.term=term;
	this.addop=addop;
	this.expr=expr;
    }

    /**
     * Appends expressions.
     * @param NodeExpr expr
     */
    public void append(NodeExpr expr) {
	if (this.expr==null) {
	    this.addop=expr.addop;
	    this.expr=expr;
	    expr.addop=null;
	} else
	    this.expr.append(expr);
    }

    /**
     * NodeExpr evaluations. 
     * @param env
     * @throws EvalException
     * @return double 
     */
    public Double eval(Environment env) throws EvalException {
	return expr==null
	    ? term.eval(env)
	    : addop.op(expr.eval(env),term.eval(env));
    }

}
