package Message;

import java.util.Collection;

import it.istc.pst.platinum.executive.dc.DCResult;
import it.istc.pst.platinum.executive.dc.DispatchDCResult;

public class DispatchMessage extends Message{
	public Collection<String> tokens;
	
	public DispatchMessage(String message, Collection<String> tokens){
		super(message); 
		this.tokens = tokens; 
	}

	@Override
	public String toString() {
		String a = this.message; 
		for (String c : this.tokens){
			a = a.concat(c).concat(", ");
		}
		
		return a; 
	}
}
