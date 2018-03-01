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
							c.setTime(horizon);
						}
						else 
							c.setTime(Integer.parseInt(v.substring(v.indexOf("=")+1, v.indexOf(",")).trim()));
					}
			}
		}
	}

	public Set<Clock> getClocks(){
		return this.clocks; 
	}
}
