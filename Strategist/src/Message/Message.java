package Message;

public abstract class Message {
	public String message; 
	
	public Message(String message){
		this.message = message; 
	}
	
	@Override
	public abstract String toString(); 
	
}
