package fa.nfa;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import fa.State;
import fa.dfa.DFA;

/**
 * Implementation of NFA
 * @author randycamacho
 *
 */
public class NFA implements NFAInterface {
	
	private NFAState start;
	private Set<NFAState> states;
	private Set<Character> ABC;
	private Set<NFAState> epsilon = new HashSet<NFAState>();
	
	/**
	 * Constructor for NFA
	 */
	public NFA() {
		this.states = new HashSet<NFAState>();
		this.ABC = new HashSet<Character>();
	}

	/**
	 * (non-Javadoc)
	 * @see fa.FAInterface#addStartState(java.lang.String)
	 */
	@Override
	public void addStartState(String name) {
		NFAState s = getState(name);
		if(s == null){
			s = new NFAState(name);
			addState(s);
		}
		this.start = s;	
	}
	
	/**
	 * Helper method for getting a state
	 * @param name
	 * @return an NFAState
	 */
	private NFAState getState(String name){
		NFAState ret = null;
		for(NFAState s : this.states){
			if(s.getName().equals(name)){
				ret = s;
				break;
			}
		}
		return ret;
	}
	
	/**
	 * Helper method for adding a state
	 * @param state
	 */
	private void addState(NFAState state){
		this.states.add(state);
	}

	/**
	 * (non-Javadoc)
	 * @see fa.FAInterface#addState(java.lang.String)
	 */
	@Override
	public void addState(String name) {
		NFAState s = new NFAState(name);
		states.add(s);	
	}

	/**
	 * (non-Javadoc)
	 * @see fa.FAInterface#addFinalState(java.lang.String)
	 */
	@Override
	public void addFinalState(String name) {
		NFAState s = new NFAState(name, true);
		addState(s);	
	}

	/**
	 * (non-Javadoc)
	 * @see fa.FAInterface#addTransition(java.lang.String, char, java.lang.String)
	 */
	@Override
	public void addTransition(String fromState, char onSymb, String toState) {
		(getState(fromState)).addTransition(onSymb, getState(toState));
		if(!ABC.contains(onSymb) && onSymb != 'e')
			ABC.add(onSymb);
	}

	/**
	 * (non-Javadoc)
	 * @see fa.FAInterface#getStates()
	 */
	@Override
	public Set<? extends State> getStates() {
		Set<State> ret = new HashSet<State>();
		ret.addAll(this.states);
		return ret;
	}

	/**
	 * (non-Javadoc)
	 * @see fa.FAInterface#getFinalStates()
	 */
	@Override
	public Set<? extends State> getFinalStates() {
		Set<State> ret = new HashSet<State>();
		for (NFAState s : this.states) {
			if (s.isFinalState()) {
				ret.add(s);
			}
		}
		return ret;
	}

	/**
	 * (non-Javadoc)
	 * @see fa.FAInterface#getStartState()
	 */
	@Override
	public State getStartState() {
		return this.start;
	}

	/**
	 * (non-Javadoc)
	 * @see fa.FAInterface#getABC()
	 */
	@Override
	public Set<Character> getABC() {
		
		return this.ABC;
	}

	/**
	 * (non-Javadoc)
	 * @see fa.nfa.NFAInterface#getDFA()
	 */
	@Override
	public DFA getDFA() {
		DFA dfa = new DFA();	
		LinkedList<HashSet<NFAState>> listSet = new LinkedList<HashSet<NFAState>>();
		Set<Set<NFAState>> temp = new HashSet<Set<NFAState>>();
		HashSet<NFAState> newSet = new HashSet<NFAState>();
		
		newSet.add(start);
		newSet.addAll(eClosure(start));
		
		boolean isFinalState = false;
		
		for (NFAState state : newSet) {
			if (state.isFinalState()) {
				isFinalState = true;
			}
		} if (isFinalState == true) {
			dfa.addFinalState(newSet.toString());
		}
		
		dfa.addStartState(newSet.toString());	
		listSet.addFirst(newSet);
		
		while (!listSet.isEmpty()) {
			HashSet<NFAState> currentState = listSet.removeLast();
			temp.add(currentState);
			String currentSet = currentState.toString();
			
			for (Character c : ABC) {
				isFinalState = false;
				HashSet<NFAState> createSet = new HashSet<NFAState>();
				
				for (NFAState state : currentState) {
					HashSet<NFAState> transitions = state.getTo(c);
					
					if (transitions != null) {
						
						for (NFAState transition : transitions) {
							this.epsilon = createSet;
							eClosure(transition);
							
							if (transition.isFinalState()) {
								isFinalState = true;
							}
							
							createSet.add(transition);
						}
					}
					
					String newName = createSet.toString();
					if (isFinalState == true) {
						if (!temp.contains(createSet) && !listSet.contains(createSet)){
							dfa.addFinalState(newName);
						}
						dfa.addTransition(currentSet, c, newName);
					} else if (!temp.contains(createSet) && !listSet.contains(createSet)){
						dfa.addState(newName);
					}
					dfa.addTransition(currentSet, c , newName);
					if (!temp.contains(createSet) && !listSet.contains(createSet)){
						listSet.addFirst(createSet);
					}
				}
			}			
		}	return dfa;
	}

	/**
	 * (non-Javadoc)
	 * @see fa.nfa.NFAInterface#getToState(fa.nfa.NFAState, char)
	 */
	@Override
	public Set<NFAState> getToState(NFAState from, char onSymb) {
		return from.getTo(onSymb);
	}

	/**
	 * * (non-Javadoc)
	 * @see fa.nfa.NFAInterface#eClosure(fa.nfa.NFAState)
	 */
	@Override
	public Set<NFAState> eClosure(NFAState s) {
		HashSet<NFAState> epsilonSet = s.getTo('e');
		if (epsilonSet != null) {
			for (NFAState state : epsilonSet) {
				NFAState temp = state;
				this.epsilon.add(temp);
				eClosure(temp);
			}
		}
		return this.epsilon;
	}	
}
