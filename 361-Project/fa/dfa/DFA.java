package fa.dfa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import fa.FAInterface;
import fa.State;

/**
 * The DFA
 * @author Randy Camacho
 */
public class DFA implements DFAInterface, FAInterface {

	ArrayList<DFAState> finalStateList = new ArrayList<>();
	ArrayList<DFAState> startStateList = new ArrayList<>();
	ArrayList<DFAState> stateList = new ArrayList<>();
	ArrayList<DFAState> allStates = new ArrayList<>();
	Set<State> finalStateSet = new HashSet<State>();
	Set<State> startStateSet = new HashSet<State>();
	Set<State> stateSet = new HashSet<State>();
	Set<Character> symbols = new HashSet<Character>();
	Set<Character> alphabet = new HashSet<Character>();

	/**
	 * Adds start state to the DFA
	 */
	public void addStartState(String startStateName) {
		DFAState dfa = new DFAState();
		dfa.setName(startStateName);
		startStateList.add(dfa);
		startStateSet.add(dfa);
		allStates.add(dfa);
	}
	
	/**
	 * Adds final state to the DFA
	 */
	public void addFinalState(String nextToken) {
		DFAState dfa = new DFAState();
		dfa.setName(nextToken);
		finalStateList.add(dfa);
		finalStateSet.add(dfa);
		allStates.add(dfa);
	}

	/**
	 * Adds states to the DFA
	 */
	public void addState(String nextToken) {
		DFAState dfa = new DFAState();
		dfa.setName(nextToken);
		stateSet.add(dfa);
		stateList.add(dfa);
		allStates.add(dfa);
	}
	
	/**
	 * Adds transition to map.
	 */
	public void addTransition(String valueOf, char c, String valueOf2) {
		symbols.add(valueOf.charAt(0));
		symbols.add(valueOf2.charAt(0));
		alphabet.add(c);

		for (DFAState state : finalStateList){
			if (state.getName().equals(valueOf)){
				state.setTransition(c, valueOf2);
			}
		}
		for (DFAState state : startStateList){
			if (state.getName().equals(valueOf)){
				state.setTransition(c, valueOf2);
			}
		}
		for (DFAState state : stateList){
			if (state.getName().equals(valueOf)){
				state.setTransition(c, valueOf2);
			}
		}
	}

	/**
	 * checks if a string is valid in the DFA
	 * returns true if it reaches the final state
	 * else returns false
	 */
	public boolean accepts(String nextLine) {

		DFAState currentState = startStateList.get(0);
		for (char c : nextLine.toCharArray()){
			currentState = getToState(currentState, c);
		}
		for (DFAState state : finalStateList){
			if (currentState.equals(state)){
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Uses transition function delta of FA
	 * @param from the source state
	 * @param onSymb the label of the transition
	 * @return the sink state.
	 */
	public DFAState getToState(DFAState from, char transitionSymb) {

		String s = from.getNextState(transitionSymb);

		for (DFAState a :  startStateList){
			if (a.getName().equals(s)){
				return a;
			}
		}

		for (DFAState a : finalStateList){
			if (a.getName().equals(s)){
				return a;
			}
		}

		for (DFAState a : stateList){
			if (a.getName().equals(s)){
				return a;
			}
		}

		return new DFAState();
	}

	/**
	 * get all the states in a set
	 */
	public Set<State> getStates() {
		return stateSet;
	}

	/**
	 * get all final states
	 */
	public Set<State> getFinalStates() {
		return startStateSet;
	}

	/**
	 * get the start state
	 */
	public State getStartState() {
		return startStateList.get(0);
	}

	/**
	 * Getter for Sigma
	 * @return the alphabet of FA
	 */
	public Set<Character> getABC() {
		return this.symbols;
	}

	/**
	 * Construct the textual representation of the DFA, for example
	 * A simple two state DFA
	 * Q = { a b }
	 * Sigma = { 0 1 }
	 * delta =
	 *		0	1	
	 *	a	a	b	
	 *	b	a	b	
	 * q0 = a
	 * F = { b }
	 * 
	 * The order of the states and the alphabet is the order
	 * in which they were instantiated in the DFA.
	 * @return String representation of the DFA
	 */
	public String toString(){
		String ret = "Q = {";
		for (DFAState state : allStates){
			ret += " " + state.getName();
		}		

		ret += " }";
		ret += "\n";
		ret += "Sigma = {";
		for (Character a : alphabet){
			ret += " " + a;
		}
		ret += " }\n";

		ret += "delta = \n";

		ret += "\t\t";
		for (Character a: alphabet){
			ret += a + "\t";
		}
		ret += "\n\t";

		for (DFAState state : allStates){
			ret += state + "\t";
			for (Character a : alphabet){
				ret += state.getNextState(a) + "\t";
			}
			ret += "\n\t";			
		}
		ret += "\n";
		ret += "q0 = ";
		for (State a : startStateSet){
			ret += a.getName();
		}

		ret+= "\n";
		ret += "F = {";
		for (State a : finalStateSet){
			ret+= " " + a.getName();
		}
		ret += " } \n";		

		return ret;

	}

}