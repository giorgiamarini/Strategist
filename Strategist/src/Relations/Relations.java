package Relations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Relations {
	private Set<String> relations; 

	public Relations(File file) throws FileNotFoundException{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(file); 
		this.relations = new HashSet<String>(); 
		String line = "";
		
		while(scanner.hasNextLine() && !line.contains("Primitive Relations left:")){
			line = scanner.nextLine();
		}
			line = scanner.nextLine(); 
			
		while(scanner.hasNextLine() && !line.startsWith("Warning")){
			relations.add(line.substring(0, line.indexOf(':')).trim());
			line = scanner.nextLine(); 
		}		
	}
	
	public Set<String> getRelations(){
		return this.relations;
	}
}
