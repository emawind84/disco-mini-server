package pmis.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Token resolver for <code>Properties</code> file properties
 * <p>
 * How to use it:
 * <p><blockquote><pre>
 * TokenReplacingReader.replace( content, new PropertyTokenResolver("path_to_properties_file") );
 * </pre></blockquote><p>
 *
 */
public class PropertyTokenResolver implements ITokenResolver {

	private Properties properties;
	
	public PropertyTokenResolver( String filepath ) throws FileNotFoundException, IOException {
		super();
		this.properties = new Properties();
		properties.load( new BufferedInputStream( new FileInputStream(filepath) ) );
	}

	@Override
	public String resolveToken(String tokenName) {
		return properties.getProperty( tokenName );
	}

}
