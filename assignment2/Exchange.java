public class Exchange {
	int id;
	Exchange par;
	ExchangeList childlist;
	MobilePhoneSet mset;
	
	Exchange(int number){
		childlist=new ExchangeList();
		mset=new MobilePhoneSet();
		id=number;
		par=null;
	}
	public Exchange parent(){
		return this.par;
	}
	public int numChildren(){
		return this.childlist.size();
	}
	public Exchange child(int i){
		return this.childlist.geti(i);
	}
	public boolean isRoot(){
		return (this.par==null);
	}
	public RoutingMapTree subtree(int i){
		Exchange x=this.child(i);
		RoutingMapTree y=new RoutingMapTree();
		y.root=x;
		return y;
	}
	public MobilePhoneSet residentSet(){
		return mset;
	}
	
}
