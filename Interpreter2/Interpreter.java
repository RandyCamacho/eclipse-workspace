// This is the main class/method for the interpreter.
// Each command-line argument is a complete program,
// which is scanned, parsed, and evaluated.
// All evaluations share the same environment,
// so they can share variables.

/**
 * The main class for compiling the interpreter on an input program. 
 * @author CS354 Instructor
 */
public class Interpreter {

    public static void main(String[] args) {
	Parser parser=new Parser();
	Environment env=new Environment();
	for (String stmt: args)
	    try {
	    	Node node = parser.parse(stmt);
            Double evaluation;
            System.out.print(node == null ? "" : (evaluation= node.eval(env)) == null ? "" : evaluation + "\n");
	    } catch (SyntaxException e) {
		System.err.println(e);
	    } catch (EvalException e) {
		System.err.println(e);
	    }
    }

}
