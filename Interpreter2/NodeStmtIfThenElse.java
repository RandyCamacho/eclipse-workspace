/**
 * A node for if then else statements
 * @author randy camacho
 */
public class NodeStmtIfThenElse extends NodeStmt {
    private BoolExpr expr;
    private NodeStmt ifThenStmt;
    private NodeStmt elseStmt;
    
    public NodeStmtIfThenElse(BoolExpr bexpr, NodeStmt ifThenStmt, NodeStmt elseStmt)
    {
        this.expr = bexpr;
        this.ifThenStmt = ifThenStmt;
        this.elseStmt = elseStmt;
    }
    
    /**
     * Evaluates the if then else statement
     */
    public double eval(Environment env) throws EvalException
    {
        if (expr.eval(env))
        {
            return ifThenStmt.eval(env);
        } 
        else
        {
            return elseStmt.eval(env);
        }
    }
}
