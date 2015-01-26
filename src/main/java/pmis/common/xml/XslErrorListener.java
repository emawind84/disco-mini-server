package pmis.common.xml;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;

public class XslErrorListener implements ErrorListener {
	
	private static final Logger logger = Logger.getLogger( XslErrorListener.class );
	
	@Override
	public void error(TransformerException transformerException ) throws TransformerException {
		logger.error( "TransformationError:ERROR", transformerException);
		throw transformerException;
	}

	@Override
	public void fatalError(TransformerException transformerException ) throws TransformerException {
		logger.error( "TransformationError: FATAL", transformerException);
		throw transformerException;
	}

	@Override
	public void warning(TransformerException transformerException ) throws TransformerException {
		logger.warn( "TransformationError: WARNING", transformerException);
		throw transformerException;
	}

}
