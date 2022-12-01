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
    
    
    //A compléter + ajouter la gestion des exceptions
    public void ProcessCommand(String ch){
        StringTokenizer st =new StringTokenizer(ch);
        String deb=st.nextToken();
        switch (deb) {
            case "CREATE ":
                CreateTableCommand createTable = new CreateTableCommand(ch);
                createTable.Execute();
                break;
            case "DROPDB":
                DropDBCommand drop=new DropDBCommand(ch);
                drop.Execute(); 
                break;
            case "INSERT":
                InsertCommand insert=new InsertCommand(ch);
                insert.Execute();
                break;
            case "SELECT":
            	 SelectCommande select= new SelectCommand(ch);
            	 select.Execute();
            
            case "DELETE":
            	DeleteCommand delete = new DeleteCommand(ch);
            	delete.Execute();
            	break;
  
            default:
            	System.out.println("Veuillez entrer une commande valide.");
        }
    }

    
  } 
    
    
    
