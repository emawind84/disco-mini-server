package pmis.common.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util class to execute external commands.
 * <p>
 * {@link StreamGobbler} empties any stream passed into it in a separate thread.
 * <br>Is important to note that the error output stream and the standard output stream will be emptied simultaneously
 * <p>
 * Reference:
 * <br>http://www.javaworld.com/article/2071275/core-java/when-runtime-exec---won-t.html
 * <br>http://bryanmarty.com/2012/01/22/process-leaking-file-descriptors/
 * 
 * @author Disco
 *
 */
public class RuntimeUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(RuntimeUtil.class);
	
	public static int getExecuteCommand(String[] command ){
		// OUTPUT STREAM
		OutputStream eos = new ByteArrayOutputStream();
		OutputStream os = new ByteArrayOutputStream();
					
		return getExecuteCommand(command, eos, os);
	}

	public static int getExecuteCommand(String[] command, OutputStream eos, OutputStream os ) {

		Process p = null;
		try {
			Runtime runProcess = Runtime.getRuntime();
			p = runProcess.exec(command);

			// STREAM READER THREADS
			StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR", eos);
			StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT", os);

			// THREADS START
			outputGobbler.start();
			errorGobbler.start();

			// WAIT UNTIL THE PROCESS HAS TERMINATED
			p.waitFor();

			// LOGGING OUTPUT
			if( eos instanceof ByteArrayOutputStream ){
				logger.debug(eos.toString());
			}
			if( os instanceof ByteArrayOutputStream ){
				logger.debug(os.toString());
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return 0;
	}
	
}
