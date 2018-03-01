package Clock;

public class Clock {
	private String clockName; 
	private long tick;  
	
	public Clock(String clockName){
		this.clockName = clockName; 
		this.tick = 0;
	}
	
	public String getClockName(){
		return this.clockName; 
	}
	
	public long getTime(){
		return this.tick; 
	}
	
	public void setTime(long tick){
		this.tick = tick; 
	}
}
