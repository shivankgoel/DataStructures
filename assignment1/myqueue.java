public class myqueue {

    // implement the stack using an array
	int myarray[] = new int[100];
	int f,r;
	myqueue(){
	f=0; 
	r=-1;
	}
	// declare additional variables here

	public void enqueue(int element) {
	r++;
	if(r-f==100){
		System.out.println("Queue Full"); return;
	}
	myarray[r%100]=element;
		// ..... fill the stub function ......
	}

	public int dequeue() {
		if(f>r){
			System.out.println("Queue Empty"); 
			return 0;
		}
		int x = myarray[f%100]; myarray[f%100]=0;
		f++;
		return x;
		// ..... fill the stub function ......
	
	}

	public boolean isEmpty() {
		return(f>r); 
		// ..... fill the stub function ......
	}
}

