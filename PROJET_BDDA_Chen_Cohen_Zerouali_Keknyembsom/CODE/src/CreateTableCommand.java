import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
		schemaRel.charAt(0);
		schemaRel.charAt(schemaRel.length()-1);

		StringTokenizer stBis=new StringTokenizer(schemaRel, ",");
		this.nombreCol=stBis.countTokens();

		String chaineCourante;
		while(stBis.hasMoreTokens()) {
			chaineCourante=stBis.nextToken();
			StringTokenizer ch=new StringTokenizer(chaineCourante,":");

			this.nomColonne.add(ch.nextToken());
			this.typeColonne.add(ch.nextToken());

		}

		//System.out.println("nombre col"+nombreCol);


}

	public void Execute() {

		try {
			PageId pageid=FileManager.getInstance().createNewHeaderPage() ;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		RelationInfo rel=new RelationInfo( nomRelation, nombreCol, nomColonne, PageId);
		

	}
	}

}
