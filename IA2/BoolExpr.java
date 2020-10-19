/**
 * A node for boolean expressions
 *
 */
public class BoolExpr {
    private final String relop;
    private final NodeExpr left;
    private final NodeExpr right;
    
    public BoolExpr(NodeExpr leftSide, String relop, NodeExpr rightSide)
    {
        this.relop = relop;
        this.left = leftSide;
        this.right = rightSide;
    }
    
    /**
     * Evaluates the boolean expression 
     * @param env
     * @return - a boolean value
     * @throws EvalException 
     */
    public boolean eval(Environment env) throws EvalException
    {
        double rightHandVal = right.eval(env);
        double leftHandVal = left.eval(env);
        switch (relop)
        {
            case "==":
                return leftHandVal == rightHandVal;
            case "<>":
                return leftHandVal != rightHandVal;
            case "<=":
                return leftHandVal <= rightHandVal;
            case ">=":
                return leftHandVal >= rightHandVal;
            case "<":
                return leftHandVal <  rightHandVal;
            case ">":
                return leftHandVal >  rightHandVal;
            default:
                throw new EvalException(0, "Relational operator not recognized");
        }
    }
}
