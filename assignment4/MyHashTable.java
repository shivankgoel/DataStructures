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
		String m=s.substring(0, s.length()-1);
		String n=s.substring(1);
		n+=s.substring(0,1).toLowerCase();
		if(s.charAt(s.length()-1)=='s'){
			m=s.substring(0, s.length()-1);
		};
		
		int index=getHashIndex(s);
		if(wordentrylists[index]==null)return null;
		node<WordEntry> t=wordentrylists[index].getFirstlink();
		while(t!=null){
			if(t.getO().getName().equals(s)||t.getO().getName().equals(m)||t.getO().getName().equals(s)){
				return t.getO();
			}
			t=t.getNext();
		}
		return null;
	}

}
