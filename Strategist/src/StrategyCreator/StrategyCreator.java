package StrategyCreator;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import JavaRunCommand.JavaRunCommand;
import Relations.Relations;
import Strategy.Strategy;
import StringFinder.StringFinder; 

public class StrategyCreator {
	private Strategy strategy;  
	
	/*this method create the file strategy if the input file has .xta extension.*/
	public Strategy createStrategy(String fileName) throws Exception{
		if (okExtension(fileName, ".xta")){ 
			this.strategy = new Strategy(verifytga(fileName), getHorizon(fileName), new Relations(FileSystems.getDefault().getPath(fileName).toFile()));
			return this.strategy;
		}
		return null; 
	}

	/*This method extrapulates the horizon from the initial file.*/
	private int getHorizon(String fileName) {
		File file = FileSystems.getDefault().getPath(fileName).toFile();
		StringFinder sf = new StringFinder(); 
		String line = sf.find(file, "const int H"); 
		return Integer.parseInt(line.substring(line.indexOf("=")+1).trim());	
	}


/*The method verifytga call the instruction  verifytga on the terminal and save the output in a file.*/
	private static File verifytga(String fileName) throws IOException, InterruptedException {
		JavaRunCommand jav = new JavaRunCommand();
		File output = jav.runCommandVTGA(fileName);
		return output; 
	}

	/*The method controls the extension of the input file.*/
	private boolean okExtension(String filePath, String ok){
		return filePath.substring(filePath.lastIndexOf('.')).equals(ok);  //we need an .xta file
	}
	
	public Strategy getStrategy(){
		return this.strategy; 
	}
}
