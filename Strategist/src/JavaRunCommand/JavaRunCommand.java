package JavaRunCommand;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class JavaRunCommand {
	private Path pathFile;

	public File runCommandVTGA(String fileName, File strategy) throws FileNotFoundException {
		setPathFile(FileSystems.getDefault().getPath(fileName)); 

		try {
			Process p = Runtime.getRuntime().exec("/verifytga -t0 "+this.pathFile.toString());
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new FileWriter (strategy));
			while((br.readLine())!=null){
				bw.write(br.readLine()); // You can also use append.
			}

			bw.close(); 
			p.destroy();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return strategy; 
	}


    public void setPathFile(Path p){
    	this.pathFile = p; 
    }
    
}
