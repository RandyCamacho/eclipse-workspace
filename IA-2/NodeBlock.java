
import java.util.LinkedList;
import java.util.List;

/**
 * A node that represents a block 
 * @author randy camacho
 */
class NodeBlock extends NodeStmt {
    List<NodeStmt> stmt = new LinkedList<NodeStmt>();
    
    public NodeBlock(List<NodeStmt> stmt)
    {
        this.stmt = stmt;
    }
    
    /**
     * Evaluates the statements sequentially
     * @param env - the environment which contains the variables being operated on
     * @throws EvalException 
     */
    public Double eval(Environment env) throws EvalException
    {
        for (NodeStmt nodeStmt : stmt)
        {
            nodeStmt.eval(env);
        }
        return null;
    }
}
