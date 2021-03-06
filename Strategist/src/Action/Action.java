package Action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Check.Guards;
import Clock.ClockControl;
import Clock.NewValues;
import StateVariables.StateVariables;

public class Action {

/* Action is a pair of maps. 
 * The first, initialStates, contains the initial states of the transitions, the second the final states.
 * Both maps has the same set of keys, that are the state variables of the system. */
	private Map<String, String> initialStates;
	private Map<String, String> finalStates; 
	private Guards guards;
	private NewValues newValues;
	
/*This constructor takes a string, taken from the terminal output, and create the object Action.*/	
	public Action(String actionFromLine, String conditions, Set<String> variables) {
		this.initialStates = new HashMap<String, String>();
		this.finalStates = new HashMap<String, String>(); 
		
		actionFromLine = actionFromLine.trim(); 
		
		while(!actionFromLine.isEmpty()){
			String alfa = actionFromLine.substring(0, actionFromLine.indexOf(" ")); 
			
			this.initialStates.put(alfa.substring(0, alfa.indexOf(".")), 
					alfa.substring(alfa.indexOf(".")+1, alfa.indexOf("-")));
			
			String beta = alfa.substring(alfa.indexOf(">"+1)); 
			this.finalStates.put(beta.substring(0, beta.indexOf(".")), 
					beta.substring(beta.indexOf(".")+1).trim());
			actionFromLine = actionFromLine.replace(alfa, "").trim(); 
			}
		
			for(String s: variables){
				if (!this.initialStates.containsKey(s) && !this.finalStates.containsKey(s)){
					this.initialStates.put(s, "");
					this.finalStates.put(s, ""); 
				}
			}
		
			this.guards = new Guards(conditions.substring(0, conditions.indexOf("tau"))); 
			this.newValues = new NewValues(conditions.substring(conditions.indexOf("tau")));
	}

	/*This method change the value of the state variable value in the state.*/
	public StateVariables takeTransition(StateVariables state, ClockControl clock) {
		if (this.guards.isVerified(clock)){
			for(String actualState : state.getStateVariables().keySet())
				if (this.initialStates.get(actualState)!="")
					state.insertStateVariableValue(actualState, this.finalStates.get(actualState));
		}			
	
		return state; 
	}

	public Map<String, String> getInitialStates(){
		return this.initialStates; 
	}
	
	public Map<String, String> getFinalStates(){
		return this.finalStates; 
	}
	
	public Guards getGuards(){
		return this.guards; 
	}
	
	public NewValues getNewValues(){
		return this.newValues; 
	}
}
