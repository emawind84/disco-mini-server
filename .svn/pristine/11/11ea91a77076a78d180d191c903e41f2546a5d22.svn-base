package pmis.common.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.CharBuffer;

import org.apache.commons.io.IOUtils;

/**
 * <h1>Replaces strings in streams in Java</h1>
 * 
 * <br>The explanation for how this class works can be found in this article:
 * <br>http://tutorials.jenkov.com/java-howto/replace-strings-in-streams-arrays-files.html 
 * <p>See also Oleg's version of this class, which can use any kind of token marker instead of this, which uses ${...}
 * <br>https://github.com/primefaces-extensions/resources-optimizer-maven-plugin/blob/master/src/main/java/org/primefaces/extensions/optimizerplugin/TokenReplacingReader.java
 * 
 * @author Jakob Jenkov ( http://jenkov.com )
 * @author Disco
 * @see https://github.com/jjenkov/TokenReplacingReader/tree/master/src/main/java/com/jenkov/io
 *
 */
public class TokenReplacingReader extends Reader {

	protected PushbackReader pushbackReader = null;
	protected ITokenResolver tokenResolver = null;
	protected StringBuilder tokenNameBuffer = new StringBuilder();
	protected String tokenValue = null;
	protected int tokenValueIndex = 0;

	public TokenReplacingReader(Reader source, ITokenResolver resolver) {
		this.pushbackReader = new PushbackReader(source, 2);
		this.tokenResolver = resolver;
	}

	public int read() throws IOException {
		if (this.tokenValue != null) {
			if (this.tokenValueIndex < this.tokenValue.length()) {
				return this.tokenValue.charAt(this.tokenValueIndex++);
			}
			if (this.tokenValueIndex == this.tokenValue.length()) {
				this.tokenValue = null;
				this.tokenValueIndex = 0;
			}
		}

		int data = this.pushbackReader.read();
		if (data != '$')
			return data;

		data = this.pushbackReader.read();
		if (data != '{') {
			this.pushbackReader.unread(data);
			return '$';
		}
		this.tokenNameBuffer.delete(0, this.tokenNameBuffer.length());

		data = this.pushbackReader.read();
		while (data != '}') {
			this.tokenNameBuffer.append((char) data);
			data = this.pushbackReader.read();
		}

		this.tokenValue = this.tokenResolver.resolveToken(this.tokenNameBuffer.toString());

		if (this.tokenValue == null) {
			this.tokenValue = "${" + this.tokenNameBuffer.toString() + "}";
		}
		if (this.tokenValue.length() == 0) {
			return read();
		}
		return this.tokenValue.charAt(this.tokenValueIndex++);

	}

	public int read(char cbuf[]) throws IOException {
		return read(cbuf, 0, cbuf.length);
	}
	
	public int read(char cbuf[], int off, int len) throws IOException {
		int charsRead = 0;
		for (int i = 0; i < len; i++) {
			int nextChar = read();
			if (nextChar == -1) {
				if (charsRead == 0) {
					charsRead = -1;
				}
				break;
			}
			charsRead = i + 1;
			cbuf[off + i] = (char) nextChar;
		}
		return charsRead;
	}

	public void close() throws IOException {
		this.pushbackReader.close();
	}

	public boolean ready() throws IOException {
		return this.pushbackReader.ready();
	}
	
	public static String replace( String content, ITokenResolver resolver ) throws IOException {
		StringReader sr = new StringReader( content );
		TokenReplacingReader reader = new TokenReplacingReader( sr , resolver );
		
		//StringBuffer strbuff = new StringBuffer();
		StringBuilder strb = new StringBuilder( content.length() );
		try {
			int data = reader.read();
			while (data != -1) {
				strb.append( (char)data );
				data = reader.read();
			}
		} finally {
			IOUtils.closeQuietly(reader);
		}
		
		return strb.toString();
	}
	
	public static String replace( File file, ITokenResolver resolver ) throws IOException {
		FileReader sr = new FileReader( file );
		TokenReplacingReader reader = new TokenReplacingReader( sr , resolver );
		
		//StringBuffer strbuff = new StringBuffer();
		StringBuilder strb = new StringBuilder();
		try {
			int data = reader.read();
			while (data != -1) {
				strb.append( (char)data );
				data = reader.read();
			}
		} finally {
			IOUtils.closeQuietly(reader);
		}
		
		return strb.toString();
	}

	@Override
	public int read(CharBuffer target) throws IOException {
		throw new UnsupportedOperationException("Method not supported");
	}

	@Override
	public long skip(long n) throws IOException {
		throw new UnsupportedOperationException("Method not supported");
	}

	@Override
	public void mark(int readAheadLimit) throws IOException {
		throw new UnsupportedOperationException("Method not supported");
	}
	
	@Override
	public boolean markSupported() {
		throw new UnsupportedOperationException("Method not supported");
	}
	
	@Override
	public void reset() throws IOException {
		throw new UnsupportedOperationException("Method not supported");
	}
}
