package pmis.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class Streams {

	public static void copy( InputStream input, OutputStream output ) {
		try {
			byte buffer[] = new byte[8192];
			int readCount;
			while ( ( readCount = input.read( buffer ) ) != -1 ) {
				output.write( buffer, 0, readCount );
			}
			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			Errors.lower( e );
		}
	}
	
	@Deprecated
	public static String getStringFromStream( InputStream inputStream ) {
		return asString( inputStream );
	}
	
	public static String asString( InputStream inputStream ) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		copy( inputStream, outputStream );
		return outputStream.toString();
	}
	
	public static String asString( InputStream inputStream, String encoding ) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			copy( inputStream, outputStream );
			return outputStream.toString( encoding!=null?encoding:"UTF-8" );
		} catch( UnsupportedEncodingException e ) {
			throw Errors.wrap( e );
		}
	}
	
	public static String asString( Reader reader ) {
		try {
			StringBuilder result = new StringBuilder( 4096 );
			char[] buf = new char[ 1024 ];
			int n;
			while( ( n = reader.read(buf) ) >= 0 ) {
				result.append( buf, 0, n );
			}
			return result.toString();
		} catch (IOException e) {
			throw Errors.wrap( e );
		}
		
	}
	
	public static InputStream fromFile( File file ) {
		try {
			return new FileInputStream( file );
		} catch (FileNotFoundException e) {
			throw Errors.wrap( e );
		}
	}

}
