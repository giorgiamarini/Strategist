package Message;

public class FailureMessage extends Message{
	
	public FailureMessage(String message){
		super(message); 
		this.type = MessageType.FAILURE; 
		
	}

	@Override
	public String toString() {
		return this.message; 
	}
	
	public String typeToString(){
		return "FAILURE"; 
	}
}
