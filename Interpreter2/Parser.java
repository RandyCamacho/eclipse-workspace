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
     * Parses a boolean expression
     * @return - a boolean expression
     */
    private BoolExpr parseBoolExpr() throws SyntaxException
    {
        NodeExpr leftHandSide = parseExpr();
        String relop = curr().lex();
        match(relop);
        NodeExpr rightHandSide = parseExpr();
        return new BoolExpr(leftHandSide, relop, rightHandSide);
    }
    
    /**
     * Parses any number of consecutive unary + or -
     *
     * @return a NodeUnary that contains the result one or many unary + or -s
     * @throws SyntaxException
     */
    private NodeUnaryMinus parseUnary() throws SyntaxException
    {
        int i = 1;
        while (curr().lex().equals("-") || curr().lex().equals("+"))
        {
            if (curr().lex().equals("-"))
            {
                i *= -1;
                match("-");
            }
            else
            {
                match("+");
            }

        }
        return i == -1 ? new NodeUnaryMinus("-") : null;
    }

    /**
     * Parses a digit
     *
     * @return a NodeNum with the parsed digit
     * @throws SyntaxException
     */
    private NodeNum parseDigit() throws SyntaxException
    {
        String lex = curr().lex();
        return new NodeNum(lex);
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
    private NodeFact parseFact() throws SyntaxException
    {
        NodeUnaryMinus unary = parseUnary();
        if (curr().equals(new Token("(")))
        {
            match("(");
            NodeExpr expr = parseExpr();
            match(")");
            return new NodeFactExpr(expr, unary);
        }
        if (curr().equals(new Token("id")))
        {
            Token id = curr();
            match("id");
            NodeFactId factId = new NodeFactId(pos(), id.lex(), unary);
            return factId;
        }
        NodeNum node = parseDigit();
        node.setUnary(unary);
        match("digit");
        return new NodeFactNum(node);
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
    private NodeStmt parseStmt() throws SyntaxException
    {
        NodeStmt stmt = null;
        
        switch(curr().lex())
        {
            case "rd":
                match("rd");
                Token id = curr();
                match("id");
                stmt = new NodeStmtRd(id.lex());
                break;
            case "wr":
                match("wr");
                NodeExpr expr = parseExpr();
                stmt = new NodeStmtWr(expr);
                break;
            case "if":
                match("if");
                BoolExpr bexpr = parseBoolExpr();
                match("then");
                NodeStmt ifThenStmt = parseStmt();
                if (curr().lex().equals("else"))
                {
                    match("else");
                    NodeStmt elseStmt = parseStmt();
                    return new NodeStmtIfThenElse(bexpr, ifThenStmt, elseStmt);
                }
                else
                {    
                    stmt = new NodeStmtIfThen(bexpr, ifThenStmt);
                }
                break;
            case "begin":
                match("begin");
                stmt = parseBlock();
                match("end");
                break;
            case "while":
                match("while");
                BoolExpr whileBexpr = parseBoolExpr();
                match("do");
                NodeStmt whileStmt = parseStmt();
                stmt = new NodeStmtWhile(whileBexpr, whileStmt);
                break;
            default:
                NodeAssn assn = parseAssn();
                stmt = new NodeStmtAssn(assn);
                break;
        }
        return stmt;
    }
    private NodeBlock parseBlock() throws SyntaxException
    {
        List<NodeStmt> statements = new LinkedList<>();
        NodeStmt statement;
        while(true)
        {
            statement = parseStmt();
            statements.add(statement);
            
            if (curr().equals(new Token(";")))
            {
                match(";");
            }
            else
            {
                break;
            }
        }
      
        return new NodeBlock(statements);
    }
    /**
     * Parses the program, returns a parse statement. 
     * @param program
     * @throws SyntaxException
     */
    public Node parse(String program) throws SyntaxException {
	scanner=new Scanner(program);
	scanner.next();
	return scanner.getNoTokens() ? null : parseBlock();
    }

}
