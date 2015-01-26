package pmis.common.xml;

import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import pmis.common.util.Errors;
import pmis.common.util.Streams;

public class SaxXmlReader extends XmlReader {

	private InputSource source;

	public SaxXmlReader(InputSource source) {
		super();
		this.source = source;
	}

	protected void doParseSource( DefaultHandler handler ){
		try {
			SAXParserFactory sfactory = SAXParserFactory.newInstance();
			SAXParser parser = sfactory.newSAXParser();
			parser.parse( source, handler );
		} catch (Exception e) {
			throw Errors.wrap( e );
		}
	}

	@Override
	protected String doGetString() {
		if( source.getByteStream() != null ) {
			return Streams.asString( source.getByteStream(), source.getEncoding() );
		} else {
			return Streams.asString( source.getCharacterStream() );
		}
	}

	@Override
	protected Element doGetElement() {
		try {
			return new SAXBuilder().build( source ).getRootElement();
		} catch (JDOMException e) {
			throw Errors.wrap( e );
		} catch (IOException e) {
			throw Errors.wrap( e );
		}
	}

}
