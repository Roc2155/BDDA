import java.io.IOException;
import java.nio.ByteBuffer;

public class TestCatalog {
	public static RelationInfo createRelationInfo(PageId page) {
		RelationInfo rel1 = new RelationInfo("Etudiant", 3, page);
		rel1.addRelaInfo("VARCHAR", 15, "Nom");
    rel1.addRelaInfo("VARCHAR", 15, "Prenom");
    rel1.addRelaInfo("INTEGER", "Age");
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

      System.out.println("Liste : " + rel1.getList());

      System.out.println("Info de la page : ");
      BufferManager.getInstance().getPage(page);

      Record r1 = createRecord(rel1);
      System.out.println("Info du record : " + r1.getRelInfo());
      System.out.println("Valeur du record : " + r1.getValues());
      String[] tuple1 = {"Cohen", "Rahel", "20"};
      r1.setValues(tuple1);
      System.out.println("Valeur du record après définition du record: ");
      System.out.println(r1.toString());

      //Avec un buffer
      ByteBuffer buffNom = ByteBuffer.wrap("Zerouali".getBytes());
      ByteBuffer buffPre = ByteBuffer.wrap("Faycal".getBytes());
      ByteBuffer buffNum = ByteBuffer.wrap("1".getBytes());
      r1.writeToBuffer(buffNom, 0);
      r1.writeToBuffer(buffPre, 0+buffNom.capacity());
      r1.writeToBuffer(buffNum, 0+buffNom.capacity()+buffPre.capacity());
      r1.readFromBuffer(buffPre, buffNom.capacity());

		}catch(IOException e) {
			e.printStackTrace();
		}

	}
}
