public class AvlTree<X extends Comparable<X>> {
	public Avltreenode<X> getRoot() {
		return root;
	}
	public void setRoot(Avltreenode<X> root) {
		this.root = root;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String name;
	private Avltreenode<X> root; //
	
	public Avltreenode<X> leftrotate(Avltreenode<X> z){ //rrcase
		Avltreenode<X> y=z.getRightchild();
		z.setRightchild(y.getLeftchild());
		y.setLeftchild(z);

		z.setHeight(Math.max(getheight(z.getLeftchild()),getheight(z.getRightchild()))+1);
		y.setHeight(Math.max(getheight(z),getheight(y.getRightchild()))+1);
		return y;
	}
	public Avltreenode<X> rightrotate(Avltreenode<X> z){ //llcase
		Avltreenode<X> y=z.getLeftchild();
		z.setLeftchild(y.getRightchild());
		y.setRightchild(z);
		z.setHeight(Math.max(getheight(z.getLeftchild()),getheight(z.getRightchild()))+1);
		y.setHeight(Math.max(getheight(z),getheight(y.getLeftchild()))+1);
		return y;
	}
	
	public Avltreenode<X> lrcase(Avltreenode<X> z){
		z.setLeftchild(leftrotate(z.getLeftchild()));
		return rightrotate(z);
	}
	
	public Avltreenode<X> rlcase(Avltreenode<X> z){
		z.setRightchild(rightrotate(z.getRightchild()));
		return leftrotate(z);
	}
	
	public int getheight(Avltreenode<X> n){
		if(n==null)return -1;
		else return n.getHeight();
	}
	public void insert(X obj){
		this.root=insert(this.root,obj);
	}
	public Avltreenode<X> insert(Avltreenode<X> rt,X obj){
		if(rt==null){
			rt =new Avltreenode<X>(obj);
		}
		if(rt.getObj().compareTo(obj)>0){
			rt.setLeftchild(insert(rt.getLeftchild(),obj));
		}
		if(rt.getObj().compareTo(obj)<0){
			rt.setRightchild(insert(rt.getRightchild(),obj));
		}
		rt.setHeight(Math.max(getheight(rt.getRightchild()),getheight(rt.getLeftchild()))+1);
		int diff=getheight(rt.getLeftchild())-getheight(rt.getRightchild());
		
		if(diff>1){
			if(obj.compareTo(rt.getObj())<0){
				return rightrotate(rt);
			}
			else return lrcase(rt);
		}
		if(diff<-1){
			if(obj.compareTo(rt.getObj())>0){
				return leftrotate(rt);
			}
			else return rlcase(rt);
		}
		return rt;
	}
	
	public void delete(X obj){
		this.root=delete(this.root,obj);
	}
	public Avltreenode<X> delete(Avltreenode<X> rt,X obj){
		if(rt==null)return rt;
		if(rt.getObj().compareTo(obj)>0)
			rt.setLeftchild(delete(rt.getLeftchild(),obj));
		else if(rt.getObj().compareTo(obj)<0)
			rt.setRightchild(delete(rt.getRightchild(),obj));
		else{
			if(rt.getLeftchild()==null||rt.getRightchild()==null){
				Avltreenode<X> t=rt.getLeftchild()!=null ? rt.getLeftchild() : rt.getRightchild();
				if(t==null){
					rt=null;
					return rt;
				}
				else{
					rt.setObj(t.getObj());
					if(rt.getLeftchild()==t)rt.setLeftchild(null);
					else if(rt.getRightchild()==t) rt.setRightchild(null);
				}
			}
			else{
				Avltreenode<X> t=rt.getRightchild();
				while(t.getLeftchild()!=null)t=t.getLeftchild();
				rt.setObj(t.getObj());
				rt.setRightchild(delete(rt.getRightchild(),t.getObj()));
			}
		
		}
		
		rt.setHeight(Math.max(getheight(rt.getRightchild()),getheight(rt.getLeftchild()))+1);
		int diff=getheight(rt.getLeftchild())-getheight(rt.getRightchild());
		
		if(diff>1){
			if(obj.compareTo(rt.getObj())<=0){
				return rightrotate(rt);
			}
			else return lrcase(rt);
		}
		if(diff<-1){
			if(obj.compareTo(rt.getObj())>0){
				return leftrotate(rt);
			}
			else return rlcase(rt);
		}
		return rt;
	}
	
	public void traverse(){
		traverse(this.root);
	}
	public void traverse(Avltreenode<X> rt){
		if(rt==null)return;
		traverse(rt.getLeftchild());
		System.out.print(rt.getObj().toString()+" ");
		traverse(rt.getRightchild());
	}
	
	public Avltreenode<X> find(X node){
		return find(this.root,node);
	}
	public Avltreenode<X> find(Avltreenode<X> rt,X node){
		if(rt==null)return null;
		if(rt.getObj()==(node))return root;
		else if(rt.getObj().compareTo(node)>0)return find(rt.getLeftchild(),node);
		else return find(rt.getRightchild(),node);
	}
	
	public Avltreenode<X> inordersuccessor(Avltreenode<X> x){
		if(x.getRightchild()!=null){
			Avltreenode<X> temp=x.getRightchild();
			while(temp.getLeftchild()!=null)temp=temp.getLeftchild();
			return temp;
		}
		Avltreenode<X> temp=this.root;
		Avltreenode<X> succ=null;
		while(temp!=null){
			if(x.getObj().compareTo(temp.getObj())<0){
				succ=temp;
				temp=temp.getLeftchild();
				//System.out.println("hi tempdata "+temp.getObj().toString());
			}
			else if(x.getObj().compareTo(temp.getObj())>0){
				temp=temp.getRightchild();
			}
			else{
				break;
			}
		}
		return succ;
	}
	
	/*public static void main(String[] args) {
		AvlTree<Position> t = new AvlTree<>();
		t.insert(new Position(null,9));
		System.out.println("done1");
		t.insert(new Position(null,5));
		System.out.println("done1");
		t.insert(new Position(null,10));
		//System.out.println("done1");
		t.insert(new Position(null,0));
		//System.out.println("done1");
		t.insert(new Position(null,6));
		//System.out.println("done1");
		t.insert(new Position(null,11));
		//System.out.println("done1");
		t.insert(new Position(null,-1));
		System.out.println("done1");
		t.insert(new Position(null,2));
		//System.out.println("done1");
		t.insert(new Position(null,-2));
		t.insert(new Position(null,-3));
		t.delete(new Position(null,-1));
		t.delete(new Position(null,2));
		t.traverse();
	}*/
}

class Avltreenode<X extends Comparable<X>>{
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public X getObj() {
		return obj;
	}
	public void setObj(X obj) {
		this.obj = obj;
	}
	public Avltreenode<X> getLeftchild() {
		return leftchild;
	}
	public void setLeftchild(Avltreenode<X> leftchild) {
		this.leftchild = leftchild;
	}
	public Avltreenode<X> getRightchild() {
		return rightchild;
	}
	public void setRightchild(Avltreenode<X> rightchild) {
		this.rightchild = rightchild;
	}
	
	Avltreenode(X obj){
		this.obj=obj;
		rightchild=null;
		leftchild=null;
		height=0;
	}
	private X obj;
	private Avltreenode<X> leftchild;
	private Avltreenode<X> rightchild;
	private int height;
	
}