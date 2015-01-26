package pmis.common.xml;

import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import pmis.common.util.Errors;

public class WriterContentHandler implements ContentHandler {
	private final static Logger logger = Logger.getLogger( WriterContentHandler.class );

	private Writer writer;
	private String lineSeparator;

	public WriterContentHandler( Writer writer ) {
		lineSeparator = System.getProperty( "line.separator" );
		this.writer = writer;
	}

	private void print( String string ) {
		try {
			writer.write( string );
		} catch ( IOException e ) {
			Errors.lower( e );
		}
	}
	
	private void print( char[] cahars ) {
		try {
			writer.write( cahars );
		} catch ( IOException e ) {
			Errors.lower( e );
		}
	}

	private void println( char[] cahars ) {
		try {
			writer.write( cahars );
		} catch ( IOException e ) {
			Errors.lower( e );
		}
		println();
	}

	private void println( String string ) {
		try {
			writer.write( string );
		} catch ( IOException e ) {
			Errors.lower( e );
		}
		println();
	}

	private void println() {
		try {
			writer.write( lineSeparator );
		} catch ( IOException e ) {
			Errors.lower( e );
		}
	}

	protected char[] XmlUnquote( char[] chars ) {
		StringBuilder sb = new StringBuilder( chars.length );
		StringBuilder ampersand = null;
		StringBuilder trace = null;
		if ( logger.isTraceEnabled() ) {
			trace = new StringBuilder( chars.length );
		}
		for ( char ch : chars ) {
			if ( logger.isTraceEnabled() ) {
				trace.append( ch );
			}
			if ( ch == '&' ) {
				if ( ampersand != null ) {
					sb.append( ampersand );
				}
				ampersand = new StringBuilder( chars.length );
				ampersand.append( ch );
			} else if ( ch == ';' ) {
				if ( ampersand != null ) {
					ampersand.append( ch );
					String temp = ampersand.toString();
					if ( temp.equals( "&amp;" ) ) {
						sb.append( '&' );
					} else if ( temp.equals( "&lt;" ) ) {
						sb.append( '<' );
					} else if ( temp.equals( "&gt;" ) ) {
						sb.append( '>' );
					} else if ( temp.equals( "&quot;" ) ) {
						sb.append( '"' );
					} else if ( temp.equals( "&apos;" ) ) {
						sb.append( "'" );
					} else {
						sb.append( ampersand );
					}
					ampersand = null;
				} else {
					sb.append( ch );
				}
			} else {
				if ( ampersand == null ) {
					sb.append( ch );
				} else {
					ampersand.append( ch );
				}
			}
		}
		if ( ampersand != null ) {
			sb.append( ampersand );
		}
		if ( logger.isTraceEnabled() ) {
			//logger.debug( "char[] originale = '{}', lunghezza = '{}", trace.toString(), trace.length() );
			//logger.debug( "char[] trasformato = '{}', lunghezza = '{}'", sb.toString(), sb.length() );
		}
		sb.trimToSize();
		return sb.toString().toCharArray();
	}

	@Override
	public void characters( char[] ch, int start, int length ) throws SAXException {
		StringBuilder sb = new StringBuilder( length );
		sb.append( ch, start, length );

		// TODO da gestire uri e localName
		if ( logger.isTraceEnabled() ) {

			//logger.debug( "char[] = '{}' ,", sb.toString() );
		}
		print( XmlUnquote( sb.toString().toCharArray() ) );
	}

	@Override
	public void startElement( String uri, String localName, String qName, Attributes atts ) throws SAXException {
		StringBuilder sb = new StringBuilder( 100 );
		sb.append( "<" ).append( qName );
		if ( atts != null ) {
			int len = atts.getLength();
			for ( int index = 0; index < len; index++ ) {
				sb.append( " " ).append( atts.getQName( index ) ).append( "=\"" ).append( atts.getValue( index ) ).append( '"' );
				;
			}
		}
		sb.append( ">" );
		print( sb.toString() );
	}

	@Override
	public void endElement( String uri, String localName, String qName ) throws SAXException {
		// TODO da gestire uri e localName
		StringBuilder sb = new StringBuilder( 20 );
		sb.append( "</" ).append( qName ).append( ">" );
		println( sb.toString() );
	}

	@Override
	public void startPrefixMapping( String prefix, String uri ) throws SAXException {
		logger.debug( "startPrefixMapping( String prefix, String uri )" );
		// try {
		// incompatible();
		// } catch (Exception e) {
		// Errors.lower( e );
		// }
	}

	@Override
	public void endPrefixMapping( String prefix ) throws SAXException {
		logger.debug( "setDocumentLocator( Locator locator )" );
		// try {
		// incompatible();
		// } catch (Exception e) {
		// Errors.lower( e );
		// }

	}

	@Override
	public void ignorableWhitespace( char[] ch, int start, int length ) throws SAXException {
		logger.debug( "ignorableWhitespace( char[] ch, int start, int length )" );
		try {
			incompatible();
		} catch ( Exception e ) {
			Errors.lower( e );
		}
	}

	@Override
	public void processingInstruction( String target, String data ) throws SAXException {
		logger.debug( "processingInstruction( String target, String data )" );
		try {
			incompatible();
		} catch ( Exception e ) {
			Errors.lower( e );
		}

	}

	@Override
	public void setDocumentLocator( Locator locator ) {
		logger.debug( "setDocumentLocator( Locator locator )" );
		try {
			incompatible();
		} catch ( Exception e ) {
			Errors.lower( e );
		}
	}

	@Override
	public void skippedEntity( String name ) throws SAXException {
		logger.debug( "skippedEntity( String name )" );
		try {
			incompatible();
		} catch ( Exception e ) {
			Errors.lower( e );
		}
	}

	@Override
	public void startDocument() throws SAXException {
		logger.debug( "startDocument()" );
		// try {
		// incompatible();
		// } catch (Exception e) {
		// Errors.lower( e );
		// }
	}

	@Override
	public void endDocument() throws SAXException {
		logger.debug( "endDocument()" );
		// try {
		// incompatible();
		// } catch (Exception e) {
		// Errors.lower( e );
		// }

	}

	protected void incompatible() {
		throw new RuntimeException( "Forbidden method" );
	}
}
