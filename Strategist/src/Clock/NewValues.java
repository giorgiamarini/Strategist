package Clock;

import java.util.HashSet;
import java.util.Set;

public class NewValues {
	private Set<String> newValues;
	private String newValue; 
	
	public NewValues(String newValue){
		this.newValue = newValue; 
		createSet(newValue);
	}
	
	/*The method creates a new set of values that must substitute the actual values in the clocks.*/
	private void createSet(String newVal){
		String a = newValue.substring(newValue.indexOf("tau"+4)).trim(); 
		if (newValue.equals("1")){
			this.newValues = null;
		} else {
			this.newValues = new HashSet<String>(); 
			this.newValues.add(a.substring(0, a.indexOf(",")-1).trim()); 
			a = a.substring(a.indexOf(",")+1);
		}
		
		
	}
	
	public Set<String> getNewValues(){
		return this.newValues; 
	}
}
