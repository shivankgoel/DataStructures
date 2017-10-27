import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class PageEntry {
	
	private PageIndex ind;
	private String pagename;
	

	private static String connectors[]={ "a", "an", "the", "they", "these", "for", "is", "are", "of", "or", "and",
			"does", "will", "whose" };
	
	public PageIndex getPageIndex() {
		return ind;
	}

	public void setInd(PageIndex ind) {
		this.ind = ind;
	}

	public String getPagename() {
		return pagename;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}

	PageEntry(String pageName){
		ind=new PageIndex();
		pagename=pageName;
		BufferedReader br=null;
		try{
			try{
			br = new BufferedReader(new FileReader("webpages\\"+pageName));
			}
			catch(Exception e){
				System.out.println("File not found  "+e);
			}
			int c=0;
			String l[]={};
			String line;
			String m;
			
			while ((line = br.readLine()) != null) {
				if(line.equals(""))continue;
				m = line.replaceAll("[{}<>=().,;?#!-\"\'\\[\\]^_]+", " ");
				m.toLowerCase(Locale.ENGLISH);
		    	m.trim();
		    	l=m.split("\\s+");
		    	for(int i=0;i<l.length;i++){
		    		c++;
		    		l[i] = Character.toLowerCase(l[i].charAt(0)) + (l[i].length() > 1 ? l[i].substring(1) : "");
		    		if(l[i].equals("stacks"))l[i]="stack";
		    		if(l[i].equals("applications"))l[i]="application";
		    		if(l[i].equals("structures"))l[i]="structure";
		    		String shivank=l[i].replaceAll("[-:]"," ");
		    		String[] shivanktemp=shivank.split("\\s+");
		    		if(shivanktemp.length>=2){
		    			int k=0;
		    			while(k<2){
		    			if(notbelong(shivanktemp[k])){
			    			Position s=new Position(this, c);
			    			ind.addPositionForWord(shivanktemp[k], s);
			    			//System.out.println("shivank "+shivanktemp[k]);
			    			k++;
			    		}
		    			}
		    		}
		    		else{
		    		if(shivanktemp.length==0)continue;
		    		l[i]=shivanktemp[0];
		    		//System.out.println(l[i]);
		    		if(notbelong(l[i])){
		    			Position s=new Position(this, c);
		    			ind.addPositionForWord(l[i], s);
		    		}
		    		}
		    	}
		    }
			
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	public boolean notbelong(String x){
		for(int i=0;i<connectors.length;i++){
			if(x.equals(connectors[i]))return false;
		}
		return true;
	}
	
	/*public static void main(String[] args) {
		PageEntry x=new PageEntry("test");
		PageIndex ind=x.getPageIndex();
		ind.displaywordsinindex();
		int pos[]=ind.positionsinpagewherewordappears("daddy","test");
		for(int i=pos.length-1;i>=0;i--){
			System.out.println(pos[i]);
		}
	}*/
}
