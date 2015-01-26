package se.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;

import org.apache.commons.io.FileUtils;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pmis.common.util.TemporaryFileService;
import pmis.common.xml.Xml;
import pmis.common.xml.XmlReader;
import steam.server.Main;

public class SandboxCleaner {
	
	private static Logger logger = LoggerFactory.getLogger( SandboxCleaner.class );

	private static ApplicationContext context;
	private static Properties properties = new Properties();
	private static Class<?> clazz = Main.class;
	
	static {
		LogManager.getLogManager().reset();
		SLF4JBridgeHandler.install();
		java.util.logging.Logger.getLogger("global").setLevel(Level.FINE);
		
		/*context = new ClassPathXmlApplicationContext( new String[] { 
			"spring-common.xml"
		});*/
		
		try {
			properties.load( clazz.getResourceAsStream("/app.properties") );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		if(args == null || args.length == 0 ){
			throw new RuntimeException("arguments missing!");
		}
		
		String saveFolderPath = args[0];
		
		logger.info("Save folder path: " + saveFolderPath );
		File saveFolder = new File(saveFolderPath);
		if( !saveFolder.isDirectory() ){
			throw new RuntimeException("Cannot find the save folder! Please check the path.");
		}
		
		try{
			File sbcFile = new File(saveFolder + "/Sandbox.sbc");
			
			// make backup
			FileUtils.copyFile(sbcFile, new File(saveFolder + "/Sandbox.sbc.bak") );
			
			FileInputStream stream = new FileInputStream( sbcFile );
			// ANSI = Cp1252
			XmlReader reader = Xml.read( new BufferedInputStream( stream ) );
			
			Element root = reader.asElement();
			
			Element ids = root.getChild("Identities");
			List<Element> identities = ids.getChildren("MyObjectBuilder_Identity");
			
			//System.out.println(identities.size());
			
			for (Iterator<Element> iterator = identities.iterator(); iterator.hasNext();) {
				Element obj = (Element) iterator.next();
				//System.out.println(obj);
				if( "Neutral NPC".equals( obj.getChildText("DisplayName") ) ){
					//obj.getParent().removeContent(obj);
					logger.info("Removing " + obj.toString());
					iterator.remove();
				}
			}
			
			logger.info("Removing NonPlayerIdentities");
			Element pids = root.getChild("NonPlayerIdentities");
			pids.removeContent();
			
			//System.out.println(identities.size());
			
			TemporaryFileService fileService = new TemporaryFileService();
			File out = fileService.getNewFile(".sbc");
			StringBuilder buffer = new StringBuilder("<?xml version=\"1.0\"?>");
			buffer.append(Xml.read(root).asString());
			FileUtils.writeStringToFile(out, buffer.toString(), "UTF-8");
			
			DateFormat sfd = new SimpleDateFormat("yyyyMMdd_HHmmss");
			FileUtils.moveFile(out, new File( saveFolderPath + "/Sandbox.sbc.cleaned." + sfd.format( new Date() ) ));
			
			logger.info("Process done!");
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
		
	}
	
}
