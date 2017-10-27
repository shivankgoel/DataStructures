public class WordEntry {
	private String name;
	private MyLinkedList<Position> poslist;
	
	WordEntry(String word){
		
		word.toLowerCase();
		word = Character.toLowerCase(word.charAt(0)) + (word.length() > 1 ? word.substring(1) : "");
		this.name=word;
		poslist=new MyLinkedList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public MyLinkedList<Position> getPoslist() {
		return poslist;
	}
	public void setPoslist(MyLinkedList<Position> poslist) {
		this.poslist = poslist;
	}
	public void addPosition(Position position){
		if(!this.getPoslist().contains(position))
		this.getPoslist().add(position);
	}
	
	public void addPositions(MyLinkedList<Position> positions){
		node<Position> t=positions.getFirstlink();
		while(t!=null){
			if(!this.getPoslist().contains(t.getO()))
			this.getPoslist().add(t.getO());
			t=t.getNext();
		}
	}
	public MyLinkedList<Position> getAllPositionsForThisWord(){
		return this.getPoslist();
	}

}
