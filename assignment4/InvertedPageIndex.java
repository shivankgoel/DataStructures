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
		l.toLowerCase(Locale.ENGLISH);
		l = Character.toLowerCase(l.charAt(0)) + (l.length() > 1 ? l.substring(1) : "");
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
		return m;
	}
	
	/*public static void main(String[] args) {
		InvertedPageIndex test=new InvertedPageIndex();
		PageEntry p=new PageEntry("test");
		PageEntry q=new PageEntry("test2");
		test.addPage(p);
		test.addPage(q);
		MySet<PageEntry> s=test.getPagesWhichContainWord("daddy");
		node<PageEntry> n=s.getL().getFirstlink();
		n=s.getL().getFirstlink();
		while(n!=null){
			PageEntry pagecontainingword=n.getO();
			System.out.println(pagecontainingword.getPagename());
			PageIndex ind=pagecontainingword.getPageIndex();
			ind.displaywordsinindex();
			System.out.println("no.of times daddy appears= "+ ind.nofwordsinpage("daddy",pagecontainingword.getPagename()));
			System.out.println("positions where daddy appears");
			int pos[]=ind.positionsinpagewherewordappears("daddy",pagecontainingword.getPagename());
			for(int j=0;j<pos.length;j++){
				System.out.print(pos[j]+" ");
			}
			System.out.println();
			n=n.getNext();
		}
	}*/
}
