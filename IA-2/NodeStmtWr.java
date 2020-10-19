/**
 * write statement class
 * @author randy camacho
 */
public class NodeStmtWr extends NodeStmt {
    private NodeExpr expr;

    public NodeStmtWr(NodeExpr expr)
    {
        this.expr = expr;
    }
    
    /**
     * Evaluates write
     *
     */
    @Override
    public Double eval(Environment env) throws EvalException
    {
        System.out.println(expr.eval(env));
        return null;
    }
}