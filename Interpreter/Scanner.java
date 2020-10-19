// This class is a scanner for the program
// and programming language being interpreted.

/**
 * @author Randy Camacho
 * @author CS354 Instructors
 */

import java.util.*;

public class Scanner {

    private String program;	// source program being interpreted
    private int pos;		// index of next char in program
    private Token token;	// last/current scanned token

    // sets of various characters and lexemes
    private Set<String> whitespace=new HashSet<String>();
    private Set<String> digits=new HashSet<String>();
    private Set<String> letters=new HashSet<String>();
    private Set<String> legits=new HashSet<String>();
    private Set<String> keywords=new HashSet<String>();
    private Set<String> operators=new HashSet<String>();
    private Set<String> comments = new HashSet<String>();

    // initializers for previous sets

    /**
     * Fills the Set of strings s from lo to hi. 
     * @param s
     * @param lo
     * @param hi
     */
    private void fill(Set<String> s, char lo, char hi) {
	for (char c=lo; c<=hi; c++)
	    s.add(c+"");
    }    

    /**
     * Fill Set of strings s, with whitespace of space
     * new line and tabs. 
     * @param s
     */
    private void initWhitespace(Set<String> s) {
	s.add(" ");
	s.add("\n");
	s.add("\t");
    }

    /**
     * Initialize the Digits Set. 
     * @param s
     */
    private void initDigits(Set<String> s) {
	fill(s,'0','9');
    }

    /**
     * Initialize the Letters Set a-z A-Z s. 
     * @param s
     */
    private void initLetters(Set<String> s) {
	fill(s,'A','Z');
	fill(s,'a','z');
    }

    /**
     * Initialize the letters and digits legits set. 
     * @param s
     */
    private void initLegits(Set<String> s) {
	s.addAll(letters);
	s.addAll(digits);
    }

    /**
     * Initialize the operators set. 
     * @param s
     */
    private void initOperators(Set<String> s) {
	s.add("=");
	s.add("+");
	s.add("-");
	s.add("*");
	s.add("/");
	s.add("(");
	s.add(")");
	s.add(";");
    }

    /**
     * Initialize the Keywords set. 
     * @param s
     */
    private void initKeywords(Set<String> s) {
    }
    
    /**
     * Initialize the valid comment character set.  
     * @param s
     */
    private void initComments(Set<String> s)
	{
		s.add("#");
	}

    // constructor:
    //   - squirrel-away source program
    //   - initialize sets
    public Scanner(String program) {
	this.program=program;
	pos=0;
	token=null;
	initWhitespace(whitespace);
	initDigits(digits);
	initLetters(letters);
	initLegits(legits);
	initKeywords(keywords);
	initOperators(operators);
	initComments(comments);
    }

    // handy string-processing methods

    /**
     * Depending on position, will return true if it's 
     * greater than or less than program length, and false otherwise. 
     * @return boolean 
     */
    public boolean done() {
	return pos>=program.length();
    }

    /**
     * Increment position to the next character in the program
     * if more are present and the program is not done. 
     * @param s
     */
    private void many(Set<String> s) {
	while (!done() && s.contains(program.charAt(pos)+""))
	    pos++;
    }
    
    /**
     * Go past current character at specified position. 
     * @param c
     */
    private void past(char c) {
	while (!done() && c!=program.charAt(pos))
	    pos++;
	if (!done() && c==program.charAt(pos))
	    pos++;
    }

    // scan various kinds of lexeme

    /**
     * Grab next number if present and create a new 
     * token string/lexeme. 
     */
    private void nextNumber() {
	int old=pos;
	many(digits);
	token=new Token("num",program.substring(old,pos));
    }

    /**
     * Looks for letters and legits, checking for valid ID's. 
     */
    private void nextKwId() {
	int old=pos;
	many(letters);
	many(legits);
	String lexeme=program.substring(old,pos);
	token=new Token((keywords.contains(lexeme) ? lexeme : "id"),lexeme);
    }

    /**
     * Look for next operator. 
     */
    private void nextOp() {
	int old=pos;
	pos=old+2;
	if (!done()) {
	    String lexeme=program.substring(old,pos);
	    if (operators.contains(lexeme)) {
		token=new Token(lexeme); // two-char operator
		return;
	    }
	}
	pos=old+1;
	String lexeme=program.substring(old,pos);
	token=new Token(lexeme); // one-char operator
    }
    
    /**
     * Next comment character
     */
    public void nxtCmt() {
		pos++;
		past('#');
		next();
	}

    // This method determines the kind of the next token (e.g., "id"),
    // and calls a method to scan that token's lexeme (e.g., "foo").
    public boolean next() {
	if (done()) {
	    token=new Token("EOF");
	    return false;
	}
	many(whitespace);
	String c=program.charAt(pos)+"";
	if (digits.contains(c))
	    nextNumber();
	else if (letters.contains(c))
	    nextKwId();
	else if (operators.contains(c))
	    nextOp();
	else if(comments.contains(c))
		nxtCmt();
	else {
	    System.err.println("illegal character at position "+pos);
	    pos++;
	    return next();
	}
	return true;
    }

    // This method scans the next lexeme,
    // if the current token is the expected token.
    public void match(Token t) throws SyntaxException {
	if (!t.equals(curr()))
	    throw new SyntaxException(pos,t,curr());
	next();
    }

    /**
     * @return
     * @throws SyntaxException
     */
    public Token curr() throws SyntaxException {
	if (token==null)
	    throw new SyntaxException(pos,new Token("ANY"),new Token("EMPTY"));
	return token;
    }

    public int pos() { return pos; }

    // for unit testing
    public static void main(String[] args) {
	try {
	    Scanner scanner=new Scanner(args[0]);
	    while (scanner.next())
		System.out.println(scanner.curr());
	} catch (SyntaxException e) {
	    System.err.println(e);
	}
    }

}
