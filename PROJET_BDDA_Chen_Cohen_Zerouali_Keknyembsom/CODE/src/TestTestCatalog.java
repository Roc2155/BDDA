import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TestTestCatalog {
	public static void main(String[] args) { 
		
		
		
		ArrayList<ColInfo> personne=new ArrayList<ColInfo>();
		personne.add( new ColInfo("nom","VARCHAR(10)"));
		personne.add( new ColInfo("prenom","VARCHAR(10)"));
		personne.add( new ColInfo("age","INTEGER"));
		personne.add( new ColInfo("solde","REAL"));
		RelationInfo relInfo =new RelationInfo("Personne", personne);
		
		
		
		
		
		Record r = new Record(relInfo);
		r.add("Zerouali");
		r.add("Faycal");
		r.add("20");
		r.add("1300");
		
		
		
		
		System.out.println("taille du record = " + r.getWrittenSize());
		ByteBuffer bb = ByteBuffer.allocate(r.getWrittenSize());
		r.writeToBuffer(bb, 0);
		
		for (int i = 0;i< r.getWrittenSize();i++) {
			System.out.print(bb.get()+" ");
		}
		System.out.println();
		r.readFromBuffer(bb, 0);
		for (int i = 0;i< r.getWrittenSize();i++) {
			System.out.print(bb.get()+" ");
		}
		System.out.println();
		
		for (int i=0;i<10;i++) {
			System.out.print(bb.getChar(20+i*2));
		}
		
		System.out.println();
		for (int i=0;i<10;i++) {
			System.out.print(bb.getChar(40+i*2));
		}
		System.out.println();
		System.out.println(bb.getInt(60));
		System.out.print(bb.getFloat(64));
	}

}