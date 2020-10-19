/**
 * Add operator for Node. 
 */
public class NodeAddop extends Node {

    private String addop;
    
    /**
	 * A basic constructor
	 * 
	 * @param pos
	 * @param addop
	 */
    public NodeAddop(int pos, String addop) {
	this.pos=pos;
	this.addop=addop;
    }

    /**
     * Sets up the add operation for addition and subtraction
     * for two double numbers. 
     * @param o1
     * @param o2
     * @throws EvalException
     * @return either o1+o2 or o1-o2
     */
    public double op(double o1, double o2) throws EvalException {
	if (addop.equals("+"))
	    return o1+o2;
	if (addop.equals("-"))
	    return o1-o2;
	throw new EvalException(pos,"bogus addop: "+addop);
    }
}
