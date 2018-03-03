package JavaRunCommand;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaRunCommand {
	private Path pathFile;

	public File runCommandVTGA(String fileName, File strategy) throws FileNotFoundException {
		setPathFile(FileSystems.getDefault().getPath(fileName)); 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			Runtime.getRuntime().exec("verifytga -t0 "+(this.pathFile.toString())+"> strategy.txt");
			
			PrintStream ps = new PrintStream(baos);
			// IMPORTANT: Save the old System.out!
			PrintStream old = System.out;
			// Tell Java to use your special stream
			System.setOut(ps);
			// Print some output: goes to your special stream
			Runtime.getRuntime().exec("pwd");
			// Put things back
			System.out.flush();
			System.setOut(old);
			// Show what happened

			/*	Path path = Paths.get("strategy.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new FileWriter (strategy));
			while((br.readLine())!=null){
				bw.write(br.readLine()); // You can also use append.
			}

			bw.close(); 
			p.destroy();
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Paths.get(baos.toString(), "strategy.txt").toFile(); 
	}


	public void setPathFile(Path p){
		this.pathFile = p; 
	}

}
