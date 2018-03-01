package Message;

public class WaitMessage extends Message {
	public long until; 
	
	public WaitMessage(String message, long until){
		super(message);
		this.type = MessageType.WAIT; 
		this.until = until; 
	}

	public long getUntil(){
		return this.until; 
	}
	
	@Override
	public String toString() {
		return this.message.concat(Long.toString(this.until));
	}
	
	public String typeToString(){
		return "WAIT"; 
	}
}
