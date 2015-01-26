package pmis.common.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


public class Transformer {
	
	@Deprecated
	public static void chainStylesheets(InputStream source, Writer wr,  String ... transformersPath) throws Exception {

			SAXTransformerFactory stfactory = ((SAXTransformerFactory)TransformerFactory.newInstance());
			TransformerHandler firstHandler = null;
			TransformerHandler currentHandler = null;
			TransformerHandler prevHandler = null;
			Result currentResult = null;
			
			for ( String transf : transformersPath ) {
				
				if ( prevHandler != null  ) {
					prevHandler.setResult( currentResult );
				}
				prevHandler = currentHandler;
				currentHandler = stfactory.newTransformerHandler( new StreamSource( Transformer.class.getResourceAsStream( transf ) ) ); 
				currentResult = new SAXResult( currentHandler );
				if ( firstHandler == null ) {
					firstHandler = currentHandler;
				}
			}
			if ( prevHandler != null  ) {
				prevHandler.setResult( currentResult );
			}
			currentHandler.setResult( new StreamResult( wr ) );
		
			XMLReader reader = null;

			try {
				SAXParserFactory factory = SAXParserFactory.newInstance();
				factory.setNamespaceAware(true);
				SAXParser jaxpParser = factory.newSAXParser();
				reader = jaxpParser.getXMLReader();

			} catch (ParserConfigurationException ex) {
				throw new SAXException(ex);
			} catch (FactoryConfigurationError ex1) {
				throw new SAXException(ex1.toString());
			} catch (NoSuchMethodError ex2) {
			}
			if (reader == null)
				reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(firstHandler);
			reader.parse( new InputSource( source ) );
	}

}
