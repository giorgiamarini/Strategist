package Message;

public class WaitMessage extends Message {
	public long until; 
	
	public WaitMessage(String message, long until){
		super(message);
		this.until = until; 
	}

	@Override
	public String toString() {
		return this.message.concat(Long.toString(this.until));
	}
}
