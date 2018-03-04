package PlanStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Action.Action;
import Clock.ClockControl;
import StateVariables.StateVariables;
import ThingsThatMustBeVerified.Condition;
import it.istc.pst.platinum.executive.dc.DCResult;
import it.istc.pst.platinum.executive.dc.DispatchDCResult;
import it.istc.pst.platinum.executive.dc.FailureDCResult;
import it.istc.pst.platinum.executive.dc.WaitDCResult;

public class PlanStatus {
	private StateVariables stateVariablesValue; 
	private ClockControl clocks; 
	private DCResult message; 

	public PlanStatus(ClockControl clocks, Set<String> sv){
		this.clocks = clocks; 
		this.stateVariablesValue = new StateVariables();
		this.stateVariablesValue.initialize(sv);
		this.message = null; 

	}

	public long increaseClocks(){
		this.clocks.increaseClocks();
		
		return this.clocks.getPlanClock().getTime();
	}

	/*This method does the transition, given a condition and an action.*/
	private void transition(Condition condition, Action action) throws Exception{
		if (condition.isVerified(this.clocks)){
			action.takeTransition(this.stateVariablesValue, this.clocks);	 
			this.clocks.setNewValues(action.getNewValues());
		}
	}


	public void evaluateCondition(Condition condition, Action action) throws Exception{
		if (condition.getCondition().startsWith("When")){
			while (!condition.isVerified(this.clocks)){
				;
				
			transition(condition, action);

			Map<String, String> tokensToDispatch = new HashMap<String,String>(); 
			tokensToDispatch = action.getInitialStates();
			for (String a : tokensToDispatch.keySet()){
				if (tokensToDispatch.get(a).isEmpty())
					tokensToDispatch.remove(a);
			}
			
			this.message = new DispatchDCResult(tokensToDispatch);
			}
			
		} else { 
			if (condition.getCondition().startsWith("While")){ 
				if (!condition.isVerified(this.clocks)){
					this.message = new WaitDCResult(condition.getPlanClock());

				}
			}
		}
		this.message = new FailureDCResult();
	}

	public StateVariables getStateVariablesValues(){
		return this.stateVariablesValue; 
	}

	public DCResult getMessage(){
		return this.message; 
	}

	public ClockControl getClocks(){
		return this.clocks; 
	}
}
