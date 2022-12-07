import java.io.IOException;
import java.util.ArrayList;


public class Insert {
    
    private String nomRelation;
    private ArrayList<String> recordValues;

    public Insert(String saisie) throws IOException{
        this.recordValues = new ArrayList<>();
        parse(saisie);
    }

    private void parse(String saisie) throws IOException {
        String [] splitChain;
        String [] valeurs;
        String tmpStr;
        splitChain= saisie.split(" ");
        tmpStr = splitChain[4].substring(1,splitChain[4].length()-1);
        valeurs= tmpStr.split(",");
        this.nomRelation= splitChain[2];

        for(String valeur: valeurs ){
            recordValues.add(valeur);
        }

    }

    public void execute() throws IOException{
        Record r = new Record(Catalog.getCatalog().getRelationInfo(nomRelation),recordValues);
        FileManager.getInstance().insertRecordIntoRelation(r);
        viderValeursRecords();

    }

    private void viderValeursRecords(){
        for(int i=0 ; i<recordValues.size() ; i++){
            recordValues.remove(i);
        }
    }
}