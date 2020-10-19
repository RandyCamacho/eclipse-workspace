/**
 * A node for if then statements
 * @author randy camacho
 */
public class NodeStmtIfThen extends NodeStmt {
    private BoolExpr expr;
    private NodeStmt ifThenStmt;
    
    public NodeStmtIfThen(BoolExpr bexpr, NodeStmt ifThenStmt)
    {
        this.expr = bexpr;
        this.ifThenStmt = ifThenStmt;
    }
    
    /**
     * Evaluates the if then statement
     * @param env
     * @return
     * @throws EvalException 
     */
    public Double eval(Environment env) throws EvalException
    {
        if (expr.eval(env))
        {
            return ifThenStmt.eval(env);
        }
        return (Double) null;
    }
}
