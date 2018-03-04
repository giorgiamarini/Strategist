package Check;

import java.util.Set;

import Clock.Clock;
import Clock.ClockControl;

public class Guards extends Check {

	public Guards(String s){
		super(s); 
		putGuards(this.condition);
	}

	public void putGuards(String guards){
		guards = guards.substring(0, guards.indexOf(", tau")-1);
		while(!guards.isEmpty()){
			this.conditions.add(guards.substring(0, guards.indexOf("&&")).replace("&&", "").trim());
			guards = guards.substring(guards.indexOf("&&")+2).trim(); 
		}
	}

	private boolean verify(Clock clock) {
		boolean verified = false; 
		for (String c : this.conditions){
			if (c.contains(clock.getClockName())){
				switch(symbols(c)){
				case ">": {
					verified = verified && clock.getTime() > Integer.parseInt(numbers(c, c.indexOf(">")+1));} 
				break; 
				case ">=": {
					verified = verified && clock.getTime() >= Integer.parseInt(numbers(c, c.indexOf(">=")+2));} 
				break; 
				case "==": {
					verified = verified && clock.getTime() == Integer.parseInt(numbers(c, c.indexOf("==")+2));} 
				break; 
				case "<": {
					verified = verified && clock.getTime() < Integer.parseInt(numbers(c, c.indexOf("<")+1));} 
				break; 
				case "<=": {
					verified = verified && clock.getTime() <= Integer.parseInt(numbers(c, c.indexOf(">")+2));} 
				break; 
				}
			}
		}
		return verified; 
	}
	public boolean isVerified(ClockControl clock){
		boolean verified = true;
		for (Clock c : clock.getClocks()){
			verified = verified && verify(c); 
		}
		return verified; 
	}
	
	public String getGuard(){
		return this.condition; 
	}

	public Set<String> getGuards(){
		return this.conditions; 
	}

}
