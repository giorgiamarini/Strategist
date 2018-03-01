package PlanStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Action.Action;
import Clock.ClockControl;
import Message.DispatchMessage;
import Message.FailureMessage;
import Message.Message;
import Message.WaitMessage;
import StateVariables.StateVariables;
import ThingsThatMustBeVerified.Condition;

public class PlanStatus {
	private StateVariables state; 
	private ClockControl clocks; 
	private Message message; 

	public PlanStatus(ClockControl clocks, Set<String> sv){
		this.clocks = clocks; 
		this.state = new StateVariables();
		this.state.initialize(sv);
		this.message = null; 

	}
	
	public void increaseClocks(){
		this.clocks.increaseClocks();
	}
	
	/*This method does the transition, given a condition and an action.*/
	private void transition(Condition condition, Action action) throws Exception{
		if (condition.isVerified(this.clocks)){
			action.takeTransition(this.state, this.clocks);	 
			this.clocks.setNewValues(action.getNewValues());
		}
	}


	public void evaluateCondition(Condition condition, Action action) throws Exception{
		if (condition.getCondition().startsWith("When") && condition.isVerified(this.clocks)){
			transition(condition, action);

			Map<String, String> tokensToDispatch = new HashMap<String,String>(); 
			tokensToDispatch = action.getFinalStates();
			for (String a : tokensToDispatch.keySet()){
				if (tokensToDispatch.get(a).isEmpty())
					tokensToDispatch.remove(a);
			}
			this.message = new DispatchMessage("Dispatch", tokensToDispatch.values());

		} else { 
			if (condition.getCondition().startsWith("While")){ 
				if (!condition.isVerified(this.clocks)){
					this.message = new WaitMessage("Wait until", condition.getPlanClock());

				}
			}
		}
		this.message = new FailureMessage("Warning, an error is occurred.");
	}
	
	public StateVariables getState(){
		return this.state; 
	}
	
	public Message getMessage(){
		return this.message; 
	}
	
	public ClockControl getClocks(){
		return this.clocks; 
	}
}
