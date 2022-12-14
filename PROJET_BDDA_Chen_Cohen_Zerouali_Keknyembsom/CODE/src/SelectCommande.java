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
	private ArrayList<String> listRecords;

	public SelectCommande(String ch) {
		resultatRecord = new ArrayList<Record>();
		listRecords = new ArrayList<String>();

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
		String operation = "";
		while(recordTokens.hasMoreElements()) {
			String condition = recordTokens.nextToken();
			if(condition.contains("==")) {
				String[] recordsCondition = condition.split("==");
				listRecords.add(recordsCondition[0]);
				listRecords.add(recordsCondition[1]);
				operation = "==";
			}
			else if(condition.contains(">=")) {
				String[] recordsCondition = condition.split(">=");
				listRecords.add(recordsCondition[0]);
				listRecords.add(recordsCondition[1]);
				operation = ">=";
			}
			else if(condition.contains("<=")) {
				String[] recordsCondition = condition.split("<=");
				listRecords.add(recordsCondition[0]);
				listRecords.add(recordsCondition[1]);
				operation = "<=";
			}
			else if(condition.contains("<")) {
				String[] recordsCondition = condition.split("<");
				listRecords.add(recordsCondition[0]);
				listRecords.add(recordsCondition[1]);
				operation = "<";
			}
			else if(condition.contains(">")) {
				String[] recordsCondition = condition.split(">");
				listRecords.add(recordsCondition[0]);
				listRecords.add(recordsCondition[1]);
				operation = ">";
			}
			else if(condition.contains("<>")) {
				String[] recordsCondition = condition.split("<>");
				listRecords.add(recordsCondition[0]);
				listRecords.add(recordsCondition[1]);
				operation = "<>";
			}
		}
	}

	public void RecordsSelected(ArrayList<String> list, String operation) {

	}
}
