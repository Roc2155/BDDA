import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
class LireCsv {

    private   File f;
    private ArrayList<String>rowTab;

    public LireCsv(String nomFichier) throws FileNotFoundException{


        f = new File(nomFichier);


        rowTab = new ArrayList<String>();

    }
    
    
    public void afficheTab(){
        for(int i = 0 ; i < rowTab.size();i++) {
            System.out.println("ligne "+ i+1 + " : "+ rowTab.get(i));
        }
    }
    public ArrayList<String> lireFichier() throws IOException {
        FileReader fR = new FileReader(f);
        BufferedReader bR = new BufferedReader(fR);
        String line = "";

        while((line = bR.readLine()) != null) {
            rowTab.add(line);  
        }
        bR.close();
        return rowTab;
    }
}