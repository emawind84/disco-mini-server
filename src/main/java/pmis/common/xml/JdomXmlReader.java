package pmis.common.xml;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.SAXOutputter;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.helpers.DefaultHandler;

import pmis.common.util.Errors;

public class JdomXmlReader extends XmlReader{

	private Element element;
	
	public JdomXmlReader(Element element) {
		super();
		this.element = element;
	}

	@Override
	protected void doParseSource(DefaultHandler handler) {
		try {
			SAXOutputter outputter = new SAXOutputter( handler );
			outputter.output( element );
		} catch (JDOMException e) {
			throw Errors.wrap( e );
		}
	}

	@Override
	protected String doGetString() {
		XMLOutputter outputter = new XMLOutputter();
		return outputter.outputString( element );
	}

	@Override
	protected Element doGetElement() {
		return element;
	}

}
