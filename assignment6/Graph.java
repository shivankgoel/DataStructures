import java.util.*;

public class Graph {
	
	private HashMap<String,HashMap<String,Integer>> adlist=new HashMap<String,HashMap<String,Integer>>() ;
	private HashMap<String,String> tlist=new HashMap<String,String>();
	private HashMap<String,Integer> tavail=new HashMap<String,Integer>();
	
	
	public void addedge(String src,String destn,int t){
		
		if(!adlist.containsKey(src))adlist.put(src, new HashMap<String,Integer>());
		if(!adlist.containsKey(destn))adlist.put(destn, new HashMap<String,Integer>());
		if(!adlist.get(src).containsKey(destn))adlist.get(src).put(destn,t);
		if(!adlist.get(destn).containsKey(src))adlist.get(destn).put(src,t);
	}
	
	public void addtaxi(String name,String posn){
		if(!adlist.containsKey(posn)){
			System.out.println("No such position exists for taxi!!!");
			return;
		}
		if(!tlist.containsKey(name)){
			tlist.put(name,posn);
			tavail.put(name, 0);
		}
		else System.out.println("Taxi by this name already exists!!!");
	}
	
	public String findmin(HashMap<String,Integer> sp,ArrayList<String> f){
		//find minimum distance node which is not finalised 
		String ans="";
		int min=Integer.MAX_VALUE;
		for(Map.Entry<String,Integer> entry:sp.entrySet()){
			String a=entry.getKey();
			if(!f.contains(a)){
				if(sp.get(a)<min){
					min=sp.get(a);
					ans=a;
				}
			}
		}
		return ans;
	}
	
	public HashMap<String,String> shortestpath(String s){
		if(!adlist.containsKey(s)){
			System.out.println("Such a location does not exist!!!");
			return null;
		}
		HashMap<String,Integer> x=new HashMap<String,Integer>();
		HashMap<String,String> y=new HashMap<String,String>();
		ArrayList<String> finalised=new ArrayList<String>();
		for(Map.Entry<String, HashMap<String, Integer>> entry:adlist.entrySet()){
			String a=entry.getKey();
			x.put(a,Integer.MAX_VALUE);
			y.put(a,"");
		}
		y.put(s,s);
		x.put(s,0);
		int n=adlist.size();
		
		for(int i=0;i<n-1;i++){
			String b=findmin(x,finalised);
			finalised.add(b);
			for(Map.Entry<String,Integer> entry:adlist.get(b).entrySet()){
				String p=entry.getKey();
				Integer q=entry.getValue();
				if(!finalised.contains(p)){
					if(x.get(b)+adlist.get(b).get(p)<x.get(p)){
						x.put(p, x.get(b)+adlist.get(b).get(p));
						y.put(p, b);
					}
				}
			}
		}
		
		return y;
	}
	

	public HashMap<String,Integer> shortestpathlength(String s){
		if(!adlist.containsKey(s)){
			System.out.println("Such a location does not exist!!!");
			return null;
		}
		HashMap<String,Integer> x=new HashMap<String,Integer>();
		HashMap<String,String> y=new HashMap<String,String>();
		ArrayList<String> finalised=new ArrayList<String>();
		for(Map.Entry<String, HashMap<String, Integer>> entry:adlist.entrySet()){
			String a=entry.getKey();
			x.put(a,Integer.MAX_VALUE);
			y.put(a,"");
		}
		y.put(s,s);
		x.put(s,0);
		int n=adlist.size();
		
		for(int i=0;i<n-1;i++){
			String b=findmin(x,finalised);
			finalised.add(b);
			for(Map.Entry<String,Integer> entry:adlist.get(b).entrySet()){
				String p=entry.getKey();
				Integer q=entry.getValue();
				if(!finalised.contains(p)){
					if(x.get(b)+adlist.get(b).get(p)<x.get(p)){
						x.put(p, x.get(b)+adlist.get(b).get(p));
						y.put(p, b);
					}
				}
			}
		}
		
		return x;
	}
	
	public boolean istaxiavail(String s,int t){
		if(!tlist.containsKey(s)){
			System.out.println("Taxi by this name does not exist , thus not available!!!");
			return false;
		}
		if(tavail.get(s)<=t)return true;
		return false;
	}
	
	public void printavailtaxis(int t){
		System.out.println("Available taxis are");
		boolean flag=false;
		int c=0;
		for(Map.Entry<String,String> entry:tlist.entrySet()){
			if(istaxiavail(entry.getKey(),t)){
				flag=true;
				System.out.println(++c+") "+entry.getKey()+": at posn: "+entry.getValue());
			}
		}
		if(flag==false)System.out.println("No taxi is available at this time");
	}
	
	public void printshortestpath(String src,String dest){
		if(!adlist.containsKey(src) || !adlist.containsKey(dest)){
			System.out.println("invalid print shortest path command, no junction with name exists!!!");
			return ;
		}
		HashMap<String,String> h=shortestpath(src);
		LinkedList<String> l=new LinkedList<String>();
		String temp=dest;
		while(!temp.equals(src)){
			l.add(temp);
			temp=h.get(temp);
		}
		l.add(src);
		for(int i=l.size()-1;i>=0;i--)System.out.print(l.get(i)+"  ");
	}
	
	public void customercall(String src,String dest,int t){
		if(!adlist.containsKey(src) || !adlist.containsKey(dest)){
			System.out.println("invalid customer call, no junction with name exists!!!");
			return ;
		}
		int mint=Integer.MAX_VALUE;
		String mins="";
		int c=0;
		System.out.println("Available Taxis For Customer call are");
		for(Map.Entry<String, String> entry1:tlist.entrySet()){
			String x=entry1.getKey();
			String y=entry1.getValue();
			if(istaxiavail(x,t)){
			System.out.print(++c+") Path of Taxi "+x+": ");
			printshortestpath(y,src);
			int tempt=shortestpathlength(y).get(src);
			System.out.print(" :Time Taken: "+tempt);
			if(istaxiavail(x,t) && tempt<mint){
				mint=tempt;
				mins=x;
			}
			System.out.println();
			}
		}
		int sdt=shortestpathlength(src).get(dest);
		if(mins.equals(""))System.out.println("Sorry!!! No taxi is available at this time("+t+" units)");
		else{
			System.out.println("Choose taxi \""+mins+"\" to service customer request");
			tavail.put(mins,t+mint+sdt);
			tlist.put(mins, dest);
		}
		System.out.print("Path of Customer: ");
		printshortestpath(src,dest);
		System.out.print(" :Time Taken: "+sdt);
		System.out.println();
	}
	

	
	
	/*public static void main(String[] args) {
		Graph g=new Graph();
		g.addedge("indiagate","lajpatnagar",50);
		g.addedge("iitmaingate","lajpatnagar",40);
		g.addedge("aiims","lajpatnagar",15);
		g.addedge("iitmaingate","chanakyapuri",50);
		g.addedge("aiims","indiagate",30);
		g.addedge("iitmaingate","aiims",30);
		g.addedge("aiims","chanakyapuri",20);
		g.addedge("indiagate","chanakyapuri",30);
		g.addtaxi("taxi1","iitmaingate");
		g.addtaxi("taxi2","lajpatnagar");
		g.addtaxi("taxi3","indiagate");
		g.printavailtaxis(0);
		g.customercall("iitmaingate","chanakyapuri",1);
		g.customercall("indiagate","chanakyapuri",1);
		g.customercall("indiagate","chanakyapuri",10);
		g.customercall("indiagate","chanakyapuri",10);
		g.printavailtaxis(99);
	}*/

}
