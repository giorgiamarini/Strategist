package Message;

import java.util.Collection;

public class DispatchMessage extends Message{
	public Collection<String> tokens;
	
	public DispatchMessage(String message, Collection<String> tokens){
		super(message); 
		this.type = MessageType.DISPATCH;
		this.tokens = tokens; 
	}

	public Collection<String> getTokens(){
		return this.tokens; 
	}
	
	@Override
	public String toString() {
		String a = this.message; 
		for (String c : this.tokens){
			a = a.concat(c).concat(", ");
		}
		
		return a; 
	}
	
	public String typeToString(){
		return "DISPATCH"; 
	}
}
