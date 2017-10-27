import java.util.ArrayList;
import java.util.Iterator;

public class SearchEngine {
	InvertedPageIndex ipi;
	
	SearchEngine(){
		ipi=new InvertedPageIndex();
	}
	void addPage(String s){
		PageEntry x=new PageEntry(s);
		//System.out.println("//"+x.getPagename()+x.getPageIndex().getWordEntries().toString());
		ipi.addPage(x);
		ipi.checkaddedpages.addElement(s);
		
	}
	
	public MySet<PageEntry> queryFindPagesWhichContainWord(String x){
		return ipi.getPagesWhichContainWord(x);
	}
	
	public MySet<PageEntry> queryFindPagesWhichContainAllWords(String arr[]){
		MySet<PageEntry> x=new MySet<PageEntry>();
		x=queryFindPagesWhichContainWord(arr[0]);
		for(int i=1;i<arr.length;i++){
			x=x.intersection(queryFindPagesWhichContainWord(arr[i]));
		}
		node<PageEntry> tmp=x.getL().getFirstlink();
		MySet<SearchResult> sr=new MySet<SearchResult>();
		while(tmp!=null){
			SearchResult shiv=new SearchResult(tmp.getO(),tmp.getO().getRelevanceOfPage(arr, false));
			sr.Insert(shiv);
			tmp=tmp.getNext();
		}
		MySort<SearchResult> ms=new MySort<SearchResult>();
		ArrayList<SearchResult> al=ms.sortThisList(sr);
		x=null; x=new MySet<PageEntry>();
		Iterator<SearchResult> it=al.iterator();
		while(it.hasNext()){
			x.addElement(it.next().getP());
		}
		return x;
	}
	
	
	public MySet<PageEntry> queryFindPagesWhichContainAnyOfTheseWords(String arr[]){
		MySet<PageEntry> x=new MySet<PageEntry>();
		x=queryFindPagesWhichContainWord(arr[0]);
		for(int i=1;i<arr.length;i++){
			x=x.union(queryFindPagesWhichContainWord(arr[i]));
		}
		node<PageEntry> tmp=x.getL().getFirstlink();
		MySet<SearchResult> sr=new MySet<SearchResult>();
		while(tmp!=null){
			SearchResult shiv=new SearchResult(tmp.getO(),tmp.getO().getRelevanceOfPage(arr, false));
			sr.Insert(shiv);
			tmp=tmp.getNext();
		}
		MySort<SearchResult> ms=new MySort<SearchResult>();
		ArrayList<SearchResult> al=ms.sortThisList(sr);
		x=null; x=new MySet<PageEntry>();
		Iterator<SearchResult> it=al.iterator();
		while(it.hasNext()){
			x.addElement(it.next().getP());
		}
		return x;
	}
	
	public MySet<PageEntry> queryFindPagesWhichContainPhrase(String arr[]){
		MySet<PageEntry> temp=new MySet<PageEntry>();
		MySet<PageEntry> x=new MySet<PageEntry>();
		temp=queryFindPagesWhichContainWord(arr[0]);
		node<PageEntry> tempnode=temp.getL().getFirstlink();
		while(tempnode!=null){
			if((tempnode.getO().positionsinpagewherephraseappears(arr)).length!=0)x.addElement(tempnode.getO());
		tempnode=tempnode.getNext();	
		}
		node<PageEntry> tmp=x.getL().getFirstlink();
		MySet<SearchResult> sr=new MySet<SearchResult>();
		while(tmp!=null){
			SearchResult shiv=new SearchResult(tmp.getO(),tmp.getO().getRelevanceOfPage(arr, true));
			sr.Insert(shiv);
			tmp=tmp.getNext();
		}
		MySort<SearchResult> ms=new MySort<SearchResult>();
		ArrayList<SearchResult> al=ms.sortThisList(sr);
		x=null; x=new MySet<PageEntry>();
		Iterator<SearchResult> it=al.iterator();
		while(it.hasNext()){
			x.addElement(it.next().getP());
		}
		return x;
	}
	
