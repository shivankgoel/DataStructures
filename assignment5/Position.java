public class Position implements Comparable<Position>{
	private PageEntry p;
	private int wordindex;
	
	 Position(PageEntry p, int wordIndex){
		 this.p=p;
		 this.wordindex=wordIndex;
	 }
	
	public PageEntry getPageEntry(){
		return p;
	}
	public int getWordIndex(){
		return wordindex;
	}
	public void setPageEntry(PageEntry p){
		this.p=p;
	}
	public void setWordIndex(int x){
		this.wordindex=x;
	}

	@Override
	public int compareTo(Position p) {
		// TODO Auto-generated method stub
		if(this.wordindex>p.wordindex)return 1;
		else if(this.wordindex==p.wordindex)return 0;
		return -1;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.wordindex);
	}
}
