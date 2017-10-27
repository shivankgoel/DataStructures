public class MyHashTable {
	
	private static int N=55;
	
	@SuppressWarnings("unchecked")
	private static MyLinkedList<WordEntry>[] wordentrylists=new MyLinkedList[N]; 
	
	private int getHashIndex(String str){
		int s=0;
		for(int i=0;i<str.length();i++){
			s=s+(int)str.charAt(i);
		}
		return hashfunction(s);
	}
	
	private int hashfunction(int a){
		int x=0; int r; int i=0;
		int array[]={2,3,5,7,11,13,19};
		while(a>0){
			r=(a%10);
			x+=Math.pow(r,array[i%7]);
			a=a/10;
		}
		return (Math.abs(x)%N);
	}
	
	
	public void addPositionsForWord(WordEntry w){
		int index=getHashIndex(w.getName());
		if(wordentrylists[index]==null){
			wordentrylists[index]=new MyLinkedList<>();
			wordentrylists[index].add(w);
			return;
		}
		node<WordEntry> t=wordentrylists[index].getFirstlink();
		boolean temp=false;
		while(t!=null){
			if(t.getO().getName().equals(w.getName())){
				temp=true;
				t.getO().addPositions(w.getPoslist());
				break;
			}
			t=t.getNext();
		}
		if(temp==false){
			wordentrylists[index].add(w);
		}
	}
	
	public WordEntry getwordentryofstring(String s){
		s = Character.toLowerCase(s.charAt(0)) + (s.length() > 1 ? s.substring(1) : "");
		if(s.equals("stacks"))s="stack";
		if(s.equals("applications"))s="application";
		if(s.equals("structures"))s="structure";
		int index=getHashIndex(s);
		if(wordentrylists[index]==null)return null;
		node<WordEntry> t=wordentrylists[index].getFirstlink();
		while(t!=null){
			if(t.getO().getName().equals(s)){
				return t.getO();
			}
			t=t.getNext();
		}
		return null;
	}

}
