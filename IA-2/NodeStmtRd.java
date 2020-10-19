
import java.util.Scanner;

/**
 * A node for a read statement
 * @author randy camacho
 */
public class NodeStmtRd extends NodeStmt {
    private String id;

    public NodeStmtRd(String id)
    {
        this.id = id;
    }
    
    /**
     * Evaluate rd
     */
    @Override
    public Double eval(Environment env) throws EvalException
    {
        Scanner scanner = new Scanner(System.in);
        Double value = null;
        while (value == null)
        {
            try
            {
                System.out.println(String.format("Enter value to store in %s: ", id));
                value = Double.parseDouble(scanner.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.out.println("The value you entered could not be parsed as a double, please try again");
            }
        }
        return env.put(id, value);
    }
}
