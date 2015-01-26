package pmis.common.xml;

@SuppressWarnings("serial")
public class XmlHttpServiceException extends RuntimeException {

	private int status;

	public XmlHttpServiceException() {
		super();
	}

	public XmlHttpServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public XmlHttpServiceException(String message, int status ) {
		super(message);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public XmlHttpServiceException(Throwable cause) {
		super(cause);
	}

}
