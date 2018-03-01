package Main;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import Clock.ClockControl;
import CreatingTheStrategy.CreatingTheStrategy;
import PlanStatus.PlanStatus;
import Strategy.Strategy;

public class Main {
	
	public static void main(String[] args) throws Exception{
		CreatingTheStrategy cts = new CreatingTheStrategy();
		cts.creatingStrategy();

		Strategy strategy = cts.getStrategy();
		PlanStatus istant = new PlanStatus(new ClockControl(clocks(strategy), strategy.getHorizon()), strategy.stateVariables());
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		for(;;) 
		{ 
			try { 
				Thread.sleep(1000); 
				istant.increaseClocks();
				istant = strategy.goOn(istant);
				
				if (scanner.hasNextLine()){
					System.out.println(istant.getMessage().toString());
				}
			} catch (InterruptedException e) { 
				e.printStackTrace(); 
			} 
		}
	}

	public static Set<String> clocks(Strategy strategy){
		Set<String> clocks = new HashSet<String>(); 

		clocks.addAll(strategy.stateVariables()); 
		clocks.add("plan_clock"); 
		clocks.addAll(strategy.getRelations().getRelations());
		
		return clocks; 
	}
}