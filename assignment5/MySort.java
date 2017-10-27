import java.util.ArrayList;
import java.util.Collections;

public class MySort<X extends Comparable<X>>{
	
	public  ArrayList<X> sortThisList(MySet<X> l){
		ArrayList<X> a=new ArrayList<X>();
		node<X> t=l.getL().getFirstlink();
		while(t!=null){
			a.add(t.getO());
			t=t.getNext();
		}
		//Collections.sort(a);
		
		for(int i=0;i<a.size();i++){
			X max=a.get(i);
			int pos=i;
			for(int j=i;j<a.size();j++){
				if(a.get(j).compareTo(max)>0){
					max=a.get(j); pos=j;
				}
			}
			X temp;
			temp=a.get(i);
			a.set(i,max);
			a.set(pos,temp);
		}
		
		return a;
	}
}

