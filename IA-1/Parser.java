// This class is a recursive-descent parser,
// modeled after the programming language's grammar.
// It constructs and has-a Scanner for the program
// being parsed.

import java.util.*;

/**
 * Combines scanned lexemes into the grammar
 *
 */
public class Parser 
{
	private Scanner scanner;

	/**
	 * Calls the scanners match function
	 * 
	 * @param s
	 * @throws SyntaxException
	 */
	private void match(String s) throws SyntaxException 
	{
		scanner.match(new Token(s));
	}

	/**
	 * Return the current token value returned from the scanner
	 * 
	 * @return scanner.curr()
	 * @throws SyntaxException
	 */
	private Token curr() throws SyntaxException 
	{
		return scanner.curr();
	}

	/**
	 * Return the value of pos to the parser
	 * 
	 * @return scanner.pos()
	 */
	private int pos()
	{
		return scanner.pos();
	}

	/**
	 * Parses a muloperation
	 * 
	 * @return 
	 * @throws SyntaxException
	 */
	private NodeMulop parseMulop() throws SyntaxException 
	{
		if (curr().equals(new Token("*"))) 
		{
			match("*");
			return new NodeMulop(pos(),"*");
		}

		if (curr().equals(new Token("/"))) 
		{
			match("/");
			return new NodeMulop(pos(),"/");
		}
		return null;
	}

	/**
	 * Parse an addoperation
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	private NodeAddop parseAddop() throws SyntaxException 
	{
		if (curr().equals(new Token("+"))) 
		{
			match("+");
			return new NodeAddop(pos(),"+");
		}
		if (curr().equals(new Token("-"))) 
		{
			match("-");
			return new NodeAddop(pos(),"-");
		}
		return null;
	}

	
	/**
	 * Return the value of fact value which used a term and expr.
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	private NodeFact parseFact() throws SyntaxException 
	{	
		if (curr().equals(new Token("("))) 
		{
			match("(");
			NodeExpr expr=parseExpr();
			match(")");
			return new NodeFactExpr(expr);
		}

		if (curr().equals(new Token("id"))) 
		{
			Token id=curr();
			match("id");
			return new NodeFactId(pos(),id.lex());
		}

		if (curr().equals(new Token("-"))) 
		{
			match("-");
			NodeFact fact = parseFact();
			return new NodeFactUnaryMinus(fact);
		}
		Token num=curr();
		match("num");
		return new NodeFactNum(num.lex());	
	}

	/**
	 * Parse a term
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	private NodeTerm parseTerm() throws SyntaxException 
	{
		NodeFact fact=parseFact();
		NodeMulop mulop=parseMulop();

		if (mulop==null)
		{
			return new NodeTerm(fact,null,null);
		}
		NodeTerm term=parseTerm();
		term.append(new NodeTerm(fact,mulop,null));
		return term;
	}

	/**
	 * Parse an expression
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	private NodeExpr parseExpr() throws SyntaxException 
	{
		NodeTerm term=parseTerm();
		NodeAddop addop=parseAddop();

		if (addop==null)
		{
			return new NodeExpr(term,null,null);
		}

		NodeExpr expr=parseExpr();
		expr.append(new NodeExpr(term,addop,null));
		return expr;
	}

	/**
	 * Parse an assignment
	 * 
	 * @return
	 * @throws SyntaxException
	 */
//	private NodeAssn parseAssn() throws SyntaxException 
//	{
//		Token id=curr();
//		match("id");
//		match("=");
//		NodeExpr expr=parseExpr();
//		NodeAssn assn=new NodeAssn(id.lex(),expr);
//		return assn;
//	}

	/**
	 * Parse a statement
	 * 
	 * @return stmt
	 * @throws SyntaxException
	 */
	//TODO
	private NodeStmt parseStmt() throws SyntaxException 
	{

		if(curr().equals(new Token("id"))) 
		{
			Token id = curr();
			match("id");
			match("=");
			NodeExpr expr = parseExpr();
			return new NodeStmtAssn(id.lex(), expr);
		}

		match("begin");
		NodeBlock block  = parseBlock();
		match("end"); 

		return new NodeStmtBegin(block);
	}

	// TODO
	/**
	 * Parse a block
	 * @return
	 * @throws SyntaxException
	 */
	private NodeBlock parseBlock() throws SyntaxException
	{
		NodeStmt stmt = parseStmt();
		NodeBlock block = null;

		if(curr().equals(new Token(";")))
		{
			match(";");
			block = parseBlock();
		}
		return new NodeBlock(stmt, block);
	}

	//private Node
	/**
	 * Parse the input
	 * 
	 * @param program
	 * @return parseStmt()
	 * @throws SyntaxException
	 */
	public Node parse(String program) throws SyntaxException
	{
		scanner=new Scanner(program);
		scanner.next();
		return parseBlock();
	}
}
