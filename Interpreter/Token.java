// This class models a token, which has two parts:
// 1) the token itself (e.g., "id" or "+")
// 2) the token's lexeme (e.g., "foo")

public class Token {

    private String token;
    private String lexeme;

    /**
     * Constructor 1: 
     * Initializes String Token, and String lexeme. 
     * @param token
     * @param lexeme
     */
    public Token(String token, String lexeme) {
	this.token=token;
	this.lexeme=lexeme;
    }

    /**
     * Constructor 2:
     * Calls a token with two token parameters. 
     * @param token
     */
    public Token(String token) {
	this(token,token);
    }

    /**
     * Returns the token
     * @return token
     */
    public String tok() { return token; } 
    public String lex() { return lexeme; }

    /**
     * Evaluates two tokens for equality. 
     * @return true if equal, false otherwise. 
     */
    public boolean equals(Token t) {
	return token.equals(t.token);
    }

    /**
     * toString of token. 
     * @return string of token and lexeme within <>. 
     */
    public String toString() {
	return "<"+tok()+","+lex()+">";
    }

}
