import java.util.ArrayList;
import java.io.Serializable;

public class RelationInfo implements Serializable {
	private ArrayList<ColInfo> liste;
	private String nomRelation;
	private int nbcolonnes;
	private PageId headerPage;
	private int nbColonnes;

	public RelationInfo(String nomRelation, ArrayList<ColInfo> liste, PageId headerPage) {
		this.liste=liste;
		this.nomRelation=nomRelation;
		this.nbcolonnes= liste.size();
		this.headerPage = headerPage;
	}
	public RelationInfo(String nomRelation, int nbColonnes, ArrayList<ColInfo> a, PageId pId){
        this.nomRelation= nomRelation;
        this.nbColonnes= nbColonnes;
        this.liste= a;
        this.headerPage=pId;
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
	public String toString() {

        StringBuffer sb = new StringBuffer("nom relation : " + this.nomRelation +  "\nnbColonnes " + this.nbColonnes +"\n");
        return sb.toString();
    }

}
