import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestFileManager {
	public static void main(String[] args) {
		DBParams.DBPath = args[0];
		DBParams.pageSize = 5;
		DBParams.maxPagesPerFile = 4;
		DBParams.frameCount = 5;

		FileManager fm = FileManager.getInstance();
		BufferManager.getInstance().init();

		try {
			PageId page = fm.createNewHeaderPage();
			ColInfo col1 = new ColInfo("Nom", "VARCHAR(15)");
			ColInfo col2 = new ColInfo("Prenom", "VARCHAR(15)");
			ArrayList<ColInfo> listeColonne = new ArrayList<ColInfo>();
			listeColonne.add(col1);
			listeColonne.add(col2);
			RelationInfo rel1 = new RelationInfo("Personne", listeColonne, page);

			Record r1 = new Record(rel1);
		    r1.add("Cohen");
		    r1.add("Rahel");

			//fm.getAllDataPages(rel1);

			List<PageId> listPage = new ArrayList<PageId>();

			listPage = fm.getAllDataPages(rel1);

			//System.out.println(listPage.size());
		}
		catch(IOException e) {
			e.printStackTrace();
		}


	}
}
