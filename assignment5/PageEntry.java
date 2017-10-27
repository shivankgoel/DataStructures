import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class PageEntry {
	
	private PageIndex ind;
	private String pagename;
	private AvlTree<Wordposition> tree;

	public AvlTree<Wordposition> getTree() {
		return tree;
	}

	public void setTree(AvlTree<Wordposition> tree) {
		this.tree = tree;
	}

	private static String connectors[]={ "a", "an", "the", "they", "these", "for", "is", "are", "of", "or", "and",
			"does", "will", "whose" };
	
	public PageIndex getPageIndex() {
		return ind;
	}

	public void setInd(PageIndex ind) {
		this.ind = ind;
	}

	public String getPagename() {
		return pagename;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}

	PageEntry(String pageName){
		ind=new PageIndex();
		pagename=pageName;
		BufferedReader br=null;
		try{
			try{
			br = new BufferedReader(new FileReader("webpages\\"+pageName));
			}
			catch(Exception e){
				System.out.println("File not found  "+e);
			}
			int c=0;
			String l[]={};
			String line;
			String m;
			
			while ((line = br.readLine()) != null) {
				if(line.equals(""))continue;
				m = line.replaceAll("[{}<>=().,;?#!-\"\'\\[\\]^_]+", " ");
				m.toLowerCase(Locale.ENGLISH);
		    	m.trim();
		    	l=m.split("\\s+");
		    	for(int i=0;i<l.length;i++){
		    		c++;
		    		l[i] = Character.toLowerCase(l[i].charAt(0)) + (l[i].length() > 1 ? l[i].substring(1) : "");
		    		if(l[i].equals("stacks"))l[i]="stack";
		    		if(l[i].equals("applications"))l[i]="application";
		    		if(l[i].equals("structures"))l[i]="structure";
		    		String shivank=l[i].replaceAll("[-:]"," ");
		    		String[] shivanktemp=shivank.split("\\s+");
		    		if(shivanktemp.length>=2){
		    			int k=0;
		    			while(k<2){
		    			if(notbelong(shivanktemp[k])){
			    			Position s=new Position(this, c);
			    			ind.addPositionForWord(shivanktemp[k], s);
			    			//System.out.println("shivank "+shivanktemp[k]);
			    			k++;
			    		}
		    			}
		    		}
		    		else{
		    		if(shivanktemp.length==0)continue;
		    		l[i]=shivanktemp[0];
		    		//System.out.println(l[i]);
		    		if(notbelong(l[i])){
		    			Position s=new Position(this, c);
		    			ind.addPositionForWord(l[i], s);
		    		}
		    		}
		    	}
		    }
			
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}
		
		makeavltree();
	}
	
	public boolean notbelong(String x){
		for(int i=0;i<connectors.length;i++){
			if(x.equals(connectors[i]))return false;
		}
		return true;
	}
	
	public void makeavltree(){
		tree=null;
		tree=new AvlTree<Wordposition>();
		tree.setName(pagename);
		node<WordEntry> t=ind.getWordEntries().getFirstlink();
		while(t!=null){
			node<Position> q=t.getO().getPoslist().getFirstlink();
			while(q!=null){
				Wordposition temp=new Wordposition();
				temp.setName(t.getO().getName());
				temp.setPos(q.getO());
				tree.insert(temp);
				q=q.getNext();
			}
			t=t.getNext();
		}
		return;
	}
	
	 public double relevanceofwordwithrespecttopage(String word){
		 int k[]=ind.positionsinpagewherewordappears(word, this.pagename);
		 //if(k.length==0)return 0;
		 double x=0.0;
		 for(int i=0;i<k.length;i++){
			 //System.out.println("hi k[i]= "+k[i]);
			 double c=0.0;
			 c=((double)1/k[i])*((double)1/k[i]);
			 //System.out.println("c= "+c);
			 x=x+c;
		 }
		 return x;
	 }
	 
	 public Avltreenode<Wordposition> find(int pos,Avltreenode<Wordposition> temp){
		 if(temp==null)return null;
		 if(temp.getObj().getPos().getWordIndex()==pos)return temp;
		 if(temp.getObj().getPos().getWordIndex()>pos){
			 return find(pos,temp.getLeftchild());
		 }
		 return find(pos,temp.getRightchild());
	 }
	  
	 public int[] positionsinpagewherephraseappears(String[] phrase){
		 //System.out.println("phraselength= "+phrase.length);
		 int a[]=ind.positionsinpagewherewordappears(phrase[0], this.pagename);
		 
		 //System.out.println("posn " +phrase[0]);
		 //for(int i=0;i<a.length;i++)System.out.println(a[i]);
		 
		 ArrayList<Integer> ans=new ArrayList<>();
		 
		 for(int i=0;i<a.length;i++){
		   Wordposition x=new Wordposition();
		   x.setName(phrase[0]);
		   Position p=new Position(this,a[i]);
		   x.setPos(p);
		   Avltreenode<Wordposition> n=find(p.getWordIndex(),tree.getRoot());
		  // System.out.println("nstatus "+n);
		   if(n!=null){
			   boolean r=true;
			  for(int j=1;j<phrase.length;j++){
				  if(tree.inordersuccessor(n)==null || !tree.inordersuccessor(n).getObj().getName().equals(phrase[j])){
					  r=false ; break;
				  }
				  n=tree.inordersuccessor(n);
			  }
			  if(r==true){
				  ans.add(a[i]);
			  }
		   }
		 }
		 //System.out.println("ans.size= "+ans.size());
		 int b[]=new int[ans.size()];
		 for(int i=0;i<ans.size();i++){
			 b[i]=ans.get(i);
		 }
		 return b;
	 }
	 
	 public double getRelevanceOfPage(String str[], boolean phrase){
		double ans=0;
		 if(!phrase){
			for(int i=0;i<str.length;i++)ans+=relevanceofwordwithrespecttopage(str[i]);
			return ans;
		}
		 else{
			 int k[]=positionsinpagewherephraseappears(str);
			 for(int i=0;i<k.length;i++){
				 ans+=((double)1/k[i])*((double)1/k[i]);
			 }
			 return ans;
		 }
	 }
	 

		/*public static void main(String[] args) {
			PageEntry test=new PageEntry("shivank");
			test.makeavltree();
			//test.getTree().traverse();
			//Avltreenode<Wordposition> x=test.getTree().getRoot();
			
			System.out.println("root= "+x.getObj().toString());
			System.out.println("7left = "+x.getLeftchild().getObj().toString());
			System.out.println("7right= "+x.getRightchild().getObj().toString());
			System.out.println("4left = "+x.getLeftchild().getLeftchild().getObj().toString());
			System.out.println("4right = "+x.getLeftchild().getRightchild().getObj().toString());
			System.out.println("10left= "+x.getRightchild().getLeftchild().getObj().toString());
			System.out.println("10right= "+x.getRightchild().getRightchild().getObj().toString());
			System.out.println("2left = "+x.getLeftchild().getLeftchild().getLeftchild().getObj().toString());
			System.out.println("2right = "+x.getLeftchild().getLeftchild().getRightchild().getObj().toString());
			System.out.println(" inordersuucc "+(test.getTree().inordersuccessor(x.getRightchild().getLeftchild() )).getObj().toString());
			
			String s[]={"i","love","you"};
			int pos[]=test.positionsinpagewherephraseappears(s);
			for(int i=0;i<pos.length;i++)System.out.println("pos= "+pos[i]);
			System.out.println("rel= "+test.getRelevanceOfPage(s, true));
		}
	*/
}

class Wordposition implements Comparable<Wordposition>{
	@Override
	public String toString() {
		return "Wordposition [name=" + name + ", pos=" + pos + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Position getPos() {
		return pos;
	}
	public void setPos(Position pos) {
		this.pos = pos;
	}
	private String name;
	private Position pos;
	@Override
	public int compareTo(Wordposition o) {
		// TODO Auto-generated method stub
		return this.getPos().compareTo(o.getPos());
	}
	
}
