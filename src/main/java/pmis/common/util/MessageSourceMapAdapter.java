package pmis.common.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

public class MessageSourceMapAdapter extends MapAdapter<MessageSource, String> {

	private Locale locale;

	public MessageSourceMapAdapter(MessageSource container, Locale locale) {
		super(container);
		this.locale = locale;
	}

	@Override
	public String get(MessageSource container, String key) {
		if (Strings.isBlank(key)) {
			return null;
		}

		try {
			return new String(container.getMessage(key, null, locale).getBytes("ISO8859_1"), "UTF-8");
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
			return "";
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

}