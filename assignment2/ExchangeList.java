public class ExchangeList {

	 Linkedlist l;
	 
	 ExchangeList(){
		 l=new Linkedlist();
	 }
	 public void add(Exchange e){
		 l.add(e);
	 }
	 public void delete(Exchange e){
		 l.delete(e);
	 }
	 public boolean isEmpty(){
		 return l.isempty();
	 }
	 public int size(){
		return l.size();
	 }
	 public Exchange geti(int i){
			try {
				if(l.size()-1<i)
			    throw new Exception(i+"th element dont exist");
					} 
				catch (Exception e) {
						System.out.println(e);
						return null;
					}
			//imp we are returning ith element from last
			    i=l.size()-i;
				node x=l.firstlink;
				int c=1;
				while(c!=i && x!=null){
					c++;
					x=x.next;
				}
				return (Exchange) x.o;
	 }
}



