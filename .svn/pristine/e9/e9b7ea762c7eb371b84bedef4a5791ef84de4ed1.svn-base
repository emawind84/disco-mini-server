package pmis.common.util;

import java.util.Map;

import org.apache.commons.lang3.Validate;

/**
 * Token resolver for <code>Map</code> properties
 * <p>
 * How to use it:
 * <p><blockquote><pre>
 * Map propertiesMap = ...;
 * TokenReplacingReader.replace( content, new MapTokenResolver( propertiesMap ) );
 * </pre></blockquote><p>
 *
 */
public class MapTokenResolver implements ITokenResolver {
	
	private Map<String, String> params;

	public MapTokenResolver(Map<String, String> params) {
		super();
		this.params = params;
	}

	@Override
	public String resolveToken(String tokenName) {
		Validate.notNull( params );
		return String.valueOf( params.get( tokenName ) );
	}

}
