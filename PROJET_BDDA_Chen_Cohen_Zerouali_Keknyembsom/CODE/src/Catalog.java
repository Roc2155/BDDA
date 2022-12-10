import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Catalog
{
    private static Catalog catalog;
    private File svCatalog = new File(DBParams.DBPath+File.separator+"catalog.sv");
    private List<RelationInfo> listRelatInfo = new ArrayList<>();
    private Catalog(){
    }

    //Singleton
    public static Catalog getCatalog(){
        if (catalog==null){
            catalog=new Catalog();
        }
        return catalog;
    }

  //méthode qui lit les informations du fichier catalog.sv et remplit le Catalog avec ces informations.
    public void init() throws IOException {
        if (svCatalog.length() != 0) {
            InputStream is = new FileInputStream(svCatalog);
            ObjectInputStream ois = new ObjectInputStream(is);
            listRelatInfo.clear();
            boolean restaL = false;
            try {
                restaL = true;
                while (restaL) {
                    try {
                        RelationInfo rf = (RelationInfo) ois.readObject();
                        listRelatInfo.add(rf);
                        restaL = true;
                    } catch (Exception e) {
                        restaL = false;
                    }
                }
                ois.close();
            } catch (Exception e) {
                restaL = false;
            }
        }
    }
    public void reset(){
        listRelatInfo.clear();
    }

    //sauvegarde les informations du Catalog dans un fichier nommé catalog.sv
    public void finish() throws IOException {
        OutputStream os = new FileOutputStream(svCatalog);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        for (RelationInfo relationInfo : listRelatInfo)
        {
            oos.writeObject(relationInfo);
        }
        oos.close();
    }

    //méthode qui prend en argument une RelationInfo et qui la rajoute à la liste
    public void addRelationInfo(RelationInfo relationInfo) {
      if(!listRelatInfo.contains(relationInfo)) {
            listRelatInfo.add(relationInfo);
            System.out.println("Ajout de la relation dans la liste avec succès!");
        }
        else {
            System.out.println("Relation existante dans la liste de relation");
        }
    }

 //méthode qui prend en argument le nom d’une relation et retourne la RelationInfo associée.
    public RelationInfo getRelationInfo(String nom) {
      RelationInfo r = null;
        for(RelationInfo relationInfo : listRelatInfo) {
          if(relationInfo.getNom().equals(nom)) {
            r = relationInfo;
          }
          else {
            System.out.println("La relation " + nom + " n'existe pas !!");
          }
        }
        return r;
    }

    //getteurs
    public int getNbreRelation()
    {
        return list.size();
    }
}
