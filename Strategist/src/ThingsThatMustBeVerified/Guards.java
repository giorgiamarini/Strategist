package ThingsThatMustBeVerified;

import java.util.Set;

public class Guards extends ThingsThatMustBeVerified {
	
	public Guards(String s){
		super(s); 
		putGuards(this.condition);
	}

	public void putGuards(String guards){
		guards = guards.substring(0, guards.indexOf("tau")-1);
		while(!guards.isEmpty()){
			this.conditions.add(guards.substring(0, guards.indexOf("&&")).replace("&", "").trim());
			guards = guards.substring(guards.indexOf("&&")).trim(); 
		}
	}
	
	public String getGuard(){
		return this.condition; 
	}

	public Set<String> getGuards(){
		return this.conditions; 
	}
	
}
