package Strategy;

public class StrategyException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public static void error(String command){
		if (command.endsWith("error")){
			return; 
		}
	}

}
