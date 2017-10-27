import java.io.*;

public class RoutingMapTree {
	Exchange root;
	Exchange nonreg;
	RoutingMapTree(){
		root=new Exchange(0);
		nonreg=new Exchange(-1);
	}

	//checks if the tree contains exchange a
	public boolean containsNode(Exchange a){
		Exchange x=this.root;
		if(x==null)return false;
		if(x==a)return true;
		
		for(int i=0;i<x.numChildren();i++){
			if(x.subtree(i).containsNode(a)==true)return true;
		}
		
		return false;
	}
	
	//returns MobilePhone of id z from exchange x if present 
	public MobilePhone findMobileByIdInExchange(Exchange x,int z){
		MobilePhoneSet s=x.mset;
		Myset y=s.a;
		Linkedlist p=y.l;
		node t=p.firstlink;
		while(t!=null){
		if( ((MobilePhone)t.o).id==z)return (MobilePhone)t.o;
		t=t.next;
		}
		return null;
	}
	
	public MobilePhone findMobileById(int z){
	    Exchange x=this.root;
	    if(x==null)return null;
	    MobilePhone t=findMobileByIdInExchange(x,z);
	    if(t!=null)return t;
		for(int i=0;i<x.numChildren();i++){
			t=x.subtree(i).findMobileById(z);
			if(t!=null)return t;
		}
		return null;
	}
	
	public Exchange findExchangeById(int n){
	       Exchange x=this.root;
	       if(x==null)return null;
	       if(x.id==n)return x;
	   	for(int i=0;i<x.numChildren();i++){
			Exchange y=x.subtree(i).findExchangeById(n);
			if(y!=null)return y;
		}
	   	return null;
	}
	
	public void addExchange(int x,int y){
		Exchange a=findExchangeById(x);
		Exchange b=findExchangeById(y);
	    try{
	    	if(a==null)throw new Exception("No exchange(a) by this id exists!!!");
	    	if(b!=null)throw new Exception("Exchange "+b+" by this id already exists!!!");
	    }
	    catch(Exception e){
	    	System.out.println(e);
	    	return;
	    }
	    b=new Exchange(y);
	    a.childlist.add(b);
	    a.mset=a.mset.Union(b.mset);
	    b.par=a;
}
	
	/*public void switchOn(MobilePhone m,Exchange x){
		if(this.containsNode(x))
		m.loc=x;
		m.ison=true;
		x.mset.Insert(m);
	}*/
	
	public void switchOnMobile(int a,int b){
	Exchange x=findExchangeById(b);
	try{
		if(x==null)throw new Exception("Exchange Dont Exist");
		if(x.childlist.size()!=0)throw new Exception("The given exchange is not base exchange!!!");
	}
	catch(Exception e){
		System.out.println(e);
		return;
	}
	MobilePhone m;
	m=findMobileByIdInExchange(nonreg,a);
	if(m!=null){
		m.switchOn();
		m.loc=x;
		x.residentSet().Insert(m);
		nonreg.residentSet().Delete(m);
	}
	else{
	m=findMobileById(a);
	if(m==null){
		m=new MobilePhone(a);
		m.loc=x;
		m.switchOn();
		x.residentSet().Insert(m);
		if(x.parent()!=null)updateparent(x.parent(),m);
	}
	else{
		try{
			if(m.status()==true)throw new Exception("Mobile Phone is already on and registered with exchange with id "+m.loc.id+" !!!");
		}
		catch(Exception e){
			System.out.println(e);
			return;
		}
		//control reaches here only if m is switched off but still registered with some exchange
		m.loc.residentSet().Delete(m);
		m.switchOn();
		m.loc=x;
		x.residentSet().Insert(m);
	 
	}
	}
	return;
}
	
	public void updateparent(Exchange x,MobilePhone m){
		if(!x.residentSet().isMember(m)){
			x.residentSet().Insert(m);
		}
		if(x.par!=null)updateparent(x.par,m);
		return;
	}
	
	public void switchOffMobile(int z){
		MobilePhone m=findMobileById(z);
		try{
			if(m==null)throw new Exception("Mobile by this id does not exist!!!");
		}
		catch(Exception e){
			System.out.println(e);
			return;
		}
		Exchange p=m.location();
		p.residentSet().Delete(m);
		if(p.parent()!=null){
		updateparent2(p.parent(),m);
		}
		m.switchOff();
		nonreg.residentSet().Insert(m);
		m.loc=nonreg;
	}
	
	public void updateparent2(Exchange x,MobilePhone m){
		x.residentSet().Delete(m);
		if(x.par!=null)updateparent2(x.par,m);
		return;
	}
	
	public void queryNthChild(int a,int b){
		Exchange x=findExchangeById(a);
		try{
			if(x==null)throw new Exception("Query cant be done such exchange dont exist");
		    if(b>=x.numChildren())throw new Exception("Exchange "+x+" has only "+x.numChildren()+" number of children");
		}
		catch(Exception e){
			System.out.println(e);
			return;
		}
		System.out.println(x.child(b).id);
	}
	
	public void queryMobilePhoneSet(int a){
	Exchange x=findExchangeById(a);
		try{
			if(x==null)throw new Exception("Query cant be done such exchange dont exist");
		}
		catch(Exception e){
			System.out.println(e);
			return;
		}
		MobilePhoneSet p=x.residentSet();
		node m=p.a.l.firstlink;
		int array1[]=new int[p.a.l.size()];
		int k=0;
		while(m!=null){
			if((((MobilePhone)m.o).status())==true)
			array1[k]=(((MobilePhone)m.o).id);
			k++;
			m=m.next;
		}
		for(int j=array1.length-1;j>=0;j--)
			System.out.print(array1[j]+ " ");
		System.out.println();
	}
	
	void performAction(String msg){
		String[] a=msg.split("\\s+");
		
		if(a.length==2){
			System.out.println(a[0]+" "+a[1]);
			int b=Integer.parseInt(a[1]);
			if(a[0].equals("switchOffMobile")){
				switchOffMobile(b);
			}
			else if(a[0].equals("queryMobilePhoneSet")){
				queryMobilePhoneSet(b);
			}
			else{
				try{
					throw new Exception("Invalid statement!!!");
				}
				catch(Exception e){
					System.out.println(a.length);
					System.out.println(e);
					return;
				}
			}
		}
		else if(a.length==3){
			System.out.println(a[0]+" "+a[1]+" "+a[2]);
			int a1=Integer.parseInt(a[1]);
			int b1=Integer.parseInt(a[2]);
			if(a[0].equals("addExchange")){
				addExchange(a1,b1);
			}
			else if(a[0].equals("queryNthChild")){
				queryNthChild(a1,b1);
			}
			else if(a[0].equals("switchOnMobile")){
				switchOnMobile(a1,b1);
			}
			else{
				try{
					throw new Exception("Invalid statement!!!");
				}
				catch(Exception e){
					System.out.println(a.length);
					System.out.println(e);
					return;
				}
			}
		}
		else{
			try{
				throw new Exception("Invalid statement!!!");
			}
			catch(Exception e){
				System.out.println("Size is neither 2 nor 3");
				System.out.println(e);
				return;
			}
		}
		
	}
		
	
}
