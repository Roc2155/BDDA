import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelationInfo implements Serializable
{
    private String nomRelation;
    private int nbrCol;
    private List<ColInfo> list;

    public RelationInfo(String nomRelation, int nbrCol)
    {
        this.nomRelation = nomRelation.toLowerCase();
        this.nbrCol = nbrCol;
        this.list = new ArrayList<>(this.nbrCol);
    }

    public void addRelaInfo(String nomType,String nomCol)
    {
        if (list.size() < nbrCol) {
            Type type = null;
            String nom = nomType.toUpperCase();
            if (nom.equals("INTEGER"))
                type = new Type("INTEGER", 4);
            else if (nom.equals("REAL"))
                type = new Type("REAL", 4);
            try {
                if (type != null)
                    list.add(new ColInfo(type , nomCol));
                else
                    throw new RuntimeException("Erreur initialisation coloneInfo");
            } catch (Exception e) {
                System.out.println("Erreur initialisation coloneInfo");

            }
        }
        else
            System.out.println("Limite de colone atteint dans la relation");
    }
    public void addRelaInfo(String nomType,int taille ,String nomCol)
    {
        if (list.size() < nbrCol) {
            Type type = null;
            String nom = nomType.toUpperCase();
            if (nom.equals("VARCHAR"))
                type = new Type("VARCHAR", taille);
            else if ((nom.equals("REAL"))||(nom.equals("INTEGER")))
            {
                addRelaInfo(nomType, nomCol);
                System.out.println("Mauvaise configuration mais élément ajouté avec succès");
                return;
            }
            try {
                if (type != null)
                    list.add(new ColInfo(type, nomCol));
                else
                    throw new RuntimeException("Erreur initialisation coloneInfo");
            } catch (Exception e) {
                System.out.println("Erreur initialisation coloneInfo");
            }
        }
        else
            System.out.println("Limite de colonne atteint dans la relation. la colonne "+nomCol+" ne peut pas être ajouté");
    }

    public void removeColRelaInfo(String nomCol)
    {
        int i = list.size();
        for (ColInfo colInfo : list)
        {
            if (colInfo.getNom().equals(nomCol)) {
                list.remove(colInfo);
                break;
            }
        }
        if (i != list.size())
            System.out.println("Colonne "+nomCol+" retiré avec succès.");
        else
            System.out.println(nomCol+" n'existe pas dans la liste.");
    }
    public String getNomRelation() {
        return nomRelation;
    }

    public int getNbrCol() {
        return nbrCol;
    }

    public List<ColInfo> getList(){
        return list;
    }
}
