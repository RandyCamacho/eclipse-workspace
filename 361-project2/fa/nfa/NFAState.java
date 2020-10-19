package fa.nfa;

import java.util.HashMap;
import java.util.HashSet;

import fa.State;

/**
 * Implementation of NFAState
 * @author randycamacho
 */
public class NFAState extends State {

	public boolean isFinalState = false;
	private HashMap<Character, HashSet<NFAState>> transitionsMap;
	
	/**
	 * Constructor
	 * @param stateName
	 */
	public NFAState(String stateName){
		initState(stateName);
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param isFinalState
	 */
	public NFAState(String name, boolean isFinalState){
		initState(name);
		this.isFinalState = isFinalState;
	}
	
	/**
	 * Check to see if the state is Final
	 * @return
	 */
	public boolean isFinalState() {
		return this.isFinalState;
	}

	/**
	 * returns the next state for a symbol
	 * @param symb
	 */
	public HashSet<NFAState> getTo(char symb){
		HashSet<NFAState> transition = transitionsMap.get(symb);
		if (transition == null){
			return new HashSet<NFAState>();
		}
		else{
			return transitionsMap.get(symb);
		}
	}
	/**
	 * Initialize state
	 * @param stateName
	 */
	private void initState(String stateName ){
		this.name = stateName;
		this.transitionsMap = new HashMap<Character, HashSet<NFAState>>();
	}
	
	/**
	 * Add Transitions to the map
	 * @param onSymb
	 * @param toState
	 */
	public void addTransition (char onSymb, NFAState toState) {
		HashSet<NFAState> s = transitionsMap.get(onSymb);
		if (s == null) {
			s = new HashSet<NFAState>();
			s.add(toState);
			transitionsMap.put(onSymb, s);
		} else {
			s.add(toState);
			transitionsMap.put(onSymb, s);
		}
	}
}
