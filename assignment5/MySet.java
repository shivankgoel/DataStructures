public class MySet<X>{
	
	private MyLinkedList<X> l;
	
	MySet(){
		l = new MyLinkedList<X>();
	}

	
	public MyLinkedList<X> getL() {
		return l;
	}

	public void setL(MyLinkedList<X> l) {
		this.l = l;
	}

	public void addElement(X element){
		l.add(element);
	}
	
	public MySet<X> union(MySet<X> otherSet){
		 MySet<X> x=new MySet<X>();
		   node<X> t1=otherSet.l.getFirstlink();
		   while(t1!=null){
			   x.Insert(t1.getO());
			   t1=t1.getNext();
		   }
		   node<X> t2=this.l.getFirstlink();
		   while(t2!=null){
			   if(!x.isMember(t2.getO()))x.Insert(t2.getO());
			   t2=t2.getNext();
		   }
		   return x;
	}

	public MySet<X> intersection(MySet<X> otherSet){
		  MySet<X> x=new MySet<X>();
		   node<X> t1=otherSet.l.getFirstlink();
		   while(t1!=null){
			   if(this.isMember(t1.getO())){
			   x.Insert(t1.getO());
			   }
			   t1=t1.getNext();
		   }
		   return x;
	}
	
	 public void Insert(X o){
		   l.add(o);
	   }
	   
	   public void Delete(X o){
		   l.delete(o);
	   }
	   
	   public boolean isMember(X obj){
		    if(l.getFirstlink()==null)return false;
		    node<X> x=l.getFirstlink();
		    while(x!=null){
		    	if(x.getO()==obj)return true;
		    	x=x.getNext();
		    }
		    return false;
	   }
}
