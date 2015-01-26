package pmis.common.xml;

import org.jdom2.Element;
import org.xml.sax.helpers.DefaultHandler;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.JDom2Writer;
import com.thoughtworks.xstream.io.xml.SaxWriter;

/**
 * XStream is a simple library to serialize objects to XML and back again.
 * 
 * @see http://xstream.codehaus.org/
 * 
 * @author Disco
 *
 */
public class XStreamXmlReader extends XmlReader {

	private Object object;
	XStream xstream;
	
	public XStreamXmlReader(Object object) {
		super();
		this.object = object;
		//xstream = new XStream( new StaxDriver( new NoNameCoder() ) ); // StaxDriver puts xml declaration
		xstream = new XStream(new DomDriver("UTF-8", new NoNameCoder())); // DomDriver doesn't put xml declaration
		xstream.autodetectAnnotations( true );
	}
	
	@Override
	protected Element doGetElement() {
		Element element = new Element( "node" );
		JDom2Writer writer = new JDom2Writer( element, new NoNameCoder() );
		xstream.marshal( this.object, writer );
		return (Element)element.getChildren().get(0);
	}

	@Override
	protected String doGetString() {
		return xstream.toXML( this.object );
	}

	@Override
	protected void doParseSource(DefaultHandler handler) {
		SaxWriter writer = new SaxWriter( new NoNameCoder() );
		writer.setContentHandler( handler );
		xstream.marshal( this.object, writer );
	}

//	@Override
//	public <T> T as(Class<T> type) {
//		if( type.isAssignableFrom( this.object.getClass() ) ) {
//			return (T)this.object;
//		} else {
//			throw new IllegalStateException( "Incompatible object" );
//		}
//	}

}
