import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SelectCommande {
	private ArrayList<Record> resultatRecord;
	private final static int MAX_CRITERE = 20;
	private String nomRelation;
	private StringBuffer conditions;
	private StringTokenizer recordTokens;

	public SelectCommande(String ch) {
		resultatRecord = new ArrayList<Record>();

		//Parsing de la chaine saisie par l'utilisateur
		StringTokenizer tokenizer = new StringTokenizer(ch, " *");
		tokenizer.nextToken(); //SELECT
		tokenizer.nextToken(); //FROM
		nomRelation = tokenizer.nextToken();
		if(tokenizer.hasMoreElements()) {
			tokenizer.nextToken(); //WHERE
			while(tokenizer.hasMoreElements()) {
				String token = tokenizer.nextToken();
				conditions = new StringBuffer();
				if(!token.equals("AND")) {
					conditions.append(token+" ");
				}
			}
		}
		System.out.println(conditions.toString());
	}

	public void Execute() {

	}

	public void recordsSelected() {
		recordTokens = new StringTokenizer(conditions.toString(), " ");
		while(recordTokens.hasMoreElements()) {

		}
	}
}
