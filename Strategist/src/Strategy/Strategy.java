package Strategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import Action.Action;
import PlanStatus.PlanStatus;
import Relations.Relations;
import StateVariables.StateVariables;
import StringFinder.StringFinder;
import ThingsThatMustBeVerified.Condition;

public class Strategy {
	private File strategy;
	private int horizon; 
	private Relations relations; 

	public Strategy(File strategy, int horizon, Relations relations) throws Exception{
		if (strategy.exists()){
			this.strategy = strategy;
			this.horizon = horizon; 
			this.relations = relations; 
		} else 
			throw new FileNotFoundException();
		
		if (!isToWin()) throw new Exception(); 
	}

	@SuppressWarnings("null")
	public Set<String> stateVariables(){
		StringFinder sf = new StringFinder(); 
		String state = sf.find(this.strategy, "State");
		state = state.substring(state.indexOf("(")+1, state.indexOf(")")).trim(); 
		
		Set<String> stateVariables = new HashSet<String>(); 
		while(state!=null || !state.equals("")){
			stateVariables.add(state.substring(0, state.indexOf(".")).trim()); 
			state = state.substring(state.indexOf(" ")).trim(); 
		}
		
		return stateVariables; 
	}
	
	
	public Condition newConditionReleatedToState(StateVariables state, Condition condition) throws FileNotFoundException{
		StringFinder sf = new StringFinder(); 
		int lineNumber = findState(state);
		String line = sf.lineAtNumber(this.strategy, lineNumber);

		if (condition!= null){
			if(condition.getCondition().startsWith("While")){
				while (!line.startsWith("When")){
					lineNumber ++;
					line = sf.lineAtNumber(this.strategy, lineNumber);
				}
			} else {
				if (isThereWhile(lineNumber)){
					while(!line.startsWith("While")){
						lineNumber ++;
						line = sf.lineAtNumber(this.strategy, lineNumber);
					}
				} else {
					while(!line.startsWith("When")){
						lineNumber ++;
						line = sf.lineAtNumber(this.strategy, lineNumber);
					}
				}
			}
		}
		return new Condition(this.getConditionFromLine(lineNumber)); 

	}

	/*The method controls if the state has a while-condition.*/
	public boolean isThereWhile(int line) throws FileNotFoundException{
		StringFinder sf = new StringFinder(); 
		String state = sf.lineAtNumber(this.strategy, line+1);
		boolean isThereWhile = false;

		while(!sf.lineAtNumber(this.strategy, line).isEmpty() && 
				(!state.startsWith("While") || !state.startsWith("State"))){
			if (state.startsWith("While")){
				isThereWhile = true; 
			} 
			line ++; 
		}
		return isThereWhile; 
	}

	/*The method finds the state in the document and returns the line of the document containing the state.*/
	public int findState(StateVariables state) throws FileNotFoundException{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(this.strategy); 
		String line = "";
		String stringState = ""; 
		int lineNumber = 0; 

		while (scanner.hasNextLine()){
			lineNumber ++; 
			line = scanner.nextLine(); 
			if (line.startsWith("State")){
				stringState = getState(lineNumber);
				if (state.isState(stringState)) {
					return lineNumber; 
				}
			}
		} return lineNumber; 

	}

	private String getState(int line) throws FileNotFoundException {
		StringFinder sf = new StringFinder(); 
		String state = sf.lineAtNumber(this.strategy, line);

		while (!state.endsWith(")") || !state.contains(")")){
			line ++; 
			state = state.concat(sf.lineAtNumber(this.strategy, line)); 
		}

		return state.substring(state.indexOf("("+1),state.indexOf(")")).trim(); 
	}

	public String nextState(StateVariables state) throws FileNotFoundException{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(this.strategy);
		String line = ""; 
		boolean isLine = false; 

		while (scanner.hasNextLine() && !isLine){
			line = scanner.nextLine();
			if (line.startsWith("State") && state.isState(line)){
				isLine = true; 
			}

		}

		if (isLine){
			while (!line.contains(")") && scanner.hasNextLine()){ 
				String s = scanner.nextLine(); 
				line = line.concat(s); 

			}
		}
		
		if (line!="")
			line = line.substring(line.indexOf("("), line.indexOf(")")).trim(); 

		return line;
	}

