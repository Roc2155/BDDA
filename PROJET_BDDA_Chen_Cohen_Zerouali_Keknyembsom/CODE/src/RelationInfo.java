import java.util.ArrayList;


public class RelationInfo {
	private ArrayList<ColInfo> liste;
	private String nomRelation;
	private int nbcolonnes;
	
	
	public RelationInfo(String nomRelation,ArrayList<ColInfo> liste) {
		this.liste=liste;
		this.nomRelation=nomRelation;
		this.nbcolonnes= liste.size();
	}
	
	public String getNom() {
		return this.nomRelation;
	}
	
	public int getNb() {
		return this.nbcolonnes;
	}
	public ArrayList<ColInfo> getListe() {
		return this.liste;
	}
}
