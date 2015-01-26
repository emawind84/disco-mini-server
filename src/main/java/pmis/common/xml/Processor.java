package pmis.common.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.core.io.Resource;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class Processor {

	private List<TransformerHandler> transformers = new ArrayList<TransformerHandler>();
	private Result result;
	SAXTransformerFactory transformerFactory;
	SAXParserFactory parserFactory;
	
	public Processor() {
		super();
		transformerFactory = ((SAXTransformerFactory)TransformerFactory.newInstance());
		parserFactory = SAXParserFactory.newInstance();
	}
	
	public Processor withNamespaces() {
		parserFactory.setNamespaceAware( true );
		return this;
	}
	
	public Processor setResult( Object object ) {
		if( object instanceof ContentHandler ) {
			this.result = new SAXResult( (ContentHandler)object );
		
		} else if( object instanceof OutputStream ) {
			this.result = new StreamResult( (OutputStream)object );
			
		} else if( object instanceof Writer ) {
			this.result = new StreamResult( (Writer)object );
			
		} else if( object instanceof File ) {
			this.result = new StreamResult( (File)object );
			
		} else {
			throw new IllegalArgumentException( "Unknown result object: " + object != null ? object.getClass().getName() : "null" );
		}

		return this;
	}
	
	public Processor addTransformer( InputStream stream ) throws Exception {
		return addTransformer( new StreamSource( stream ) );
	}

	public Processor addTransformer( File file ) throws Exception {
		return addTransformer( new StreamSource( file ) );
	}
	
	public Processor addTransformer( Resource resource ) throws Exception {
		return addTransformer( new StreamSource( resource.getInputStream() ) );
	}

	public Processor addTransformer( Source source ) throws Exception {
		TransformerHandler transformer = transformerFactory.newTransformerHandler( source );
		transformers.add( transformer );
		return this;
	}
	
	public void parse( InputStream stream ) throws Exception {
		parse( new InputSource( stream ) );
	}
	
	public void parse( File file ) throws Exception {
		parse( new InputSource( new FileInputStream( file ) ) );
	}
	
	public void parse( Resource resource ) throws Exception {
		parse( new InputSource( resource.getInputStream() ) );
	}
	
	public void parse( InputSource source ) throws Exception {
		getReader().parse( source );		
	}
	
	protected XMLReader getReader() throws SAXException, ParserConfigurationException {
		XMLReader reader = parserFactory.newSAXParser().getXMLReader();

		if( transformers.size() > 0 ) {
			for( int i = 0; i < transformers.size(); i++ ) {
				if( i < ( transformers.size() - 1 ) ) {
					transformers.get( i ).setResult( new SAXResult( transformers.get( i + 1 ) ) );
				} else {
					transformers.get( i ).setResult( this.result );
				}
			}
			reader.setContentHandler( transformers.get( 0 ) );
		
		} else if( result instanceof SAXResult ) {
			reader.setContentHandler( ((SAXResult)result).getHandler() );
		}
		
		if( reader.getContentHandler() == null ) {
			throw new IllegalStateException( "Missing ContentHandler" );
		}
		
		return reader;
	}
}
