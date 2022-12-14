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
			if(tokenizer.countTokens()<=MAX_CRITERE) {
				while(tokenizer.hasMoreElements()) {
					String token = tokenizer.nextToken();
					conditions = new StringBuffer();
					if(!token.equals("AND")) {
						conditions.append(token+" ");
					}
				}
			}
			else {
				System.out.println("Critères maximum 20 !!");
				System.exit(1);
			}
		}
	}

	public void Execute() {

	}

	public void parseCritere() {
		recordTokens = new StringTokenizer(conditions.toString(), " ");
		String operation = "";
		while(recordTokens.hasMoreElements()) {
			String condition = recordTokens.nextToken(); //1 critère
			if(condition.contains("==")) {
				String[] nomColEtValeur = condition.split("==");
				operation = "==";
				recordsSelected(nomColEtValeur, operation);
			}
			else if(condition.contains(">=")) {
				String[] nomColEtValeur = condition.split(">=");
				operation = ">=";
				recordsSelected(nomColEtValeur, operation);
			}
			else if(condition.contains("<=")) {
				String[] nomColEtValeur = condition.split("<=");
				operation = "<=";
				recordsSelected(nomColEtValeur, operation);
			}
			else if(condition.contains("<")) {
				String[] nomColEtValeur = condition.split("<");
				operation = "<";
				recordsSelected(nomColEtValeur, operation);
			}
			else if(condition.contains(">")) {
				String[] nomColEtValeur = condition.split(">");
				operation = ">";
				recordsSelected(nomColEtValeur, operation);
			}
			else if(condition.contains("<>")) {
				String[] nomColEtValeur = condition.split("<>");
				operation = "<>";
				recordsSelected(nomColEtValeur, operation);
			}
		}
	}

	public void recordsSelected(String[] nomColEtValeur, String operation) {
		RelationInfo rel = Catalog.getCatalog().getRelationInfo(nomRelation);
		PageId headerPage = rel.getHeaderPageId();
		try {
			List<Record> listAllRecords = FileManager.getInstance().getAllRecords(rel);
			for(Record record : listAllRecords) {

			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
