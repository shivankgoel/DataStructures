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
	    	if(a==null)throw new Exception("No exchange by id "+x+" exists!!!");
	    	if(b!=null)throw new Exception("Exchange by id "+y+" already exists with parent id "+b.parent().id+" !!!");
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
		if(x==null)throw new Exception("Exchange "+b+" Dont Exist");
		if(x.childlist.size()!=0)throw new Exception("The exchange with id "+b+" is not base exchange!!!");
	}
	catch(Exception e){
		System.out.println(e);
		return;
	}
	
	MobilePhone m;
	m=findMobileByIdInExchange(nonreg,a); //check if mobile phone exists in switched off list
	if(m!=null){
		nonreg.residentSet().Delete(m); //1 imp to write this before m changes
		m.switchOn();
		m.loc=x;
		x.residentSet().Insert(m);
		if(x.parent()!=null)updateparent(x.parent(),m); //2 imp to update parent
	}
	else{
	m=findMobileById(a);  //check if mobile phone exists in switched on list
	if(m==null){       // mobile phone exists nowhere
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
		/*m.loc.residentSet().Delete(m);
		m.switchOn();
		m.loc=x;
		x.residentSet().Insert(m);*/
	 
	 }//closing else - if m is present somewhere 
	}//closing else - if m is not in switched off list
	return;
}
	
	public void updateparent(Exchange x,MobilePhone m){
		if(!x.residentSet().isMember(m)){
			x.residentSet().Insert(m);
		}
		if(x.parent()!=null)updateparent(x.parent(),m);
		return;
	}
	
	public void switchOffMobile(int z){
		MobilePhone p=findMobileById(z);
		MobilePhone q=findMobileByIdInExchange(nonreg,z);
		try{
			if(p==null && q==null)throw new Exception("Mobile by id "+z+" does not exist!!!");
			if(p==null && q!=null && q.status()==false)throw new Exception("Mobile by id "+z+" already switched off!!!");
		}
		catch(Exception e){
			System.out.println(e);
			return;
		}
		Exchange temp=p.location();
		temp.residentSet().Delete(p);
		if(temp.parent()!=null){
		updateparent2(temp.parent(),p);
		}
		p.switchOff();
		nonreg.residentSet().Insert(p);
		p.loc=nonreg; 
	}
	
	public void updateparent2(Exchange x,MobilePhone m){
		if(x.residentSet().isMember(m)){
		x.residentSet().Delete(m);
		}
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
		System.out.print(x.child(b).id);
	}

	public Exchange findPhone(int x){
		MobilePhone m;
		m=findMobileByIdInExchange(nonreg,x);
		if(m!=null && m.status()==false){
			try{
				throw new Exception("Mobile phone "+x+" is switched off!!!");
			}
			catch(Exception e){
				System.out.print(e); return null;
			}
		}
		m=findMobileById(x);
		if(m==null || m.status()==false){
			try{
				throw new Exception("Mobile phone "+x+" does not exist!!!");
			}
			catch(Exception e){
				System.out.print(e); return null;
			}
		}
		return m.location();
	}
	
	public Exchange lowestRouter(int a,int b){
		Exchange x=findExchangeById(a);
		Exchange y=findExchangeById(b);
		try{
			if(x==null)throw new Exception("Exchange with id "+a+" does not exist!!!");
			if(y==null)throw new Exception("Exchange with id "+b+" does not exist!!!");
			if(x.numChildren()!=0)throw new Exception("Exchange with id "+a+" is not base station!!!");
			if(y.numChildren()!=0)throw new Exception("Exchange with id "+b+" is not base station!!!");
		}
		catch(Exception e){
			System.out.println(e); return null;
		}
		Exchange ans=null;
		Exchange t=y;
		while(t!=null){
			Exchange m=x;
			while(m!=null){
				if(m==t){
					ans=m;
					break;
				}
				m=m.parent(); 
			}
			if(ans!=null)break;
			t=t.parent();
		}
		if(ans!=null)
		return ans;
		else
			return null;
	}
	
	public ExchangeList routeCall(int a,int b){
		Exchange x=findPhone(a);
		Exchange y=findPhone(b);
		ExchangeList l1=new ExchangeList(),l2=new ExchangeList();
		if(x!=null && y!=null){
			Exchange m=lowestRouter(x.id,y.id);
			while(x!=m){
				l1.add(x); x=x.parent();
			}
			while(y!=m){
				l2.add(y); y=y.parent();
			}
			l1.add(m);
			while(l2.l.firstlink!=null){
				node k=l2.l.delete();
				l1.add((Exchange)k.o);
			}
			return l1;
		}
		return null;
	}
	  
	public void movePhone(int a,int b){
		MobilePhone x=findMobileById(a);
		Exchange y=findExchangeById(b);
		try{
			if(y==null) throw new Exception("Exchange by id "+b+" does not exist!!!" );
			if(x==null){
				x=findMobileByIdInExchange(nonreg,a);
				if(x==null)throw new Exception("Mobile Phone by id "+a+" does not exist!!!");
				else throw new Exception("Mobile Phone by id "+a+" is switched off!!!");
			}
		}
		catch(Exception e){
			System.out.println(e); return;
		}
		switchOffMobile(a);
		switchOnMobile(a,b);
	}

	public void queryMobilePhoneSet(int a){
		Exchange x=findExchangeById(a);
		try{
			if(x==null)throw new Exception("Query cant be done such exchange dont exist");
		}
		catch(Exception e){
			System.out.print(e);
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
	}
	
	void performAction(String msg){
		String[] a=msg.split("\\s+");
		
		if(a.length==2){
			System.out.println();
			System.out.print(a[0]+" "+a[1]);
			int b=Integer.parseInt(a[1]);
			if(a[0].equals("switchOffMobile")){
				switchOffMobile(b);
			}
			else if(a[0].equals("queryMobilePhoneSet")){
				System.out.print(" : ");
				queryMobilePhoneSet(b);
			}
			else if(a[0].equals("queryFindPhone")||a[0].equals("findPhone")){
				System.out.print(" : ");
				Exchange p=findPhone(b);
				if(p!=null){
					System.out.print("MobilePhone is present at Exchange with id "+p.id);
				}
				
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
			System.out.println();
			System.out.print(a[0]+" "+a[1]+" "+a[2]);
			int a1=Integer.parseInt(a[1]);
			int b1=Integer.parseInt(a[2]);
			if(a[0].equals("addExchange")){
				addExchange(a1,b1);
			}
			else if(a[0].equals("queryNthChild")){
				System.out.print(" : ");
				queryNthChild(a1,b1);
			}
			else if(a[0].equals("switchOnMobile")){
				switchOnMobile(a1,b1);
			}
			else if(a[0].equals("queryLowestRouter")||a[0].equals("lowestrouter")){
				Exchange p=lowestRouter(a1,b1);
				if(p!=null)System.out.print(" : "+p.id);
			}
			else if(a[0].equals("queryFindCallPath")||a[0].equals("findCallPath")){
				System.out.print(" : ");
				ExchangeList p=routeCall(a1,b1);
				if(p!=null){
					for(int i=0;i<p.size();i++){
						Exchange t=p.geti(i);
						if(t!=null)
						System.out.print(t.id+" ");
					}
				}
			}
			else if(a[0].equals("movePhone")){
				movePhone(a1,b1);
				
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
				System.out.println(e);
				return;
			}
		}
		
	}
		
	
}
