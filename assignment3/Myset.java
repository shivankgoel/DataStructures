public class Myset {
   Linkedlist l;
   
   Myset(){
	   l=new Linkedlist();
   }
   
   public boolean isEmpty(){
	   return l.isempty();
   }
   
   public boolean isMember(Object obj){
	    if(l.firstlink==null)return false;
	    node x=l.firstlink;
	    while(x!=null){
	    	if(x.o==obj)return true;
	    	x=x.next;
	    }
	    return false;
   }
   

   public void Insert(Object o){
	   l.add(o);
   }
   
   public void Delete(Object o){
	   if(this.isMember(o))
	   l.delete(o);
	   else 
		   try{
	   throw new Exception("Can't Delete-object is not in the myset!!!");
		   }
	   catch(Exception e){
		   System.out.println(e);
	   }
   }
   
   public Myset Union(Myset a){
	   Myset x=new Myset();
	   node t1=a.l.firstlink;
	   while(t1!=null){
		   x.Insert(t1.o);
		   t1=t1.next;
	   }
	   node t2=this.l.firstlink;
	   while(t2!=null){
		   if(!x.isMember(t2.o))x.Insert(t2.o);
		   t2=t2.next;
	   }
	   return x;
   }
   
   public Myset Intersection(Myset a){
	   Myset x=new Myset();
	   node t1=a.l.firstlink;
	   while(t1!=null){
		   if(this.isMember(t1.o)){
		   x.Insert(t1.o);
		   }
		   t1=t1.next;
	   }
	   return x;
   }
}

