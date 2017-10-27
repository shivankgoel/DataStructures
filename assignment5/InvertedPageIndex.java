import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class InvertedPageIndex {
	MyHashTable hashtable;
	MySet<String> checkaddedpages= new MySet<String>(); 
	
	InvertedPageIndex(){
		hashtable = new MyHashTable();
	}
	
	public void addPage(PageEntry p){
		if(!checkaddedpages.isMember(p.getPagename()))checkaddedpages.addElement(p.getPagename());
		MyLinkedList<WordEntry> l=p.getPageIndex().getWordEntries();
		node<WordEntry> t=l.getFirstlink();
		while(t!=null){
			hashtable.addPositionsForWord(t.getO());
			t=t.getNext();
		}
	}
	
	public MySet<PageEntry> getPagesWhichContainWord(String l){
		l = Character.toLowerCase(l.charAt(0)) + (l.length() > 1 ? l.substring(1) : "");
		l.toLowerCase(Locale.ENGLISH);
		l=l.replaceAll("\\W"," ");
		l=l.split("\\s+")[0];
		if(l.equals("stacks"))l="stack";
		if(l.equals("applications"))l="application";
		if(l.equals("structures"))l="structure";
		String str=l.trim();
		MySet<PageEntry> m=new MySet<PageEntry>();
		WordEntry e=hashtable.getwordentryofstring(str.toLowerCase());
		if(e==null){
			System.out.print("Word "+str+" is not present in any of the added pages!!!!");
			return null;
		}
		node<Position> t=e.getPoslist().getFirstlink();
		while(t!=null){
			if(!m.isMember(t.getO().getPageEntry()))
			m.addElement(t.getO().getPageEntry());
			t=t.getNext();
		}
		MySet<SearchResult> tmp = new MySet<SearchResult>();
		node<PageEntry>n2 = m.getL().getFirstlink();
		while(n2!=null){
			double rel=n2.getO().relevanceofwordwithrespecttopage(str);
			SearchResult sr=new SearchResult(n2.getO(), rel); 
			tmp.Insert(sr);
			n2=n2.getNext();
		}
		MySort<SearchResult> ms=new MySort<SearchResult>();
		ArrayList<SearchResult> al=ms.sortThisList(tmp);
		m=null; m=new MySet<PageEntry>();
		Iterator<SearchResult> it=al.iterator();
		while(it.hasNext()){
			m.addElement(it.next().getP());
		}
		return m;
	}
	

	
}
