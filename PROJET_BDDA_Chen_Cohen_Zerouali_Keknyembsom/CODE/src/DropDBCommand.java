import java.io.File;
import java.io.IOException;


public class DropDBCommand{


	public DropDBCommand(String ch) {

	}
    public void Execute() {
    	//remise à zéro dans les classes de niveaux inférieurs
    	try {
    		BufferManager.getInstance().FlushAll();
        Catalog.getCatalog().reset();
    		DiskManager.getLeDiskManager().vide();
        //suppression du dossier DB
      	deleteDB(DBParams.DBPath);
    	}catch(IOException e ) {
    		e.printStackTrace();
    	}

    	Catalog.getCatalog().reset();


    	//TODO    reset dans disk manager et file manager maybe !
    }

  //suppression du dossier DB
    private void deleteDB(String path) {
      System.out.println("Current repository : "+path);
    	File repDB= new File (path);
    	File[] liste = repDB.listFiles();
      System.out.println("Liste des fichiers présents dans le répertoire : ");
    	for(int i=0; i<liste.length; i++) {
    		System.out.println("\t"+liste[i].getName());
    	}
    	if (liste !=null) {
    		 for(File item : liste){
           System.out.println("\n*****************SUPPRESSION DES FICHIERS************************");
    		        if(item.isFile()) {
                  item.delete();
    		        	System.out.format("Nom du fichier: %s%n", item.getName());
    		        }
    		        else if(item.isDirectory())
    		        {
                  //appel recursif
    		        	deleteDB(item.getPath());
    		        	System.out.format("Nom du répertoire: %s%n", item.getName());

    		      }
    		      //source : https://waytolearnx.com/2020/03/lister-le-contenu-dun-dossier-en-java.html
    	     }

	    }


  }





}
