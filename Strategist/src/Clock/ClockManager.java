package Clock;


import java.util.HashSet;
import java.util.Set;

import PlanStatus.PlanStatus;
import Strategy.Strategy;
import it.istc.pst.platinum.executive.dc.PlanExecutionStatus;

public final class ClockManager implements Runnable {
	private boolean complete;
	private PlanStatus istant; 
	private Strategy strategy; 
	private long planClockTime; 
	private long newTime; 
	private Thread thread; 


	public ClockManager(){
		this.complete = false; 
		this.istant = new PlanStatus(new ClockControl(clocks(), this.strategy.getHorizon()), this.strategy.stateVariables()); 
		start(0);
	}
	
	public Set<String> clocks(){
		Set<String> clocks = new HashSet<String>(); 

		clocks.addAll(this.strategy.stateVariables()); 
		clocks.add("plan_clock"); 
		clocks.addAll(this.strategy.getRelations().getRelations());
		
		return clocks; 
	}

	public synchronized void start(long planClockTime){
		if (this.thread == null) {
			this.planClockTime = planClockTime; 
			this.newTime = 0; 
			this.thread = new Thread(this);
			this.thread.start(); 
		}
	}
	
	@Override
	public void run() {
		if (this.newTime!=0){
			this.planClockTime = this.newTime; 
			this.istant.getClocks().uncontrollableJump(newTime);
			this.newTime = 0; 
		}
		
		while (!complete) 
		{ 
			try { 
				Thread.sleep(1000); 
				this.planClockTime = this.istant.increaseClocks();
				this.istant = this.strategy.goOn(istant);

				this.complete = this.istant.getStateVariablesValues().isFinalStatus(); 
			}
			catch (InterruptedException e) { 
				e.printStackTrace(); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		}
	}
	
	public PlanStatus getIstant(){
		return this.istant; 
	}
	
	public void setStateVariables(PlanExecutionStatus status){
		this.istant.getStateVariablesValues().uncontrollableJump(status); 
	}
	
	public long getPlanClockTime(){
		return this.planClockTime; 
	}

	public void setPlanClockTime(long planClockTime){
		this.newTime = planClockTime;
	}
}