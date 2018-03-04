package Clock;

import java.util.Set;

public class ClockControl {
	private Set<Clock> clocks; 
	private int horizon;
	
	public ClockControl(Set<String> clocks, int horizon){
		for (String s : clocks){
			this.clocks.add(new Clock(s));
		}
		for (Clock c: this.clocks){
			if (!c.getClockName().contains("clock")){
				c.setClockName(c.getClockName().concat("_clock"));
			}
		}
		
		this.horizon = horizon;
	}

	/*This method increases the values of all the clocks.*/
	public void increaseClocks(){
		for (Clock c : this.clocks)
			c.setTime(c.getTime()+1);
	}
	
	/*The method, having new values that the clock must assume, change the current values of the clock,
	 * reinitializing them if it is necessary.*/
	public void setNewValues(NewValues values){
		if (!(values == null)){

			for (Clock c: this.clocks){
				for (String v : values.getNewValues())
					if (v.contains(c.getClockName())){
						if (v.contains("H")){
							setTimeRelatedToHorizon(c, v);
						}
						else 
							c.setTime(0);
					}
			}
		}
	}

	private void setTimeRelatedToHorizon(Clock c, String newValue){
			if (newValue.contains(c.getClockName())){
				String a = newValue.substring(newValue.indexOf("=")+1).trim();
				if (a.equals("H")) c.setTime(this.horizon);
				else {
					c.setTime(this.horizon + Integer.parseInt(numbers(a, a.indexOf("H"))));
				}
		}
	}
	
	private String numbers(String c, int i){
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
	
	public Clock getPlanClock(){
		Clock c = null; 
		for (Clock a : this.clocks)
			if (a.getClockName().equals("plan_clock")){
				c = a; 
			}
		return c; 
	}
	
	public Set<Clock> getClocks(){
		return this.clocks; 
	}
	
	public void addToClocks(long u){
		for(Clock c : this.clocks){
			c.setTime(c.getTime()+u);
		}
	}
	
	public void subtractToClocks(long u){
		for(Clock c : this.clocks){
			c.setTime(c.getTime()-u);
		}
	}

	public void uncontrollableJump(long newTime) {
		if (this.getPlanClock().getTime() < newTime) 
			addToClocks(newTime-this.getPlanClock().getTime());
		else subtractToClocks(newTime-this.getPlanClock().getTime());
			
		
	}
}
