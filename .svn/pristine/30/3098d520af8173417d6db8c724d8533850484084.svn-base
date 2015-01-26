package pmis.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

/**
 * Token resolver for <code>MessageSource</code>.
 * <p>
 * How to use it:
 * <p><blockquote><pre>
 * TokenReplacingReader.replace( content, new MessageSourceTokenResolver( messageSource ) );
 * </pre></blockquote><p>
 *
 */
public class MessageSourceTokenResolver implements ITokenResolver {
	
	private static final Logger logger = LoggerFactory.getLogger( MessageSourceTokenResolver.class );
	
	private MessageSource messageSource;
	private Locale locale;

	public MessageSourceTokenResolver(MessageSource messageSource, Locale locale ) {
		super();
		this.messageSource = messageSource;
		this.locale = locale;
	}

	@Override
	public String resolveToken(String tokenName) {
		try {
			String value =  messageSource.getMessage( tokenName, null, locale );
			return new String(value.getBytes("ISO8859_1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException();
		} catch (NoSuchMessageException e){
			e.printStackTrace();
			return null;
		}
	}

}
