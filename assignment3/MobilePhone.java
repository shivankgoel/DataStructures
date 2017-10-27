public class MobilePhone {
	int id;
	boolean ison;
	Exchange loc;
	
	MobilePhone(int number){
		id=number;
		ison=false;
		loc=null;
	}
	
	public int number(){
		return id;
	}
	
	public boolean status(){
		return ison;
	}
	
	public void switchOn(){
		ison=true;
	}
	
	public void switchOff(){
		ison=false;
	}
	
	public Exchange location(){
		return loc;
	}

}
