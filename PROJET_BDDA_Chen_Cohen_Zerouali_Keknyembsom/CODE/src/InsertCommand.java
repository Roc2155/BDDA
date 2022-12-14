import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.IOException;

public class InsertCommand {
	private String nomRelationInfo;
	private ArrayList<String> valeurs;

	public InsertCommand(String ch) {
		this.valeurs = new ArrayList<String>();

		//Parsing de la chaine saisie par l'utilisateur
		StringTokenizer tokenizer = new StringTokenizer(ch, " ");
		tokenizer.nextToken(); //INSERT
		tokenizer.nextToken(); //INTO
		nomRelationInfo = tokenizer.nextToken();
		//(val1,val2,...,valn)
		StringTokenizer tokensValues = new StringTokenizer(tokenizer.nextToken(), ",)(");
		System.out.println(tokensValues.countTokens());
		while(tokensValues.hasMoreElements()) {
			valeurs.add(tokensValues.nextToken());
		}
		System.out.println("************Valeurs insérées**************");
		for(int i=0; i<valeurs.size(); i++) {
			System.out.print("\t"+valeurs.get(i)+" ");
		}
		System.out.println("");
	}

	public void Execute() {
		RelationInfo rel = Catalog.getCatalog().getRelationInfo(nomRelationInfo);
		Record record = new Record(rel, valeurs);
		try {
			FileManager.getInstance().insertRecordIntoRelation(record);
			valeurs.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
