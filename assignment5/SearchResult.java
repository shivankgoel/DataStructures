public class SearchResult implements Comparable<SearchResult>{
	public PageEntry getP() {
		return p;
	}

	public void setP(PageEntry p) {
		this.p = p;
	}

	public double getRelevance() {
		return relevance;
	}

	public void setRelevance(float relevance) {
		this.relevance = relevance;
	}

	private PageEntry p;
	private double relevance;
	
	SearchResult(PageEntry p,double r){
		this.p=p;
		relevance=r;
	}

	@Override
	public int compareTo(SearchResult x) {
		if(this.relevance>x.getRelevance())return 1;
		if(this.relevance==x.getRelevance())return 0;
		if(this.relevance<x.getRelevance())return -1;
		// TODO Auto-generated method stub
		return 0;
	}
	
}
