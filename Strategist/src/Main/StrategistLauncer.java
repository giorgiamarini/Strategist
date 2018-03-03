package Main;

import Clock.ClockManager;
import Strategy.Strategy;
import StrategyCreator.StrategyCreator;


public class StrategistLauncer {

	public static void main(String[] args) throws Exception{
		
		try {
		StrategyCreator sc = new StrategyCreator(); 
		Strategy strategy = sc.createStrategy(args[0]);
		ClockManager time = new ClockManager(strategy);
		time.run(); 
		
		} catch (Exception e) {
			System.out.println("Warning. File not found.");
		}
	}
}
