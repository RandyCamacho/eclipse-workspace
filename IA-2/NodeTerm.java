/**
 * NodeTerm Class
 */
public class NodeTerm extends Node {

    private NodeFact fact;
    private NodeMulop mulop;
    private NodeTerm term;

    /**
     * Initializes NodeFact fact, NodeMulop mulop, and NodeTerm term. 
     * @param fact
     * @param mulop
     * @param term
     */
    public NodeTerm(NodeFact fact, NodeMulop mulop, NodeTerm term) {
	this.fact=fact;
	this.mulop=mulop;
	this.term=term;
    }

    /**
     * Appends NodeTerm
     * @param term
     */
    public void append(NodeTerm term) {
	if (this.term==null) {
	    this.mulop=term.mulop;
	    this.term=term;
	    term.mulop=null;
	} else
	    this.term.append(term);
    }

    /**
     * Evaluates NodeTerm
     * @param env
     * @throws EvalException
     */
    public Double eval(Environment env) throws EvalException {
	return term==null
	    ? fact.eval(env)
	    : mulop.op(term.eval(env),fact.eval(env));
    }

}
