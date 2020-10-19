/**
 * A node for a while statement
 * @author randy camacho
 */
public class NodeStmtWhile extends NodeStmt {
    private BoolExpr boolExpr;
    private NodeStmt stmt;

    public NodeStmtWhile(BoolExpr boolExpr, NodeStmt stmt)
    {
        this.boolExpr = boolExpr;
        this.stmt = stmt;
    }
    
    /**
     * Evaluates while
     */
    public double eval(Environment env) throws EvalException
    {
        while (boolExpr.eval(env))
        {
            stmt.eval(env);
        }
        return (Double) null;
    }
}
