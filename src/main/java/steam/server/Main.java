package steam.server;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.LogManager;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pmis.common.util.TemporaryFileService;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.servers.GoldSrcServer;

public class Main {
	
	private static Logger logger = LoggerFactory.getLogger( Main.class );

	private static ApplicationContext context;
	private static Properties properties = new Properties();
	private static Class<?> clazz = Main.class;
	
	static {
		LogManager.getLogManager().reset();
		SLF4JBridgeHandler.install();
		java.util.logging.Logger.getLogger("global").setLevel(Level.FINE);
		
		context = new ClassPathXmlApplicationContext( new String[] { 
			"spring-common.xml"
		});
		
		try {
			properties.load( clazz.getResourceAsStream("/app.properties") );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws SteamCondenserException, TimeoutException, IOException {
		if(args == null || args.length == 0 ){
			throw new RuntimeException("arguments missing!");
		}
		
		GoldSrcServer server = new GoldSrcServer(args[0]);
		//GoldSrcServer server = new GoldSrcServer("203.239.21.69:27019");
		server.initialize();
		
		File fout = new File( properties.getProperty("output.home") + "/" + "output.xml" );
		fout.createNewFile();
		
		//TemporaryFileService tempFileService = context.getBean(TemporaryFileService.class);;
		//File fout = tempFileService.getNewFile(".xml");
		//FileOutputStream fos = new FileOutputStream(fout);
		//Writer out = new BufferedWriter( new OutputStreamWriter(fos, Charset.forName("UTF-8") ) );
		
		FileUtils.writeStringToFile(fout, server.toString(), "UTF-8");
		
		System.out.println(server.toString());
		//String datastr = Xml.read(data).asString();
		//System.out.println( datastr );
		
		System.exit(0);
	}
	
}
