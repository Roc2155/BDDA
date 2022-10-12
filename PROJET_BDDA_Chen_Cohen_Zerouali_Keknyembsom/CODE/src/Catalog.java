import java.util.List;
import java.util.ArrayList;

public class Catalog {
	private Catalog leCatalog = null;
	private List<RelationInfo> listeRelatInfo;
	private int cptRelat;

	public void Init() {

	}
	public void Finish() {

	}
	private Catalog() {
		leCatalog = new Catalog();
		listeRelatInfo = new ArrayList<RelationInfo>();
		cptRelat = 0;
	}
	public Catalog getLeCatalog() {
		return leCatalog;
	}
	public void addRelation(RelationInfo r) {
		if(!listeRelatInfo.contains(r)) {
			listeRelatInfo.add(r);
			cptRelat++;
			System.out.println("Ajout de la relation dans la liste avec succès!");
		}
		else {
			System.out.println("Relation existante dans la liste de relation");
		}
	}
	public RelationInfo getRelationInfo(String nom) {
		RelationInfo relatInfo = new RelationInfo();
		for(RelationInfo r : listeRelatInfo) {
			if(r.nom.equals(nom)) {
				relatInfo = r;
			}
		}
		return relatInfo;
	}
}
