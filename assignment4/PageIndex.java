public class PageIndex {
	private MyLinkedList<WordEntry> WordEntries;
	
	PageIndex(){
		WordEntries =new MyLinkedList<WordEntry>();
	}
	
	public MyLinkedList<WordEntry> getWordEntries(){
		return WordEntries;
	}
	
	public void addPositionForWord(String str, Position p){
		boolean x=false;
		node<WordEntry> t = WordEntries.getFirstlink();
		while(t!=null){
			if(t.getO().getName().equals(str)){
			x=true; break;	
			}
			t=t.getNext();
		}
		if(x==true){
			if(!t.getO().getPoslist().contains(p))
			t.getO().addPosition(p);
		}
		else{
			WordEntry e=new WordEntry(str);
			e.addPosition(p);
			WordEntries.add(e);
		}
	}
	
	public void displaywordsinindex(){
		node<WordEntry> e=WordEntries.getFirstlink();
		while(e!=null){
			System.out.print(e.getO().getName()+" ");
			e=e.getNext();
		}
		System.out.println();
	}
	
	public int nofwordsinpage(String s,String pagename){
		node<WordEntry> e=WordEntries.getFirstlink();
		while(e!=null){
			if(e.getO().getName().equals(s)){
				MyLinkedList<Position> l=e.getO().getPoslist();
				MyLinkedList<Position> temp=new MyLinkedList<>();
				node<Position> t=l.getFirstlink();
				while(t!=null){
					if(t.getO().getPageEntry().getPagename().equals(pagename))
						temp.add(t.getO());
					t=t.getNext();
				}
				return temp.size();
			}
			e=e.getNext();
		}
		return 0;
	}
	
	public int[] positionsinpagewherewordappears(String s,String pagename){
		int positions[]={};
		node<WordEntry> e=WordEntries.getFirstlink();
		while(e!=null){
			if(e.getO().getName().equals(s)){
				MyLinkedList<Position> l=e.getO().getPoslist();
				MyLinkedList<Position> temp=new MyLinkedList<>();
				node<Position> t1=l.getFirstlink();
				while(t1!=null){
					if(t1.getO().getPageEntry().getPagename().equals(pagename))
						temp.add(t1.getO());
					t1=t1.getNext();
				}
				positions=new int[temp.size()];
				node<Position> t=temp.getFirstlink();
				int c=0;
				while(t!=null){
					positions[c]=t.getO().getWordIndex();
					c++;
					t=t.getNext();
				}
				break;
			}
			e=e.getNext();
		}
		return positions;
	}
}
