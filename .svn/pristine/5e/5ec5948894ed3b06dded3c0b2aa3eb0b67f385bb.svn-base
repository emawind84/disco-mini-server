package pmis.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

/**
 * Utility class for manage temporary files.<p>
 * 
 * Please read readme file for more information.
 * 
 */
public class TemporaryFileService {
	
	private static final Logger logger = Logger.getLogger(TemporaryFileService.class);
	
	/**
	 * System-dependent default temporary-file directory will be used as default value.<br> 
	 * The default temporary-file directory is specified by the system property java.io.tmpdir<br>
	 * The value can be set as property in the spring xml file in order to use property dependency injection.
	 */
	private String temporaryPath = System.getProperty("java.io.tmpdir");
	
	/**
	 * Define for how long the files in the temporary folder must be kept before they are deleted.<br>
	 * This time is represented in seconds.<br>
	 * The value can be set as property in the spring xml file in order to use property dependency injection.
	 */
	private int maxAge = 864000;
	
	public String putFile(File file) throws Exception {
		return putFile( file, null );
	}
	
	public String putFile(File file, String fileExt) throws IOException  {
		String fileId = generateFileId()+ ( fileExt != null ? "."+fileExt : "" );
		File tmp = new File( temporaryPath + "/" + fileId );
		tmp.createNewFile();
	
		logger.debug( "Creating file "+tmp.getName()+", size "+ tmp.length() );
		FileUtils.copyFile( file, tmp );
		
		return tmp.getName();
	}
	
	/**
	 * Create a new temporary file given a file extension (.tmp).<br>
	 * The file name will be generated automatically.
	 * 
	 * @param fileExtension
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public File getNewFile(String fileExtension) throws IOException {
		return File.createTempFile(generateFileId(), fileExtension, new File(temporaryPath) );
	}
	
	/**
	 * Create a new temporary file given a file extension and a file name.<br>
	 * For general use {@link #getNewFile(String)} method should be used instead.
	 * 
	 * @param fileExtension
	 * @param fileName
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public File getNewFile(String fileExtension, String fileName) throws IOException {
		File tmpfile = File.createTempFile(fileName, fileExtension, new File(temporaryPath) );
		
		String oriname = fileName;
		if( fileExtension != null ) {
			oriname += fileExtension;
		} else {
			oriname += ".tmp";
		}
		File renamedFile = new File( temporaryPath + "/" + oriname );
				
		if( !renamedFile.exists() ) {
			if(tmpfile.renameTo(renamedFile)){
				return renamedFile; 
			}
		}
		
		return tmpfile;
	}
	
	public File getFile(String fileId){
		Validate.notBlank( fileId, "fileId cannot be null" );
		return new File(temporaryPath+"/"+fileId);
	}
	
	public InputStream getFileAsStream(String fileId) throws FileNotFoundException{
		Validate.notBlank( fileId, "fileId cannot be null" );
		return new FileInputStream( getFile(fileId) );
	}
	
	public void cleanUp(){
		logger.debug("Start cleaning temporary files...");
		
		File tempDir = new File( temporaryPath );
		for( File file : tempDir.listFiles() ) {
			if( ( new Date().getTime() - file.lastModified() ) / 1000 > maxAge ) {
				file.delete();
			}
		}
		logger.debug("Clean up temporary files terminated");
	}
	
	private String generateFileId() {
		return Strings.generateHexId(6)
		+"-"+Strings.generateHexId(6)
		+"-"+Strings.generateHexId(6)
		+"-"+Strings.generateHexId(6)
		+"-"+Strings.generateHexId(6)
		+"-"+Strings.generateHexId(6);
	}
	
	public String getTemporaryPath() {
		return temporaryPath;
	}

	public void setTemporaryPath(String temporaryPath) {
		File file = new File(temporaryPath);
		if(!file.isDirectory()) {
			try {
				logger.debug("Creating "+file.getAbsolutePath()+" directories" );
				file.mkdirs();
			}catch (Exception e) {
				logger.debug( "Cannot create the directory: "+file.getAbsolutePath() );
				e.printStackTrace();
			}
		}
		this.temporaryPath = file.getAbsolutePath();
	}
	
	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}
}