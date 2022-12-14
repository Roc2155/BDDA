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
				splitColetVal(nomColEtValeur, operation);
			}
			else if(condition.contains(">=")) {
				String[] nomColEtValeur = condition.split(">=");
				operation = ">=";
				splitColetVal(nomColEtValeur, operation);
			}
			else if(condition.contains("<=")) {
				String[] nomColEtValeur = condition.split("<=");
				operation = "<=";
				splitColetVal(nomColEtValeur, operation);
			}
			else if(condition.contains("<")) {
				String[] nomColEtValeur = condition.split("<");
				operation = "<";
				splitColetVal(nomColEtValeur, operation);
			}
			else if(condition.contains(">")) {
				String[] nomColEtValeur = condition.split(">");
				operation = ">";
				splitColetVal(nomColEtValeur, operation);
			}
			else if(condition.contains("<>")) {
				String[] nomColEtValeur = condition.split("<>");
				operation = "<>";
				splitColetVal(nomColEtValeur, operation);
			}
		}
	}

	public void splitColetVal(String[] nomColEtValeur, String operation) {
		String nomCol = nomColEtValeur[0];
		String valeur = nomColEtValeur[1];
		int colIndice = -1;
		RelationInfo rel = Catalog.getCatalog().getRelationInfo(nomRelation);
		List<ColInfo> listColInfo = rel.getListe();
		for(int i=0; i<listColInfo.size(); i++) {
			if(nomCol.equals(listColInfo.get(i).getNom())) {
				colIndice = i;
			}
		}
		RecordSelected(colIndice, valeur, operation);
	}

	public void RecordSelected(int colIndice, String valeur, String operation) {
		RelationInfo rel = Catalog.getCatalog().getRelationInfo(nomRelation);
		String typeCol;
		Boolean resultat = false;
		try {
			List<Record> listAllRecords = FileManager.getInstance().getAllRecords(rel);
			for(Record record : listAllRecords) {
				typeCol = record.getRelInfo().getListe().get(colIndice).getType();
				if(typeCol.equals("REAL")) {
					Float val = Float.parseFloat(valeur);
					resultat = verifConditionReal(operation, val, Float.valueOf(record.getValues().get(colIndice)), colIndice);
				}
				else if(typeCol.equals("INTEGER")) {
					int val = Integer.parseInt(valeur);
					resultat = verifConditionInteger(operation, val, Integer.valueOf(record.getValues().get(colIndice)), colIndice);
				}
				else {
					resultat = verifConditionVarchar(operation, valeur, record.getValues().get(colIndice), colIndice);
				}
				if(resultat) {
					resultatRecord.add(record);
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public Boolean verifConditionReal(String operation, float valeur, float recordVal, int colIndice) {
		Boolean resultCritere = false;
		if(operation.equals("=")) {
			if(valeur==recordVal) {
				resultCritere = true;
			}
		}
		else if(operation.equals(">=")) {
			if(valeur<=recordVal) {
				resultCritere = true;
			}
		}
		else if(operation.equals("<=")) {
			if(valeur>=recordVal) {
				resultCritere = true;
			}
		}
		else if(operation.equals(">")) {
			if(valeur<recordVal) {
				resultCritere = true;
			}
		}
		else if(operation.equals("<")) {
			if(valeur>recordVal) {
				resultCritere = true;
			}
		}
		else if(operation.equals("<>")) {
			if(valeur!=recordVal) {
				resultCritere = true;
			}
		}
		return resultCritere;
	}

	public Boolean verifConditionInteger(String operation, int valeur, int recordVal, int colIndice) {
		Boolean resultCritere = false;
		if(operation.equals("=")) {
			if(valeur==recordVal) {
				resultCritere = true;
			}
		}
		else if(operation.equals(">=")) {
			if(valeur<=recordVal) {
				resultCritere = true;
			}
		}
		else if(operation.equals("<=")) {
			if(valeur>=recordVal) {
				resultCritere = true;
			}
		}
		else if(operation.equals(">")) {
			if(valeur<recordVal) {
				resultCritere = true;
			}
		}
		else if(operation.equals("<")) {
			if(valeur>recordVal) {
				resultCritere = true;
			}
		}
		else if(operation.equals("<>")) {
			if(valeur!=recordVal) {
				resultCritere = true;
			}
		}
		return resultCritere;
	}

	public Boolean verifConditionVarchar(String operation, String valeur, String recordVal, int colIndice) {
		Boolean resultCritere = false;
		if(operation.equals("=")) {
			if(valeur.equals(recordVal)) {
				resultCritere = true;
			}
		}
		else if(operation.equals(">=")) {
			int val = Integer.parseInt(valeur);
			int rVal = Integer.parseInt(recordVal);
			if(rVal>=val) {
				resultCritere = true;
			}
		}
		else if(operation.equals("<=")) {
			int val = Integer.parseInt(valeur);
			int rVal = Integer.parseInt(recordVal);
			if(rVal<=val) {
				resultCritere = true;
			}
		}
		else if(operation.equals(">")) {
			int val = Integer.parseInt(valeur);
			int rVal = Integer.parseInt(recordVal);
			if(rVal>val) {
				resultCritere = true;
			}
		}
		else if(operation.equals("<")) {
			int val = Integer.parseInt(valeur);
			int rVal = Integer.parseInt(recordVal);
			if(rVal<val) {
				resultCritere = true;
			}
		}
		else if(operation.equals("<>")) {
			if(!valeur.equals(recordVal)) {
				resultCritere = true;
			}
		}
		return resultCritere;
	}
}
