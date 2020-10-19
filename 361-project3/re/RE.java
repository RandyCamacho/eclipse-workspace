package re;

import fa.State;
import fa.nfa.NFA;
import fa.nfa.NFAState;

/**
 * Reads in a regular expression and constructs an NFA
 * @author Randy Camacho
 */
public class RE implements REInterface {
	
	private String expr;
	
	private int numStates = 0;
	
	/**
	 * Constructor
	 * @param regEx
	 */
	public RE(String regEx) {
		this.expr = regEx;
	}
	
	/**
	 * looks at the next input item without doing anything
	 * @return item
	 */
	private char peek() {
		return expr.charAt(0);
	}
	
	/**
	 * Gets the next item for processing
	 * @return item
	 */
	private char next() {
		char c = peek();
		eat(c);
		return c;
	}

	/**
	 * Processes item
	 * If item does not match what we expected then throw an exception
	 * @param c
	 */
	private void eat(char c) {
		if (peek() == c) {
			expr = expr.substring(1);
		} else {
			throw new RuntimeException("Expected: " + c + "Got: " + peek());
		}
	}
	
	/**
	 * Check if there is more input to process
	 * @return true if more exists
	 */
	public boolean more() {
		return expr.length() > 0;
	}
	
	/**
	 * check that it has not reached the boundary of a term or the end of the input
	 * @return NFA
	 */
	private NFA term() {
		
        NFA factor = new NFA();
        
        String stateName = Integer.toString(numStates++);
        
        factor.addStartState(stateName);
        
        factor.addFinalState(stateName);

        while (more() && peek() != ')' && peek() != '|'){
            NFA nextFactor = factor();
            factor =  concat(factor, nextFactor);
        }
        return factor;
    }
	
	/**
	 * Mehthod for checking kleene stars
	 * parse a base and then any number of kleene stars
	 * @return NFA
	 */
	private NFA factor() {
		
        NFA baseNFA = base();
        
        while (more() && peek() == '*'){
            eat('*');
            baseNFA = repetition(baseNFA);
        }
        return baseNFA;
    }
	
	/**
	 * Implement Concatenation
	 * 
	 * @param first
	 * @param second
	 * @return NFA
	 */
	private NFA concat(NFA first, NFA second) {

        for (State nfa : first.getFinalStates()) {
            ((NFAState) nfa).setNonFinal();
            ((NFAState) nfa).addTransition('e', (NFAState) second.getStartState());
        }

        first.addNFAStates(second.getStates());
        
		first.addAbc(second.getABC());
		
		return first;
	}
	
	/**
	 * Checks to see which of the three cases it has encountered
	 * @return NFA
	 */
	private NFA base(){
        NFA nfa;
        switch (peek()){
            case '(':
                eat('(');
                nfa = regEx();
                eat(')');
                return nfa;

            default:
            	return primitive(next());
        }
    }
	
	/**
	 * Helper Method
	 *
	 * @param c
	 * @return NFA
	 */
	private NFA primitive(char c) {
		
    	NFA nfa = new NFA();
    	
        String startName = Integer.toString(numStates++);
        
        nfa.addStartState(startName);
        
        String finalName = Integer.toString(numStates++);
        
        nfa.addFinalState(finalName);
        
        nfa.addTransition(startName, c, finalName);
        
        return nfa;
	}
	
	/**
	 * catch repetition
	 * @param nfa
	 * @return
	 */
	private NFA repetition(NFA nfa) {
		NFAState startState = (NFAState) nfa.getStartState();
		
		for(State finalStates : nfa.getFinalStates()){
		    nfa.addTransition(finalStates.getName(), 'e', startState.getName());
        }
		
		String newStart = Integer.toString(numStates++);
		
		nfa.addStartState(newStart);
		
		nfa.addFinalState(newStart);
		
		nfa.addTransition(newStart, 'e', startState.getName());
		
		return nfa;
	}
	
	/**
	 * 
	 * @param nfaOne
	 * @param nfaTwo
	 * @return
	 */
	private NFA choice(NFA nfaOne, NFA nfaTwo) {
        NFAState stateOne = (NFAState) nfaOne.getStartState();
        
        NFAState stateTwo = (NFAState) nfaTwo.getStartState();

        nfaOne.addNFAStates(nfaTwo.getStates());
        nfaOne.addAbc(nfaTwo.getABC());

        String newOne = Integer.toString(numStates++);
        
        nfaOne.addStartState(newOne);
        
        nfaOne.addTransition(newOne, 'e', stateOne.getName());
        
        nfaOne.addTransition(newOne, 'e', stateTwo.getName());
        
        return nfaOne;
    }
	
	/**
	 * method, we know that we must parse at least one term, 
	 * and whether we parse another depends only on what we find afterward:
	 * @return 
	 */
	private NFA regEx() {
		
		NFA term = term();
		
        if(more() && peek() == '|'){
            eat('|');
            return choice(term, regEx());
        } else {
            return term;
        }
	}
	
	@Override
	public NFA getNFA() {
		
		return regEx();
	}
}
