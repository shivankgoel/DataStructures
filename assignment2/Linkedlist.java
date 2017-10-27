public class Linkedlist {

	node firstlink;

	Linkedlist() {
		firstlink = null;
	}

	public boolean isempty() {
		// TODO Auto-generated method stub
		if (firstlink == null)
			return true;
		else
			return false;
	}

	public void add(Object obj) {
		node x = new node();
		x.o=obj;
		x.next = firstlink;
		firstlink = x;
	}

	public void delete(Object obj) {
		try{
		if (firstlink == null) {
			throw new Exception("List Empty!!!");
		}
		}
		catch(Exception e){
			System.out.println(e);
			return;
		}
		
		node t=this.firstlink;
		
		if(t.o==obj){
			firstlink=t.next;
		}
		
		while(t.next!=null && t.next.o!=obj)t=t.next;
		
		if(t.next!=null){
			t.next=t.next.next;
		}
		else{
		try{
			throw new Exception("Element to be deleted not found!!!");
		}
		catch(Exception e){
			System.out.println(e);
			return;
		}
		}
	}
	
	public int size(){
		node x=firstlink;
		int c=0;
		while(x!=null){
			c++;
			x=x.next;
		}
		return c;
	}
	
}

class node {

	Object o;
    node next;
	public String toString() {
		return "node [o=" + o + ", next=" + next + "]";
	}

}
