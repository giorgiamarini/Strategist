package JavaRunCommand;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class JavaRunCommand {
	private Path pathFile;

	
    public File runCommandVTGA(String fileName) throws FileNotFoundException {
    	setPathFile(FileSystems.getDefault().getPath(fileName)); 

        try {
        	   Process p = Runtime.getRuntime().exec("/verifytga -t0"+this.pathFile.toString());
        	   InputStream i = p.getInputStream();
        	   InputStreamReader isr = new InputStreamReader(i);
        	   BufferedReader br = new BufferedReader(isr);
        	    File f = new File("d:\\my.txt");
        	    FileWriter fw = new FileWriter(f);            // for appending use (f,true)
        	    BufferedWriter bw = new BufferedWriter(fw);
        	    while((br.readLine())!=null){
        	         bw.write(br.readLine()); // You can also use append.
        	  }
        	    
        	    bw.close(); 
        	    p.destroy();
        	    
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return getStrategyFile(); 
    }
 
    
    public void setPathFile(Path p){
    	this.pathFile = p; 
    }
    
    public File getStrategyFile(){
    	return FileSystems.getDefault().getPath("file.txt").toFile();
    }
}
