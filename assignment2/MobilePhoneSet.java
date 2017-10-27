public class MobilePhoneSet {
	
	Myset a;
	MobilePhoneSet(){
		a=new Myset();
	}
	MobilePhoneSet(Myset x){
		a=x;
	}
	public boolean isEmpty(){
		return a.isEmpty();
	}
	public boolean isMember(MobilePhone m){
		return a.isMember(m);
	}
	
	public void Insert(MobilePhone m){
		a.Insert(m);
	}
	public void Delete(MobilePhone m){
		a.Delete(m);
	}
	public MobilePhoneSet Union(MobilePhoneSet x){
		Myset m=this.a.Union(x.a);
		MobilePhoneSet q=new MobilePhoneSet(m);
		return q;
	}
	public MobilePhoneSet Intersection(MobilePhoneSet x){
		Myset m=this.a.Intersection(x.a);
		MobilePhoneSet q=new MobilePhoneSet(m);
		return q;
	}

}
