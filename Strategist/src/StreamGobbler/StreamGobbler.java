package StreamGobbler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

/*A Stream gobbler class take care of reading from a stream and optionally write out to another stream. 
 * Allows for non blocking reads on both stdout and stderr, when invoking a process through Runtime.exec(). 
 * Also, the user can specify a callback that is called whenever anything is read from the stream.
 */
class StreamGobbler implements Runnable {
	
    private InputStream inputStream;
    private Consumer<String> consumer;
 
    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
        this.inputStream = inputStream;
        this.consumer = consumer;
    }
 
    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(inputStream)).lines()
          .forEach(consumer);
    }
}