public class MyLinkedList<X>{
	
	@Override
	public String toString() {
		node<X> t=firstlink;
		System.out.println();
		while(t!=null){
			System.out.print(t.getO()+" ");
			t=t.getNext();
		}
		return null;
	}

	private node<X> firstlink;
	
	MyLinkedList() {
		firstlink = null;
	}
	
	public node<X> getFirstlink() {
		return firstlink;
	}

	public void setFirstlink(node<X> firstlink) {
		this.firstlink = firstlink;
	}

	public boolean isempty() {
		// TODO Auto-generated method stub
		if (firstlink == null)
			return true;
		else
			return false;
	}

	public void add(X obj) {
		node<X> x = new node<X>();
		x.setO(obj);
		x.setNext(firstlink);
		firstlink = x;
	}

	public void delete(X obj) {
		try{
		if (firstlink == null) {
			throw new Exception("List Empty!!!");
		}
		}
		catch(Exception e){
			System.out.println(e);
			return;
		}
		
		node<X> t=this.firstlink;
	
		
		if(t.getO()==obj){
			firstlink=t.getNext();
		}
		
		while(t.getNext()!=null && t.getNext().getO()!=obj)t=t.getNext();
		
		if(t.getNext()!=null){
			t.setNext(t.getNext().getNext());
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
	
	public X delete(){
		try{
			if (firstlink == null) {
				throw new Exception("List Empty!!!");
			}
			}
			catch(Exception e){
				System.out.println(e);
				return null;
			}
		node<X> t=firstlink;
		firstlink=firstlink.getNext();
		return t.getO();
	}
	
	public int size(){
		node<X> x=firstlink;
		int c=0;
		while(x!=null){
			c++;
			x=x.getNext();
		}
		return c;
	}
	
	public boolean contains(X obj){
		node<X> t=firstlink;
		while(t!=null){
			if(t.getO()==obj)return true;
			t=t.getNext();
		}
		return false;
	}

}

class node<X>{
	private X o;
    private node<X> next;
    
	public X getO() {
		return o;
	}
	public void setO(X o) {
		this.o = o;
	}
	public node<X> getNext() {
		return next;
	}
	public void setNext(node<X> next) {
		this.next = next;
	}

	public String toString() {
		return "node [o=" + o + ", next=" + next + "]";
	}

}
