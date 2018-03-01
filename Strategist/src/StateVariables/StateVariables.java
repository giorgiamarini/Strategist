package StateVariables;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*State is a map where the keys are the state variables and the value is the value of the state variables in that moment.*/
public class StateVariables {
	private Map<String, String> stateVariablesValues; 
	
	public StateVariables(){
		this.stateVariablesValues = new HashMap<String, String>();
	}

	/*The method initialize sets all the values of the state variables in the state to 'start'.*/
	public void initialize(Set<String> sv){
		for(String stateVariable : sv){
			this.stateVariablesValues.put(stateVariable, "start");
		}
	}
	
	public void updateState(String state){
		String a = state; 
		for (String key : this.stateVariablesValues.keySet()){
			String s = a.substring(state.indexOf(key.concat("")));
			s = s.substring(0, s.indexOf(" "));
			insertStateVariableValue(key, s); 
			
			a = state; 
		}
	}
	
	/*isState() controls that state passed as string is the current state.*/
	public boolean isState(String state){
		boolean isState = true;
		for (String sv : this.stateVariablesValues.keySet()){
			isState = isState && state.contains(sv.concat(".").concat(this.stateVariablesValues.get(sv)));
		}
		return isState;
	}
	
	public boolean equals(StateVariables state){
		boolean isEqual = false; 
		
		for (String key : this.stateVariablesValues.keySet()){
			for (String yek : state.getState().keySet()){
				isEqual = isEqual && this.stateVariablesValues.get(key).equals(state.getState().get(yek));
			}
		} return isEqual; 
	}
	
	/*This method insert another sv value in the map.*/
	public void insertStateVariableValue(String key, String value){
			stateVariablesValues.put(key, value);
		}
	
	
	public Map<String, String> getState(){
		return this.stateVariablesValues; 
	}
	
}
