package Main;

import Clock.ClockManager;
import Message.Message;
import it.istc.pst.platinum.executive.dc.PlanExecutionStatus;


public class Strategist {
	public static ClockManager time; 
	
	
	public static Message evaluate(PlanExecutionStatus status) {
		time.setPlanClockTime(status.getTime());
		return time.getIstant().getMessage();
		
	}


	public static boolean notify(PlanExecutionStatus status) {
		return time.uncontrollable(status);
		
	}
}
