package pmis.common.util;

import java.io.Serializable;

import org.apache.log4j.Logger;


public class TaskLink implements Serializable{
	
	private final static Logger logger = Logger.getLogger( TaskLink.class );
	
	private int progress = 0;
	private int maximum = 1;
	private boolean cancelled = false;
	private String message;
	
	public TaskLink() {
	}

	public String getMessage() throws InterruptedException{
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getProgress() throws InterruptedException{
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public void poll() throws InterruptedException {
		if( cancelled ) throw new InterruptedException();
	}

}
