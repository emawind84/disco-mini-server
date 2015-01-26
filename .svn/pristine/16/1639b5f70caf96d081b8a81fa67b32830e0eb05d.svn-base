package pmis.common.xml;

import java.io.IOException;
import java.io.StringWriter;

import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import pmis.common.util.Errors;

/**
 * Java-based solution for accessing, manipulating, and outputting XML data from Java code.
 * 
 * @see http://www.jdom.org/
 * @see http://www.jdom.org/docs/apidocs.1.1/org/jdom/output/Format.html
 * 
 * @author Disco
 *
 */
public class XmlPrinter {

	private XmlReader reader;
	private String indent = "    ";
	private Format format = Format.getPrettyFormat();

	public XmlPrinter(XmlReader reader) {
		super();
		this.reader = reader;
		
	}
	
	public XmlPrinter indent( String indent ) {
		this.indent = indent;
		return this;
	}
	
	/**
	 * (whitespace beautification)
	 *  
	 * @return
	 */
	public XmlPrinter pretty() {
		format = Format.getPrettyFormat();
		return this;
	}
	
	/**
	 * (whitespace normalization)
	 * 
	 * @return
	 */
	public XmlPrinter compact() {
		format = Format.getCompactFormat();
		return this;
	}
	
	/**
	 * (no whitespace changes)
	 * 
	 * @return
	 */
	public XmlPrinter raw() {
		format = Format.getRawFormat();
		return this;
	}

	public String asString() {
		try {
			format.setIndent( indent );
			XMLOutputter outputter = new XMLOutputter(format);
			StringWriter writer = new StringWriter();
			outputter.output( reader.asElement(), writer );
			return writer.toString();
		} catch (IOException e) {
			throw Errors.wrap( e );
		}
	}
}