	/*the method controls if the strategy is a strategy to win.*/
	private boolean isToWin() throws FileNotFoundException{
		StringFinder sf = new StringFinder(); 
		return sf.isThere(this.strategy, "Strategy to win");
	}

	public PlanStatus goOn(PlanStatus istant) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(this.strategy));
		String strLine;
		int line = 0; 

		while ((strLine = br.readLine())!= null) {
			line++; 
			if (strLine.startsWith("State") && istant.getStateVariablesValues().isState(getStateFromLine(line))){
				if (getConditionFromLine(line).startsWith("While")){
					istant.evaluateCondition(new Condition(getConditionFromLine(line+1)), 
							new Action(getActionFromLine(line+1), getGuardsFromLine(line+1)));
					line ++; 
					istant.evaluateCondition(new Condition(getConditionFromLine(line+1)), 
							new Action(getActionFromLine(line+1), getGuardsFromLine(line+1)));	
				} else{ 
					istant.evaluateCondition(new Condition(getConditionFromLine(line+1)), 
							new Action(getActionFromLine(line+1), getGuardsFromLine(line+1)));
				}
			}
		}
		br.close();
		return istant; 


	}

	private String getStateFromLine(int i) throws FileNotFoundException{
		StringFinder sf = new StringFinder(); 
		String state = ""; 
		String line = sf.lineAtNumber(this.strategy, i); 
		
		do {
			state.concat(line); 
			line = sf.lineAtNumber(this.strategy, i); 
			i++; 
		} while (!line.contains("you are in")); 
		
		return state.substring(state.indexOf("(")+1, state.indexOf(")")).trim();
	}
	
	
	private String getGuardsFromLine(int i) throws FileNotFoundException{
		StringFinder sf = new StringFinder(); 
		String guards = ""; 
		String line = sf.lineAtNumber(this.strategy, i);

		while(!sf.lineAtNumber(this.strategy, i).contains("{")){
			i ++; 
			line = sf.lineAtNumber(this.strategy, i);
		}

		do {
			guards.concat(line); 
			line = sf.lineAtNumber(this.strategy, i);
			i++; 
		} while (!line.contains("}")); 

		return guards.substring(guards.indexOf("{")+1, guards.indexOf("}")).trim();


	}
	
	/*The method getActionFromLine saves a string containing the action.*/	
	private String getActionFromLine(int i) throws FileNotFoundException {
		StringFinder sf = new StringFinder(); 
		String action = ""; 
		String line = sf.lineAtNumber(this.strategy, i);

		while(!sf.lineAtNumber(this.strategy, i).contains("take transition")){
			i ++; 
			line = sf.lineAtNumber(this.strategy, i);
		}

		do {
			action.concat(line); 
			line = sf.lineAtNumber(this.strategy, i);
			i++; 
		} while (!line.contains("}")); 

		return action.substring(action.indexOf("take transition"), action.indexOf('{')+1);


	}

	/*The method getConditionFromLine saves a string containing the condition.*/
	private String getConditionFromLine(int i) throws FileNotFoundException {
		StringFinder sf = new StringFinder(); 
		String condition = ""; 
		String line = sf.lineAtNumber(this.strategy, i); 

		while (!(sf.lineAtNumber(this.strategy, i).startsWith("When")) || 
				!(sf.lineAtNumber(this.strategy, i).startsWith("While"))) {
			i++; 
			line = sf.lineAtNumber(this.strategy, i);
		} 


		do {
			condition.concat(line); 
			line = sf.lineAtNumber(this.strategy, i);
			i++; 
		} while (!line.contains(","));

		return condition.substring(condition.indexOf(0), condition.indexOf(')')); 

	}


	public File getStrategy(){
		return this.strategy; 
	}
	
	public int getHorizon(){
		return this.horizon; 
	}
	
	public Relations getRelations(){
		return this.relations; 
	}

	public boolean canIMove(StateVariables stateVariablesValues) throws FileNotFoundException {
		return this.nextState(stateVariablesValues)!= ""; 
	}

}
