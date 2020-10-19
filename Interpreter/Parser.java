// This class is a recursive-descent parser,
// modeled after the programming language's grammar.
// It constructs and has-a Scanner for the program
// being parsed.
/**
 * @author Randy Camacho
 * @author CS354 Instructors
 */

import java.util.*;

public class Parser {

    private Scanner scanner;
    
    /**
     * Compare tokens for matching values. 
     * @param s - A string representing token. 
     * @throws SyntaxException
     */
    private void match(String s) throws SyntaxException {
	scanner.match(new Token(s));
    }

    /**
     * Return current token from scanner. 
     * @throws SyntaxException
     */
    private Token curr() throws SyntaxException {
	return scanner.curr();
    }

    /**
     * Return scanner position for the next character. 
     * return position
     * @return position - position of the scanner
     */
    private int pos() {
	return scanner.pos();
    }

    /**
     * If current token matches * or /, then match and 
     * return NodeMulop of the position and token. 
     * @throws SyntaxException
     */
    private NodeMulop parseMulop() throws SyntaxException {
	if (curr().equals(new Token("*"))) {
	    match("*");
	    return new NodeMulop(pos(),"*");
	}
	if (curr().equals(new Token("/"))) {
	    match("/");
	    return new NodeMulop(pos(),"/");
	}
	return null;
    }

    /**
     * Parses the Add operations, if matching, then creates
     * a new NodeAddop with position and operator. 
     * @throws SyntaxException
     */
    private NodeAddop parseAddop() throws SyntaxException {
	if (curr().equals(new Token("+"))) {
	    match("+");
	    return new NodeAddop(pos(),"+");
	}
	if (curr().equals(new Token("-"))) {
	    match("-");
	    return new NodeAddop(pos(),"-");
	}
	return null;
    }

    /**
     * Parses current token expressions, parenthesis, id, and num. 
     * @return NodeFactExpr or NodeFactId or NodeFactNum. 
     * @throws SyntaxException
     */
    private NodeFact parseFact() throws SyntaxException {
	if (curr().equals(new Token("("))) {
	    match("(");
	    NodeExpr expr=parseExpr();
	    match(")");
	    return new NodeFactExpr(expr);
	}
	if (curr().equals(new Token("id"))) {
	    Token id=curr();
	    match("id");
	    return new NodeFactId(pos(),id.lex());
	}
	if (curr().equals(new Token("-"))) 			//unary minus
	{
		match("-");
		NodeExpr expr=parseExpr();
		return new NodeFactUnaryMinus(pos(),expr);
	}
	Token num=curr();
	match("num");
	return new NodeFactNum(num.lex());
    }

    /**
     * Parses Term
     * @throws SyntaxException
     */
    private NodeTerm parseTerm() throws SyntaxException {
	NodeFact fact=parseFact();
	NodeMulop mulop=parseMulop();
	if (mulop==null)
	    return new NodeTerm(fact,null,null);
	NodeTerm term=parseTerm();
	term.append(new NodeTerm(fact,mulop,null));
	return term;
    }

    /**
     * Parses Expression. 
     * @return
     * @throws SyntaxException
     */
    private NodeExpr parseExpr() throws SyntaxException {
	NodeTerm term=parseTerm();
	NodeAddop addop=parseAddop();
	if (addop==null)
	    return new NodeExpr(term,null,null);
	NodeExpr expr=parseExpr();
	expr.append(new NodeExpr(term,addop,null));
	return expr;
    }

    /**
     * Parses the Node Assignment. 
     * @throws SyntaxException
     */
    private NodeAssn parseAssn() throws SyntaxException {
	Token id=curr();
	match("id");
	match("=");
	NodeExpr expr=parseExpr();
	NodeAssn assn=new NodeAssn(id.lex(),expr);
	return assn;
    }
   
    /**
     * parse the assignment statements
     * @throws SyntaxException
     */
    private NodeStmt parseStmt() throws SyntaxException {
	NodeAssn assn=parseAssn();
	match(";");
	NodeStmt stmt=new NodeStmt(assn);
	return stmt;
    }

    /**
     * Parses the program, returns a parse statement. 
     * @param program
     * @throws SyntaxException
     */
    public Node parse(String program) throws SyntaxException {
	scanner=new Scanner(program);
	scanner.next();
	NodeStmt stmt=parseStmt();
	match("EOF");
	return stmt;
    }

}
