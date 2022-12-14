import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.IOException;

public class CreateTableCommand {
	private String nomRelation;
	private int nombreCol;
	private List<String> nomColonne;
	private List<String> typeColonne;


	public CreateTableCommand(String chaine) {
		this.nomColonne=new ArrayList<String>();
		this.typeColonne=new ArrayList<String>();

		//parsing de la chaine saisie par l'utilisateur
		StringTokenizer st=new StringTokenizer(chaine, " ");
		st.nextToken();
		st.nextToken();
		nomRelation=st.nextToken();

		String schemaRel= st.nextToken();
		//on retire les parenthèses
		String res=schemaRel.substring(0,schemaRel.length()-1);

		StringTokenizer stBis=new StringTokenizer(res, ",");
		this.nombreCol=stBis.countTokens();

		String chaineCourante;
		while(stBis.hasMoreTokens()) {
			chaineCourante=stBis.nextToken();
			StringTokenizer ch=new StringTokenizer(chaineCourante,":");

			this.nomColonne.add(ch.nextToken());
			this.typeColonne.add(ch.nextToken());

		}



}

	public void Execute() {

		ArrayList<ColInfo> tab=new ArrayList<ColInfo>();
		try {         
			PageId pageId = FileManager.getInstance().createNewHeaderPage() ;
			
			//remplissage du tableau avec des objets ColInfo
			for (int i=0; i<nomColonne.size(); i++) {
				tab.add(new ColInfo(nomColonne.get(i),typeColonne.get(i)));
			}
			RelationInfo rel = new RelationInfo(nomRelation, nombreCol,tab , pageId);
			
			//ajoute RelationInfo au catalogue
			Catalog.getCatalog().addRelationInfo(rel);
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
