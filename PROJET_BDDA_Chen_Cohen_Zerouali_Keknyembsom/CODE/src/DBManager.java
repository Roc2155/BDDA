import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.StringTokenizer;

//Point d'entrée du SGBD
public class DBManager {
    private static DBManager leDBManager; 
	
	public static DBManager getLeDBManager() {

		if(leDBManager == null) {
			leDBManager = new DBManager();
		}
		return leDBManager;
	}
	
    public void Init(){
    	//Appel à la méthode init de catalog + gestion des exceptions
        try {
        	Catalog.getCatalog().init();
        } catch (FileNotFoundException e){
        	System.out.println(" Le fichier n'a pas été trouvé ");
			e.printStackTrace();
        }catch (IOException e) {
        	System.out.println(" Erreur E/S ");
			e.printStackTrace();
        }
   
        //Appel de la méthode init de BufferManager
        BufferManager.getInstance().init();
       
    }
   
    public void Finish() {
    	//Appel de la méthode finish de DiskManager
    	try {
    		Catalog.getCatalog().finish();
    	}catch(IOException e) {
    		e.printStackTrace();
    	}

    	//Appel de la méthode FlushAll de BufferManager
    	
    	try {
        	BufferManager.getInstance().FlushAll();
    	}catch (IOException e) {
    		e.printStackTrace();
    	}	
    }
    
    
    public void processCommand(String  commande){
        String cmd[] = commande.split(" ");

        try{
            switch(cmd[0]){
                case "CREATE":
                    CreateTableCommand c = new CreateTableCommand(commande);
                    c.execute();
                break;
                case "DROPDB":
                    DropDb.execute();
                break;
                case "INSERT":
                    if(commande.contains("FILECONTENTS")){
                        InsertionFile insert= new InsertionFile(commande);
                        insert.insererFichier();
                    }else{
                        Insert i = new Insert(commande);
                        i.execute();
                    }
                break;
                case "SELECT":
                    Select s = new Select(commande);
                    s.execute();
                break;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    
  } 
    
    
    
