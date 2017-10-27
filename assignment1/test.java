public class test {

	public boolean isStackSortablePermutation(myqueue input) {
		mystack s=new mystack();
		myqueue q=new myqueue();
		int a=input.f;
		int b=input.r;
		
		for(int i=a;i<=b;i++){
			int element=input.dequeue();
			while(!s.isEmpty() && element>s.getElementAtTopOfStack()){
			q.enqueue(s.pop());	
			}
			s.push(element);
		}
		
		while(!s.isEmpty()){
			q.enqueue(s.pop());	
		}
		
		for (int i = q.f; i <=q.r-1; i++) {
			if(q.myarray[i]>q.myarray[i+1])return false;
		}
		return true;
		// ..... fill the stub function ......
	}

}

