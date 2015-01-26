package pmis.common.xml;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class XslErrorHandler implements ErrorHandler {
	
	private static final Logger logger = Logger.getLogger( XslErrorHandler.class );
	
	@Override
	public void error( SAXParseException transformerException ) throws SAXParseException {
		logger.error( "TransformationError:ERROR", transformerException);
		throw transformerException;
	}

	@Override
	public void fatalError( SAXParseException transformerException ) throws SAXParseException {
		logger.error( "TransformationError: FATAL", transformerException);
		throw transformerException;
	}

	@Override
	public void warning( SAXParseException transformerException ) throws SAXParseException {
		logger.warn( "TransformationError: WARNING", transformerException);
		throw transformerException;
	}

}
