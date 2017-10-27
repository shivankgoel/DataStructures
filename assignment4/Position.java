public class Position {
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
	
}
