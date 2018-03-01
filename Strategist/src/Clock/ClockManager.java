package Clock;


import java.util.HashSet;
import java.util.Set;

import PlanStatus.PlanStatus;
import Strategy.Strategy;

public final class ClockManager implements Runnable {
	private boolean complete;
	private PlanStatus istant; 
	private Strategy strategy; 
	private long planClockTime; 
	private Thread thread; 
	
	

	public ClockManager(){
		this.complete = false; 
		ClockControl clocks = new ClockControl(clocks(), this.strategy.getHorizon());
		this.istant = new PlanStatus(clocks, this.strategy.stateVariables()); 
		start(0);
	}
	
/*	public void main(String[] args) throws Exception{
		CreatingTheStrategy cts = new CreatingTheStrategy();
		cts.creatingStrategy();

		this.strategy = cts.getStrategy();
		this.istant = new PlanStatus(new ClockControl(clocks(strategy), strategy.getHorizon()), strategy.stateVariables());
	
	}
*/
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
			this.thread = new Thread(this);
			this.thread.start(); 
		}
	}
	
	@Override
	public void run() {
		while (!complete) 
		{ 
			try { 
				Thread.sleep(1000); 
				this.planClockTime = this.istant.increaseClocks();
				istant = this.strategy.goOn(istant);

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
	
	public long getPlanClockTime(){
		return this.planClockTime; 
	}
}