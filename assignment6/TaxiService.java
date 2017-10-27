public class TaxiService {
	Graph g;
	TaxiService(){
		g=new Graph();
	}
	
	void performAction(String msg){
		String[] a=msg.split("\\s+");
		
	System.out.println("\""+msg+"\"");
			
			if(a[0].equals("edge")){
				g.addedge(a[1],a[2],Integer.parseInt(a[3]));
			}
			else if(a[0].equals("taxi")){
				g.addtaxi(a[1],a[2]);
			}
		
			else if(a[0].equals("customer")){
				g.customercall(a[1],a[2],Integer.parseInt(a[3]));
			}
			
			else if(a[0].equals("printTaxiPosition")){
				g.printavailtaxis(Integer.parseInt(a[1]));
			}
				
			else{
				try{
					throw new Exception("Invalid statement!!!");
				}
				catch(Exception e){
					System.out.println(e);
					return;
				}
			}
			
			System.out.println();
	}
	
}
