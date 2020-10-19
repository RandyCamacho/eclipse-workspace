package fa.dfa;

import java.util.HashMap;
import java.util.Map;
import fa.State;

/**
 * DFAState class
 * @author Randy Camacho
 */
public class DFAState extends State {

	Map<Character, String> transitionMap = new HashMap<Character, String>();

	/**
	 * Set the name of each state
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Set the transition for a state
	 * @param validTransition
	 * @param transitionState
	 */
	public void setTransition(char validTransition, String transitionState){
		this.transitionMap.put(validTransition, transitionState);
	}

	/**
	 * Gets the next state, given a transition.
	 * @param validTransition
	 * @return
	 */
	public String getNextState(char validTransition){
		return this.transitionMap.get(validTransition);
	}

}
