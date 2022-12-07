import java.io.IOException;
import java.util.ArrayList;


public class Select{

    private final static int MAX_CRITERES = 20;
    private  ArrayList<Record> recordResultat;
    private  String nomRelation;
    private  String[] commande;
    
    public Select(String saisie){
    	parse(saisie);
    	
    	recordResultat=new ArrayList<>();
        
    }
    


    
    private void parse(String cmd){
        commande = cmd.split("WHERE");
        String[] cmdTemp = commande[0].split(" ");
        this.nomRelation=cmdTemp[3];
    }

    
    public  void execute() throws IOException{
        selectedRecords();
        afficherRecords();
    }

    public void afficherRecords(){
        for(int i=0 ; i<recordResultat.size();i++){
            System.out.println(recordResultat.get(i).toString());
        }
        System.out.println("Total records = "+recordResultat.size());
    }


    
    private void selectedRecords() throws IOException{
        if(commande.length == 2){
            
        	
            ArrayList<SelectCondition> critere = listeCriteres();
            ArrayList<Record> allRecords= FileManager.getInstance().getAllRecords(Catalog.getCatalog().getRelationInfo(nomRelation));
            for (Record record : allRecords) {
                int i=0;
                boolean result=true;
                while(i<critere.size() && result){
                    int indiceColumn= listeCriteres().get(i).getIndiceColonne();
                    String type = record.getRelInfo().getListe().get(indiceColumn).getType();
                    switch (type) {
                    	case "REAL":
                    		result= listeCriteres().get(i).verifConditionReal(Float.valueOf(record.getValues().get(indiceColumn)));
                    		break;
                        case "INTEGER":
                            result= listeCriteres().get(i).verifConditionInt(Integer.valueOf(record.getValues().get(indiceColumn)));
                            break;
                        
                       
                        default:
                            result= listeCriteres().get(i).verifConditionString(record.getValues().get(indiceColumn));
                            break;
                    }
                    i++;
                }
                if(result){
                    recordResultat.add(record);
                }
            }
            
            
        }else{
            recordResultat=FileManager.getInstance().getAllRecords(Catalog.getCatalog().getRelationInfo(nomRelation));
        }
    }
     
    

  
    private ArrayList<SelectCondition> listeCriteres(){
        String[] critere = commande[1].split("AND");
        
        ArrayList<SelectCondition>  sc = new ArrayList<>();
        
        if(critere.length <= MAX_CRITERES){
            
        	for(int i=0 ; i< critere.length ; i++){
                sc.add(parseCmd(critere[i]));
            }
        }else{
            System.out.println("Plus de 20 critères");
            System.exit(1);
        }
        
        return sc;
    }

    
    
    private SelectCondition parseCmd(String str){
        int indice=0; 
        String op="";
        String val="";
        for(int i=0 ; i< SelectCondition.getOperateur().length;i++){
            if(str.contains(SelectCondition.getOperateur()[i])){
                String[] condition = str.split(SelectCondition.getOperateur()[i]);
                indice = getIndiceColonne(condition[0].substring(1)); 
                op = SelectCondition.getOperateur()[i];
                if(condition[1].contains(" ")){
                    val = condition[1].substring(0,condition[1].length()-1);
                }else
                    val = condition[1];
            }
        }
        return new SelectCondition(indice,op,val);
    }

    private int getIndiceColonne(String nomColonne){
        RelationInfo r = Catalog.getCatalog().getRelationInfo(nomRelation);

        for(int i=0 ; i<r.getNb() ; i++){
            if(r.getListe().get(i).getNom().equals(nomColonne)){
                return i;
            }
        }
        return -1;
    }

    
}