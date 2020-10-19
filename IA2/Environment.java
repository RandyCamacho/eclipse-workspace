
import java.util.HashMap;
import java.util.Map;

//This class provides a stubbed-out environment.
//You are expected to implement the methods.
//Accessing an undefined variable should throw an exception.

//Hint!
//Use the Java API to implement your Environment.
//Browse:
//https://docs.oracle.com/javase/tutorial/tutorialLearningPaths.html
//Read about Collections.
//Focus on the Map interface and HashMap implementation.
//Also:
//https://www.tutorialspoint.com/java/java_map_interface.htm
//http://www.javatpoint.com/java-map
//and elsewhere.

/**
* Is used as an environment for all scanned, parsed,
* and evaluated variables.
* @author Randy Camacho
*
*/
public class Environment {
    private Map<String, Double> hm = new HashMap<>();
    /**
	 * Put a variable into the environment
	 * 
	 * @param var
	 * @param val
	 * 
	 */
    public double put(String var, double val) 
    {
        Double prevVal = hm.put(var, val);
        return prevVal == null ? 0 : prevVal;
    }
    /**
	 * Get a variable from the environment
	 *  
	 * @param pos - the position of the scanner/parser where the variable should be
	 * @param var - variable
	 * @return hm.get(var) 
	 * @throws EvalException
	 */
    public double get(int pos, String var) throws EvalException 
    {
        return hm.get(var);
    }

}
