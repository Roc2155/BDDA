import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class InsertionFile {
	private ArrayList<String>recordValues;
    private String nomRelation;    
    private String nomFichier;
    


    

    public void insererFichier(){
        remplirValeurRecords();
        remplirCommande();
    }
    public InsertionFile(String cmd){
        recupererInfos(cmd);
        this.recordValues = new ArrayList<>();
    }

    private void remplirValeurRecords(){
        LireCsv lc;
        try {
            lc = new LireCsv(nomFichier);
            recordValues = new ArrayList<>();
            try {
                recordValues =  lc.lireFichier();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

   

    private void recupererInfos(String cmd){
        String[] cmdSplit = cmd.split(" ");
        this.nomRelation = cmdSplit[2];
        String[] fileSplit = cmdSplit[3].split("\\(");
        String nameFile = fileSplit[1].substring(0,fileSplit[1].length()-1);
        this.nomFichier = nameFile;
    }
    public void afficheTab(){
        for(int i = 0; i < recordValues.size(); i ++){
            System.out.println(recordValues.get(i));
        }
    }

    public String toString(){
        return this.nomRelation+" "+this.nomFichier;
    }
    private void remplirCommande(){
        for(String value : recordValues){
            String chaine = "INSERT INTO " + this.nomRelation + " VALUES (";
            String saisie = chaine + value+")";
            try {
                Insert is = new Insert(saisie);
                is.execute();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}