package pmis.common.xml;

import org.jdom2.Element;

public class StandardXmlHttpErrorHandler implements XmlHttpErrorHandler {

	@Override
	public XmlReader handleError( int status, String response ) {
		//TODO: gestire altri errori standard
		Element res = Xml.read( response ).asElement();
		String message = res.getChildTextTrim( "message" );
		if( message == null ) {
			String type = res.getChildTextTrim( "type" );
			if( type == null ) {
				type = "Unknown error";
			}
			message = type + " from service";
		}
		throw new XmlHttpServiceException( message, status );
	}

}
