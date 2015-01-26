package pmis.common.xml;

import java.io.InputStream;
import java.io.StringReader;

import org.jdom2.Element;
import org.xml.sax.InputSource;


/**
 * Java-based solution for accessing, manipulating, and outputting XML data from Java code. 
 * 
 * @author Disco
 *
 */
public class Xml {
	
	public static XmlReader read( String xml ) {
		return new SaxXmlReader( new InputSource( new StringReader( xml ) ) );
	}

	public static XmlReader read( InputStream stream ) {
		return new SaxXmlReader( new InputSource( stream ) );
	}

	public static XmlReader read( Element element ) {
		return new JdomXmlReader( element );
	}
	
	public static XmlReader read( Object object ) {
		if( object instanceof String ) {
			return read( (String)object );
		} else if( object instanceof Element ) {
			return read( (Element)object );
		} else if( object instanceof InputStream ) {
			return read( (InputStream)object );
		} else {
			return new XStreamXmlReader( object );
		}
	}
	
	public static XmlPrinter print( Object object ) {
		return new XmlPrinter( read( object ) );
	}
}
