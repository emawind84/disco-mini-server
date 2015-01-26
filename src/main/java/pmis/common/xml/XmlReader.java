package pmis.common.xml;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import org.jdom2.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.JDom2Reader;

public abstract class XmlReader {
		
	public Element asElement() {
		return doGetElement();
	}
	
	protected abstract Element doGetElement();
	
	public String asString() {
		return doGetString();
	}
	
	protected abstract String doGetString();
	
	public Map<String, String> asFlatMap() {
		return asFlatMap( true, true);
	}

	public Map<String, String> asFlatMap( boolean includeRoot, boolean includeAttributes  ) {
		Map<String, String> params = new TreeMap<String, String>();
		doParseSource( new FlattenHandler( params, includeRoot, includeAttributes ) );
		return params;
	}
	
	protected abstract void doParseSource( DefaultHandler handler );
	
	protected class FlattenHandler extends DefaultHandler {

		private Stack<String> elements = new Stack<String>();
		private boolean skipRoot;
		private boolean includeAttributes;
		private Map<String, String> params;
		private StringBuilder characters = new StringBuilder( 512 );
		
		public FlattenHandler(Map<String, String> params, boolean includeRoot, boolean includeAttributes) {
			super();
			this.params = params;
			this.skipRoot = !includeRoot;
			this.includeAttributes = includeAttributes;
		}

		private String getCurrentPath() {
			StringBuilder path = new StringBuilder( 64 );
			Iterator<String> steps = elements.iterator();
			while( steps.hasNext() ) {
				path.append( steps.next() );
				if( steps.hasNext() ) {
					path.append( "." );
				}
			}
			return path.toString();
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			characters.append( Arrays.copyOfRange( ch, start, start + length ) );
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if( !skipRoot ) {
				elements.push( qName );
			} else {
				skipRoot = false;
			}
			
			if( includeAttributes && attributes.getLength() > 0 ) {
				for( int i = 0; i < attributes.getLength(); i++ ) {
					elements.push( attributes.getQName( i ) );
					params.put( getCurrentPath(), attributes.getValue( i ) );
					elements.pop();
				}
			}
			characters.delete( 0, characters.length() );
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if( elements.size() > 0 ) {
				if( characters.length() > 0 ) {
					params.put( getCurrentPath(), characters.toString() );
					characters.delete( 0, characters.length() );
				}
				elements.pop();
			}
		}

	}
	
	@SuppressWarnings("unchecked")
	public <T> T as(Class<T> type) {
		Element element = doGetElement();
		XStream xstream = new XStream();
		xstream.autodetectAnnotations( true );
		xstream.alias( element.getName(), type );
		
		return (T)xstream.unmarshal( new JDom2Reader( element ) );
	}

}
