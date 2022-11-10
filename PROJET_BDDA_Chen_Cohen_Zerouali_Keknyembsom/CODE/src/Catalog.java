import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Catalog
{
    private static Catalog catalog;
    private File svCatalog = new File(DBParams.DBPath+File.separator+"catalog.sv");
    private List<RelationInfo> list = new ArrayList<>();
    private Catalog(){
    }
    public static Catalog getCatalog(){
        if (catalog==null){
            catalog=new Catalog();
        }
        return catalog;
    }

    public void init() throws IOException {
        if (svCatalog.length() != 0) {
            InputStream is = new FileInputStream(svCatalog);
            ObjectInputStream ois = new ObjectInputStream(is);
            list.clear();
            boolean restaL = false;
            try {
                restaL = true;
                while (restaL) {
                    try {
                        RelationInfo rf = (RelationInfo) ois.readObject();
                        list.add(rf);
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
    public void finish() throws IOException {
        OutputStream os = new FileOutputStream(svCatalog);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        for (RelationInfo relationInfo : list)
        {
            oos.writeObject(relationInfo);
        }
        oos.close();
    }

    public void addRelationInfo(RelationInfo r) {
        if(!list.contains(r)) {
            list.add(r);
            System.out.println("Ajout de la relation dans la liste avec succès!");
        }
        else {
            System.out.println("Relation existante dans la liste de relation");
        }
    }

    public RelationInfo getRelationInfo(String nom) {
        for (RelationInfo relationInfo : list) {
            if (relationInfo.getNomRelation().equals(nom))
                return relationInfo;
        }
        System.out.println("La relatation " + nom + " n'existe pas.");
        return null;
    }

    public int getNbreRelation()
    {
        return list.size();
    }
}
