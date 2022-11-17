import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class TestCatalog {
	public static RelationInfo createRelationInfo(PageId page) {

		ColInfo col1 = new ColInfo("Nom","VARCHAR");
		ColInfo col2 = new ColInfo("Prenom","VARCHAR");
		ColInfo col3 = new ColInfo("Age","INTEGER");
		ArrayList<ColInfo> listeColonnes = new ArrayList<ColInfo>();
		listeColonnes.add(col1);
		listeColonnes.add(col2);
		listeColonnes.add(col3);

		RelationInfo rel1 = new RelationInfo("Etudiant", 3, listeColonnes, page);

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

		PageId page = new PageId(0, 3);

		try {
			Catalog.getCatalog().init();
			RelationInfo rel1 = createRelationInfo(page);
			System.out.println("HeaderPage de la relation " + rel1.getNomRelation() + " : " + rel1.getHeaderPageId());
			System.out.println("Nombre de colonne de la relation " + rel1.getNomRelation() + " : " + rel1.getNbrCol());


      System.out.println("Liste : " + rel1.getInfoCol());


      System.out.println("Info de la page : ");
      BufferManager.getInstance().getPage(page);

      Record r1 = createRecord(rel1);
      System.out.println("Info du record : " + r1.getRelInfo());
      System.out.println("Valeur du record : " + r1.getValues());

      r1.getValues().add("Cohen");
      r1.getValues().add("Rahel");
      r1.getValues().add("20");
      System.out.println("Valeur du record après définition du record: ");
      System.out.println(r1.toString());

      //Avec un buffer
      ByteBuffer buffNom = ByteBuffer.wrap("Zerouali".getBytes());
      ByteBuffer buffPre = ByteBuffer.wrap("Faycal".getBytes());
      ByteBuffer buffNum = ByteBuffer.wrap("1".getBytes());

      r1.writeToBuffer(buffNom, 1);
      r1.writeToBuffer(buffPre, 1+buffNom.capacity());
      r1.writeToBuffer(buffNum, 1+buffNom.capacity()+buffPre.capacity());

		}catch(IOException e) {
			e.printStackTrace();
		}

	}
}
