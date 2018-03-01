package Action;

import java.util.HashMap;
import java.util.Map;


import Clock.ClockControl;
import Clock.NewValues;
import StateVariables.StateVariables;
import ThingsThatMustBeVerified.Guards;

public class Action {

/* Action is a pair of maps. 
 * The first, initialStates, contains the initial states of the transitions, the second the final states.
 * Both maps has the same set of keys, that are the state variables of the system. */
	private Map<String, String> initialStates;
	private Map<String, String> finalStates; 
	private Guards guards;
	private NewValues newValues;
	
/*This constructor takes a string, taken from the terminal output, and create the object Action.*/	
	public Action(String actionFromLine, String conditions) {
		this.initialStates = new HashMap<String, String>();
		this.finalStates = new HashMap<String, String>(); 
		
		actionFromLine = actionFromLine.trim(); 
		String a, b = ""; 
		
		while(!actionFromLine.isEmpty()){
			if (!actionFromLine.contains("->")){
				this.finalStates.put(actionFromLine.substring(0, actionFromLine.indexOf('.')), 
						actionFromLine.substring(actionFromLine.indexOf('.'), actionFromLine.length())); 
				actionFromLine = actionFromLine.trim(); 

			}
			else {
				if(!actionFromLine.startsWith("->")){
					a = actionFromLine.substring(3, actionFromLine.indexOf(".")); 
					b = actionFromLine.substring(actionFromLine.indexOf(".")+1, actionFromLine.indexOf('-'));
					this.initialStates.put(a, b); 
				} else {
					a = actionFromLine.substring(0, actionFromLine.indexOf(".")); 
					b = actionFromLine.substring(actionFromLine.indexOf(".")+1, actionFromLine.indexOf(' '));
					this.finalStates.put(a, b); 
				}
					actionFromLine = actionFromLine.substring(a.concat(b).length()+2);
					actionFromLine = actionFromLine.trim(); 

				}
			
			this.guards = new Guards(conditions.substring(0, conditions.indexOf("tau"))); 
			this.newValues = new NewValues(conditions.substring(conditions.indexOf("tau")));

		}
	}

	/*This method change the value of the state variable value in the state.*/
	public void takeTransition(StateVariables state, ClockControl clock) {
		if (this.guards.isVerified(clock))
			for(String actualState : state.getStateVariables().keySet())
				if (actualState == this.initialStates.get(actualState))
					state.insertStateVariableValue(actualState, this.finalStates.get(actualState));
					
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
