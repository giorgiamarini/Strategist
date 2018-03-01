package Clock;

public class Clock {
	private String clockName; 
	private long tick;  
	
	public Clock(String clockName){
		this.clockName = clockName; 
		this.tick = 0;
	}
	
	public Clock (String clockName, int tick){
		this.clockName = clockName; 
		this.tick = tick; 
	}
	
	public String getClockName(){
		return this.clockName; 
	}
	
	public void setClockName(String newName){
		this.clockName = newName; 
	}
	
	public long getTime(){
		return this.tick; 
	}
	
	public void setTime(long tick){
		this.tick = tick; 
	}
}
