public class SearchEngine {
	InvertedPageIndex ipi;
	
	SearchEngine(){
		ipi=new InvertedPageIndex();
	}
	void addPage(String s){
		PageEntry x=new PageEntry(s);
		//System.out.println("//"+x.getPagename()+x.getPageIndex().getWordEntries().toString());
		ipi.addPage(x);
		ipi.checkaddedpages.addElement(s);
		
	}
	
	public MySet<PageEntry> queryFindPagesWhichContainWord(String x){
		return ipi.getPagesWhichContainWord(x);
	}
	
	public void queryFindPositionsOfWordInAPage(String word,String page){
		MySet<PageEntry> s=queryFindPagesWhichContainWord(word);
		if(s==null){
			System.out.println("Word "+word+" Not Found!!!");
			return;
		}
		node<PageEntry> n=s.getL().getFirstlink();
		n=s.getL().getFirstlink();
		while(n!=null){
			PageEntry pagecontainingword=n.getO();
			
			if(pagecontainingword.getPagename().equals(page)){ 
			PageIndex ind=pagecontainingword.getPageIndex();
			//ind.displaywordsinindex();
			//System.out.println("no.of times "+word+" appears= "+ ind.nofwordsinpage(word,page));
			System.out.print("Positions where " +word+" appears- ");
			int pos[]=ind.positionsinpagewherewordappears(word,page);
			for(int j=pos.length-1;j>=0;j--){
				System.out.print(pos[j]+" ");
			}
			System.out.println();
			return;
		   }
			n=n.getNext();
		}
		if(n==null){
		node<String> x=ipi.checkaddedpages.getL().getFirstlink();
		while(x!=null){
			if(x.getO().equals(page))break;
			x=x.getNext();
		}
		if(x==null){
			System.out.println("Page "+page+" Not Found- First Add The Page!!!!");
			return;
		}
		else{
			System.out.println("Word "+word+" not found in given page!!!");
		}
		}
	}
	
	
	
	void performAction(String msg){
		String[] a=msg.split("\\s+");
		
		if(a.length==2){
			System.out.println("->"+a[0]+" "+a[1]);
			if(a[0].equals("addPage")){
				addPage(a[1]);
			}
			else if(a[0].equals("queryFindPagesWhichContainWord")){
				MySet<PageEntry> p=queryFindPagesWhichContainWord(a[1]);
				MySet<String> temp=new MySet<String>();
				if(p!=null){
					node<PageEntry> m=p.getL().getFirstlink();
					while(m!=null){
						if(!temp.isMember(m.getO().getPagename())){
						System.out.print(m.getO().getPagename()+"  ");
						temp.addElement(m.getO().getPagename());
						}
						m=m.getNext();
					}
				}
				System.out.println();
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
			System.out.println("->"+a[0]+" "+a[1]+" "+a[2]);
			if(a[0].equals("queryFindPositionsOfWordInAPage")){
				//addExchange(a1,b1);
				queryFindPositionsOfWordInAPage(a[1],a[2]);
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
