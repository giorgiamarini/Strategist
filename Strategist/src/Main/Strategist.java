package Main;

import java.io.FileNotFoundException;

import Clock.ClockManager;
import it.istc.pst.platinum.executive.dc.DCResult;
import it.istc.pst.platinum.executive.dc.PlanExecutionStatus;


public class Strategist {
	public static ClockManager time; 
	
	
	public static DCResult evaluate(PlanExecutionStatus status) {
		time.setPlanClockTime(status.getTime());
		return time.getIstant().getMessage();
		
	}


	public static boolean notify(PlanExecutionStatus status) throws FileNotFoundException {
		return time.uncontrollable(status);
		
	}
}
