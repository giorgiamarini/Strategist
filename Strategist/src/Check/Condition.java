package Check;

import java.util.Set;

import Clock.Clock;
import Clock.ClockControl;


/*Condition must be verified to take the transition.*/
public class Condition extends Check{

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

	public boolean isVerified(ClockControl clock){
		boolean verified = true; 
		String[] a; 
		for (String s: this.conditions){
			if ((a = numberOfClocks(clock, s)).length < 2)
				verified = verified && verify(s, clock);
			else verified = verified && verifyTwoClocks(clock, a[0], a[1], s);
		}

		return verified; 
	}

	private boolean verifyTwoClocks(ClockControl clocks, String firstClock, String secondClock, String condition){
		int i;
		boolean verified;
		if (startsWithANumber(condition)){
			if (condition.contains("<")) {
				i = Integer.parseInt(numbers(condition, 0));
				verified = (clocks.getClockWithName(firstClock).getTime() - 
						clocks.getClockWithName(secondClock).getTime() >= i); 
			} else verified = (clocks.getClockWithName(firstClock) == clocks.getClockWithName(secondClock)); 
		} else { 	
			if (condition.contains("<")) {
				i = Integer.parseInt(numbers(condition, firstClock.length()+secondClock.length()+1));
				verified = (clocks.getClockWithName(firstClock).getTime() - 
						clocks.getClockWithName(secondClock).getTime() <= i); 
			} else verified = (clocks.getClockWithName(firstClock) == clocks.getClockWithName(secondClock));}
		return verified; 
	}

	private boolean verify(String condition, ClockControl clock){
		boolean verified = false; 
		for(Clock c: clock.getClocks()){
			if (condition.contains(c.getClockName())){
				switch(symbols(condition)){
				case ">": {
					verified = c.getTime() > Integer.parseInt(numbers(condition, condition.indexOf(">")+1));} 
				break; 
				case ">=": {
					verified = verified && c.getTime() >= Integer.parseInt(numbers(condition, condition.indexOf(">=")+2));} 
				break; 
				case "==": {
					verified = verified && c.getTime() == Integer.parseInt(numbers(condition, condition.indexOf("==")+2));} 
				break; 
				case "<": {
					verified = verified && c.getTime() < Integer.parseInt(numbers(condition, condition.indexOf("<")+1));} 
				break; 
				case "<=": {
					verified = verified && c.getTime() <= Integer.parseInt(numbers(condition, condition.indexOf(">")+2));} 
				break; 
				}
				
			}
		}
		return verified;
	}

	public String[] numberOfClocks(ClockControl clocks, String condition){
		String[] two = new String[2]; 
		int i = 0; 

		for (Clock c: clocks.getClocks()){
			if (condition.contains(c.getClockName())) two[i]=c.getClockName(); 
		}

		return two; 
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
