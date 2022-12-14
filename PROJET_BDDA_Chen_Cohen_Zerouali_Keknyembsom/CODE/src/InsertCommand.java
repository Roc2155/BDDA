import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class InsertCommand {
	private String nomRelationInfo;
	private List<String> valeurs;

	public InsertCommand(String ch) {
		this.valeurs = new ArrayList<String>();

		//Parsing de la chaine saisie par l'utilisateur
		StringTokenizer tokenizer = new StringTokenizer(ch, " ");
		tokenizer.nextToken(); //INSERT
		tokenizer.nextToken(); //INTO
		nomRelationInfo = tokenizer.nextToken();
		//(val1,val2,...,valn)

	}

	public void Execute() {

	}
}
