package ThingsThatMustBeVerified;

import java.util.Set;


/*Condition must be verified to take the transition.*/
public class Condition extends ThingsThatMustBeVerified{
	
	public Condition(String condition){
		super(condition); 
		setOfConditions(); 
	}
 
	private void setOfConditions(){
		String condition1 = this.condition;
		
		if (condition1.startsWith("While")){
			condition1 = condition1.substring(17).trim(); 
		} else { 
			if (condition1.startsWith("When")){
				condition1 = condition1.substring(16).trim();
				}		
		}
		
		while (!condition1.isEmpty()){
			this.conditions.add(condition1.substring(0, condition1.indexOf("&")).trim()); 
			condition1 = condition1.substring(condition1.indexOf("&")).substring(1).trim(); 
		}
		
		adjust();
		
	}

	public long getPlanClock(){
		int a = 0;
		for (String i : this.conditions){
			if (i.contains("plan_clock"))
				 a = Integer.parseInt(numbers(i, 0));
		}
		return a; 
	}
	
	public Set<String> getConditions(){
		return this.conditions; 
	}
	public String getCondition(){
		return this.condition; 
	}

}
