public class mystack {

    // implement the stack using an array
	int myarray[] = new int[100];
	// declare additional variables here
    int t;
    mystack(){
    	t=-1;
    }
	
	public void push(int element) {
		if(t>=99){
			System.out.println("Stack Full"); return;
		}
		t++;
		myarray[t]=element;
		// ..... fill the stub function ......
	}

	public int pop() {
		if(t==-1){
			System.out.println("Stack Empty"); return 0;
		}
		else{
			int x=myarray[t];
			myarray[t]=0;
			t--;
			return x;
		}
		// ..... fill the stub function ......
	
	}

	public boolean isEmpty() {
		return (t==-1);
		// ..... fill the stub function ......
	}

	public int getElementAtTopOfStack() {
		if(t==-1){
			System.out.println("Stack Empty"); return 0;
		}
		return myarray[t];
		// ..... fill the stub function ......
	}
}

