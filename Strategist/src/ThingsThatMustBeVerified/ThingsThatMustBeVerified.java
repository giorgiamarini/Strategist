package ThingsThatMustBeVerified;

import java.util.HashSet;
import java.util.Set;

import Clock.Clock;
import Clock.ClockControl;

public abstract class ThingsThatMustBeVerified {
	protected String condition; 
	protected Set<String> conditions; 
	
	public ThingsThatMustBeVerified(String condition){
		this.condition = condition; 
		this.conditions = new HashSet<String>();
	}
	
	/*This method controls if the set of conditions is verified on the plan.*/
	public boolean isVerified(ClockControl clock){
		boolean verified = true;
		for (Clock c : clock.getClocks()){
			verified = verified && verify(c); 
		}
		return verified; 
	}
	
	
	private boolean verify(Clock clock) {
		boolean verified = false; 
		for (String c : this.conditions){
			if (c.contains(clock.getClockName())){
				switch(symbols(c)){
				case ">": {
					verified = clock.getTime() > Integer.parseInt(numbers(c, c.indexOf(">")+1));} 
				break; 
				case ">=": {
					verified = clock.getTime() >= Integer.parseInt(numbers(c, c.indexOf(">=")+2));} 
				break; 
				case "==": {
					verified = clock.getTime() == Integer.parseInt(numbers(c, c.indexOf("==")+2));} 
				break; 
				case "<": {
					verified = clock.getTime() < Integer.parseInt(numbers(c, c.indexOf("<")+1));} 
				break; 
				case "<=": {
					verified = clock.getTime() <= Integer.parseInt(numbers(c, c.indexOf(">")+2));} 
				break; 
				}
			}
		}
		return verified; 
	}

	protected void adjust(){
		for (String c : this.conditions){
			if (startsWithANumber(c) && (c.contains("<") || c.contains("<="))){
				String a = c; 
				String symbol = symbols(c); 
				if (symbol.equals("<=")) symbol = ">=";
				
				a = a.substring((a.indexOf(symbols(a)))+symbols(a).length()).concat(symbol) + numbers(c, 0); 
			}
		}
	}
	
	private String symbols(String c){
		if (c.contains("<")){
			if (c.contains("<="))
				return "<=";
			return "<"; 
		}
			return "=="; 

	}
	
	protected String numbers(String c, int i){
		String a = "";
		while (i<c.length() && isNumber(c.charAt(i))){
				a = a + c.charAt(i); 
				i++; 
		}
		return a; 
	}
	
	private boolean isNumber(char charAt) {
		return charAt=='0'||charAt=='1'||charAt=='2'||charAt=='3'||charAt=='4'||charAt=='5'
				|| charAt=='6'||charAt=='7'||charAt=='8'||charAt=='9';
	}

	private boolean startsWithANumber(String c){
		return c.startsWith("0") || c.startsWith("1") || c.startsWith("2") || 
				c.startsWith("3") || c.startsWith("4")|| c.startsWith("5") || 
				c.startsWith("6") || c.startsWith("7") || c.startsWith("8") || 
				c.startsWith("9");
	}

}
