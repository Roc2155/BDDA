import java.io.IOException;
import java.util.ArrayList;

public class CreateTableCommand {
    private String nomRelation;
    private int nombreColonnes;
    private ArrayList<String> nomColonnes;
    private ArrayList<String> typeColonnes;
   
    
    
    
    
    public CreateTableCommand(String saisie) {
        
    }

   
    
    
    
    private void parse(String saisie) {
        
    }
    
    public void execute() throws IOException{
        
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