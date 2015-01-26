package pmis.common.xml;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;

import org.springframework.beans.factory.FactoryBean;

public class SaxTransformerFactoryBean implements FactoryBean {

	@Override
	public Object getObject() throws Exception {
	  	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    if (transformerFactory.getFeature(SAXSource.FEATURE) && transformerFactory.getFeature(SAXResult.FEATURE)) { 
	    	if ( errorListener != null ) {
	    		transformerFactory.setErrorListener( errorListener );
	    	}
	    	return ((SAXTransformerFactory) transformerFactory);
	    } else {
	    	throw new RuntimeException( "Unable to create SAXTransformerFactory" );
	    }
	}

	@Override
	public Class getObjectType() {
		return SAXTransformerFactory.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	private ErrorListener errorListener;
	
	public void setErrorListener( ErrorListener errorListener ) {
		this.errorListener = errorListener;
	}

}