	public void queryFindPositionsOfWordInAPage(String word,String page){
		MySet<PageEntry> s=queryFindPagesWhichContainWord(word);
		if(s==null){
			System.out.println("Word "+word+" Not Found!!!");
			return;
		}
		node<PageEntry> n=s.getL().getFirstlink();
		n=s.getL().getFirstlink();
		while(n!=null){
			PageEntry pagecontainingword=n.getO();
			
			if(pagecontainingword.getPagename().equals(page)){ 
			PageIndex ind=pagecontainingword.getPageIndex();
			//ind.displaywordsinindex();
			//System.out.println("no.of times "+word+" appears= "+ ind.nofwordsinpage(word,page));
			System.out.print("Positions where " +word+" appears- ");
			int pos[]=ind.positionsinpagewherewordappears(word,page);
			for(int j=pos.length-1;j>=0;j--){
				System.out.print(pos[j]+" ");
			}
			System.out.println();
			return;
		   }
			n=n.getNext();
		}
		if(n==null){
		node<String> x=ipi.checkaddedpages.getL().getFirstlink();
		while(x!=null){
			if(x.getO().equals(page))break;
			x=x.getNext();
		}
		if(x==null){
			System.out.println("Page "+page+" Not Found- First Add The Page!!!!");
			return;
		}
		else{
			System.out.println("Word "+word+" not found in given page!!!");
		}
		}
	}
	
	public void print(MySet<PageEntry> p){
		MySet<String> temp=new MySet<String>();
		if(p!=null){
			node<PageEntry> m=p.getL().getFirstlink();
			while(m!=null){
				if(!temp.isMember(m.getO().getPagename())){
				temp.addElement(m.getO().getPagename());
				}
				m=m.getNext();
			}
			node<String> ns=temp.getL().getFirstlink();
			while(ns!=null){
				System.out.println(ns.getO()+"  ");
				ns=ns.getNext();
			}
		}
		System.out.println();
	}
	
	void performAction(String msg){
		String[] a=msg.split("\\s+");
		
	System.out.println(msg+" => ");
			
			if(a[0].equals("addPage")){
				addPage(a[1]);
			}
			else if(a[0].equals("queryFindPagesWhichContainWord")){
				MySet<PageEntry> p=queryFindPagesWhichContainWord(a[1]);
				print(p);
			}
		
			else if(a[0].equals("queryFindPositionsOfWordInAPage")){
				//addExchange(a1,b1);
				queryFindPositionsOfWordInAPage(a[1],a[2]);
			}
			
			else if(a[0].equals("queryFindPagesWhichContainAllWords")){
				String x[]=new String[a.length-1];
				for(int i=1;i<a.length;i++){
					x[i-1]=a[i];
				}
				MySet<PageEntry> p=queryFindPagesWhichContainAllWords(x);
				print(p);
			}
		
			
			else if(a[0].equals("queryFindPagesWhichContainAnyOfTheseWords")){
				String x[]=new String[a.length-1];
				for(int i=1;i<a.length;i++){
					x[i-1]=a[i];
				}
				MySet<PageEntry> p=queryFindPagesWhichContainAnyOfTheseWords(x);
				print(p);
			}
			
			else if(a[0].equals("queryFindPagesWhichContainPhrase")){
				String x[]=new String[a.length-1];
				for(int i=1;i<a.length;i++){
					x[i-1]=a[i];
				}
				MySet<PageEntry> p=queryFindPagesWhichContainPhrase(x);
				print(p);
			}
			
			else{
				try{
					throw new Exception("Invalid statement!!!");
				}
				catch(Exception e){
					//System.out.println("Size is neither 2 nor 3");
					System.out.println(e);
					return;
				}
			}
		
	}
		

}