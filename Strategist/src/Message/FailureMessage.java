package Message;

public class FailureMessage extends Message{
	
	public FailureMessage(String message){
		super(message); 
	}

	@Override
	public String toString() {
		return this.message; 
	}
}
