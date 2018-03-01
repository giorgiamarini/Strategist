package Message;

public abstract class Message {
	public MessageType type; 
	public String message; 
	
	public Message(String message){
		this.message = message; 
	}
	
	@Override
	public abstract String toString(); 
	public abstract String typeToString(); 
	
	public MessageType getType(){
		return this.type;
	}
	
}
