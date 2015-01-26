package pmis.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream.UnicodeExtraFieldPolicy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

public class FileUtil {
	
	private final static Logger logger = LoggerFactory.getLogger( FileUtil.class );
	
	/**
	 * 디렉토리 전체 삭제.
	 * 
	 * @param f
	 * @param removedir
	 * @return
	 */
	public static void rmdir(File f, boolean removedir) {
		Validate.isTrue( f.isDirectory(), "The file denoted by this abstract pathname is not a directory." );
		
		File[] flist = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isFile()) {
				flist[i].delete();
			}
			else if (flist[i].isDirectory()){
				rmdir(flist[i], true);
			}
		}
		if(removedir)f.delete();
	}

	/**
	 * 디렉토리내 특정 파일을 제외하고 삭제.
	 * 
	 * @param f
	 * @param fname
	 */
	public static void rmdir(File f, String[] fname) {
		Validate.isTrue( f.isDirectory(), "The file denoted by this abstract pathname is not a directory." );
		
		File[] flist = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isFile()) {
				for (int j = 0; j < fname.length && fname[j] != null && !fname[j].equals(""); j++) {
					if (fname[j].equals(flist[i].getName()))
						continue;
					flist[i].delete();
				}
			} else if (flist[i].isDirectory())
				rmdir(flist[i], true);
		}
	}
	
	public static void copy(String source, String target) throws IOException {
		File in = new File(source);
		File out = new File(target);
		copy(in, out);
	}

	/**
	 * 
	 * If a stream has associated channel then the channel is closed as well on stream close.
	 * 
	 * @see {@link FileOutputStream#close}
	 * @see {@link FileInputStream#close}
	 * 
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	public static void copy(File source, File target) throws IOException {
		// Take a look at the source of FileUtils.
		// FileUtils.copyFile will use FileChannel to transfer data.
		FileUtils.copyFile(source, target);
		
		/*FileInputStream inputStream = new FileInputStream(source);
		FileOutputStream outputStream = new FileOutputStream(target);
		
		//stream, channel
		FileChannel fcin = inputStream.getChannel();
		FileChannel fcout = outputStream.getChannel();
		
		try {
			int maxCount = (64 * 1024 * 1024) - (32 * 1024);
			long size = fcin.size();
	        long position = 0;

	        while (position < size) {
	            position += fcin.transferTo(position, maxCount, fcout);
	        }
		} finally {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
		}*/
	}
	
	public static void copyThread( File source, File target, TaskExecutor executor ){
		FileCopy copy = new FileCopy( source, target );
		executor.execute( copy );
	}
	
	private static class FileCopy implements Runnable {
		
		private File in;
		private File out;
		
		public FileCopy(File in, File out) {
			super();
			this.in = in;
			this.out = out;
		}

		@Override
		public void run() {
			logger.debug("File copy thread START");
			try {
				FileUtil.copy(in, out);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				logger.debug( "File copy thread END" );
			}
		}

	}

	public static String sanitizeFileName(String fileName) {
		char[] admissibleChars = { '(', ')', '&', '[', ']', '-' };
		StringBuilder sanitizedName = new StringBuilder();

		for (char c : fileName.toCharArray()) {
			if (ArrayUtils.indexOf(admissibleChars, c) >= 0) {
				sanitizedName.append(c);
			} else if (c == '.' || Character.isJavaIdentifierPart(c)) {
				sanitizedName.append(c);
			} else if (c == '/') {
				sanitizedName.append('_');
			} else if (c == ':') {
				sanitizedName.append('_');
			} else if (c == '*') {
				sanitizedName.append('_');
			} else if (c == '<') {
				sanitizedName.append('_');
			} else if (c == '>') {
				sanitizedName.append('_');
			} else if (c == '"') {
				sanitizedName.append('_');
			} else if (c == ' ') {
				sanitizedName.append('_');
			} else {
				sanitizedName.append('_');
			}
		}

		return sanitizedName.toString();
	}
	
	/**
	 * Create a zip file given an array of {@link File} objects.
	 * <p>
	 * <ul>
	 * <li>language encoding flag: signal that a file name has been encoded using UTF-8</li>
	 * <li>createUnicodeExtraFields: can be used to add an additional UTF-8 encoded file name to the entry's metadata</li>
	 * </ul>
	 * 
	 * Windows "compressed folder" feature doesn't recognize any flag or extra field 
	 * and creates archives using the platforms default encoding 
	 * and expects archives to be in that encoding when reading them.
	 * <p>
	 * The Windows "compressed folder" can't be used if file names are incompatible with the encoding of the target platform
	 * <p>
	 * 
	 * @param files an array of {@link File} objects
	 * @return the file zip generated
	 * @throws IOException
	 * 
	 * @see https://commons.apache.org/proper/commons-compress/zip.html
	 */
	public static File doZip(File[] files) throws IOException {
		ZipArchiveOutputStream zos = null;
		InputStream fis = null;
		
		TemporaryFileService temporaryFileService = new TemporaryFileService();
		//temporaryFileService.setTemporaryPath( PmisConfig.get("upload.temp", System.getProperty("java.io.tmpdir") ) );

		File fzip = temporaryFileService.getNewFile(".zip");
		FileOutputStream fos = new FileOutputStream(fzip);
		
		try{
			zos = new ZipArchiveOutputStream( new BufferedOutputStream( fos ) );
			zos.setEncoding("UTF-8");
			zos.setCreateUnicodeExtraFields( UnicodeExtraFieldPolicy.ALWAYS );
			zos.setLevel( Deflater.BEST_SPEED );
			
			for (int j = 0; j < files.length; j++) {
				File file = files[j];
				if(file.isFile()) {
					// For faster reading you should use BufferedInputStream to wrap any InputStream
					fis = new BufferedInputStream( new FileInputStream(file) );
					ZipArchiveEntry entry = new ZipArchiveEntry( file.getName() );
				
					// begins writing a new zip file and sets the position to the
					// start of data
					zos.putArchiveEntry(entry);
			
					byte[] buf = new byte[1024];
					int len;
					while ((len = fis.read(buf)) > 0) {
						zos.write(buf, 0, len);
					}
					zos.closeArchiveEntry();
					IOUtils.closeQuietly(fis);
				}
			}
			
			zos.finish();
			
		} catch( IOException e ){
			Errors.lower(e);
		} finally {
			IOUtils.closeQuietly(zos);
			IOUtils.closeQuietly(fis);
		}
		
		return fzip;
	}
	
	/**
	 * Create a zip file given an array of file path strings.
	 * 
	 * @param sfiles an array of file path strings
	 * @return the file zip generated
	 * @throws IOException
	 * 
	 * @see {@link #doZip(File[])}
	 */
	public static File doZip(String[] sfiles) throws IOException {
		File[] files = new File[sfiles.length];
		for (int i = 0; i < sfiles.length; i++) {
			files[i] = new File(sfiles[i]);
		}
		return doZip(files);
	}

}