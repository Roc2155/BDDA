import java.io.IOException;
import java.util.ArrayList;

public class CreateTableCommand {
    private String nomRelation;
    private int nombreColonnes;
    private ArrayList<String> nomColonnes;
    private ArrayList<String> typeColonnes;
   
    
    
    
    
    public CreateTableCommand(String saisie) {
        nomColonnes= new ArrayList<>();
        typeColonnes=new ArrayList<>();
        parse(saisie);
    }

   
    
    
    
    private void parse(String saisie) {
        String [] splitChain;
        String [] colonnes;
        splitChain= saisie.split(" ");
        String tmpColl = splitChain[3].substring(1,splitChain[3].length()-1);
        colonnes= tmpColl.split(",");
        this.nomRelation= splitChain[2];
        this.nombreColonnes= colonnes.length;

        for(String colonne: colonnes){
            String [] split= colonne.split(":");
            String name= split[0];
            String type= split[1];
            this.nomColonnes.add(name);
            this.typeColonnes.add(type);
        }
    }
    
    public void execute() throws IOException{
        PageId HeaderPid = FileManager.getInstance().createNewHeaderPage();
        ArrayList<ColInfo> liste= new ArrayList<>(nombreColonnes);
        ColInfo col;
        for(int i=0; i<nomColonnes.size(); i++){
            col= new ColInfo(nomColonnes.get(i), typeColonnes.get(i));
            liste.add(col);
        }
        RelationInfo relation = new RelationInfo(this.nomRelation, this.nombreColonnes,liste, HeaderPid);
        Catalog.getCatalog().addRelationInfo(relation);
    }
    public void setTypeColonnes(ArrayList<String> typeColonnes) {
        this.typeColonnes = typeColonnes;
    }
    
   
    public String getNomRelation() {
        return nomRelation;
    }
    /**
     * @param nomRelation 
     * modification du nom de la relation
     */
    public void setNomRelation(String nomRelation) {
        this.nomRelation = nomRelation;
    }
    
    public int getNombreColonnes() {
        return nombreColonnes;
    }
    /**
     * @param nombreColonnes
     * modification du nombre de colonnes
     */
    public void setNombreColonnes(int nombreColonnes) {
        this.nombreColonnes = nombreColonnes;
    }
    /**
     * @return noms des colonnes
     * 
     */
    public ArrayList<String> getNomColonnes() {
        return nomColonnes;
    }
    /**
     * @param nomColonnes
     * modification des noms des colonnes
     */
    public void setNomColonnes(ArrayList<String> nomColonnes) {
        this.nomColonnes = nomColonnes;
    }
    /**
     * @return les types de colonnes
     */
    public ArrayList<String> getTypeColonnes() {
        return typeColonnes;
    }
    /**
     * @param typeColonnes 
     * modification des types de colonnes
     */
    
    @Override
    /**
     * 
     */
    public String toString() {
        return "CreateTableCommand [nomRelation=" + nomRelation + ", nombreColonnes=" + nombreColonnes
                + ", nomColonnes=" + nomColonnes + ", typeColonnes=" + typeColonnes + "]";
    }



}