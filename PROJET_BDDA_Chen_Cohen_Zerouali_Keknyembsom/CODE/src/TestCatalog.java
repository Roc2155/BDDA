import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class TestCatalog {
	public static RelationInfo createRelationInfo(PageId headerPage) {

		ColInfo col1 = new ColInfo("Nom","VARCHAR(15)");
		ColInfo col2 = new ColInfo("Prenom","VARCHAR(15)");
		ColInfo col3 = new ColInfo("N°","INTEGER");
		ArrayList<ColInfo> listeColonnes = new ArrayList<ColInfo>();
		listeColonnes.add(col1);
		listeColonnes.add(col2);
		listeColonnes.add(col3);

		RelationInfo rel1 = new RelationInfo("Etudiant", listeColonnes, headerPage);

		return rel1;
	}

	public static Record createRecord(RelationInfo rel) {
		return new Record(rel);
	}

	public static void main(String[] args) {
		DBParams.DBPath = args[0];
		DBParams.pageSize = 5;
		DBParams.maxPagesPerFile = 4;
		DBParams.frameCount = 5;

		BufferManager.getInstance().init();

		PageId headerPage = new PageId(0, 3);

		try {
			Catalog.getCatalog().init();
			RelationInfo rel1 = createRelationInfo(headerPage);
			System.out.println("HeaderPage de la relation " + rel1.getNom() + " : " + rel1.getHeaderPageId());
			System.out.println("Nombre de colonne de la relation " + rel1.getNom() + " : " + rel1.getNb());

      System.out.println("Liste : " + rel1.getListe());

      System.out.println("Info de la page : ");
      BufferManager.getInstance().getPage(headerPage);

      Record r1 = createRecord(rel1);
      System.out.println("Info du record : " + r1.getRelInfo());
      System.out.println("Valeur du record : " + r1.getValues());

      r1.add("Cohen");
      r1.add("Rahel");
      r1.add("45");
      System.out.println("Valeur du record après définition du record: ");
      System.out.println(r1.getValues());

      System.out.println("taille du record : "+r1.getWrittenSize());
      ByteBuffer bb = ByteBuffer.allocate(r1.getWrittenSize());
      r1.writeToBuffer(bb, 0);
      System.out.println("yyyy");
      for (int i = 0;i< r1.getWrittenSize();i++) {
			System.out.print(bb.get()+" ");
		}
/*
      //Avec un buffer
      ByteBuffer buffNom = ByteBuffer.wrap("Zerouali".getBytes());
      ByteBuffer buffPre = ByteBuffer.wrap("Faycal".getBytes());
      ByteBuffer buffNum = ByteBuffer.wrap("2".getBytes());


      r1.writeToBuffer(buffNom, 0);
      r1.writeToBuffer(buffPre, 1+buffNom.capacity());
      r1.writeToBuffer(buffNum, 1+buffNom.capacity()+buffPre.capacity());
*/
		}catch(IOException e) {
			e.printStackTrace();
		}

	}
}
