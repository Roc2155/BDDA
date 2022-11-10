import java.io.IOException;

public class TestCatalog {
	public static RelationInfo createRelationInfo(PageId page) {
		RelationInfo rel1 = new RelationInfo("Etudiant", 3, page);
		rel1.addRelaInfo("VARCHAR", 15, "Nom");
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

			BufferManager.getInstance().getPage(page);

			Record r1 = createRecord(rel1);


		}catch(IOException e) {
			e.printStackTrace();
		}

	}
}
