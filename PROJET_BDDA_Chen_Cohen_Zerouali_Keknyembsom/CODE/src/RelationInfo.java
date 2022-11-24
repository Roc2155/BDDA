import java.util.ArrayList;


public class RelationInfo {
	private ArrayList<ColInfo> liste;
	private String nomRelation;
	private int nbcolonnes;
	private PageId headerPage;

	public RelationInfo(String nomRelation, ArrayList<ColInfo> liste, PageId headerPage) {
		this.liste=liste;
		this.nomRelation=nomRelation;
		this.nbcolonnes= liste.size();
		this.headerPage = headerPage;
	}

	public String getNom() {
		return this.nomRelation;
	}

	public int getNb() {
		return this.nbcolonnes;
	}

	public PageId getHeaderPageId() {
		return headerPage;
	}

	public ArrayList<ColInfo> getListe() {
		return this.liste;
	}
}
