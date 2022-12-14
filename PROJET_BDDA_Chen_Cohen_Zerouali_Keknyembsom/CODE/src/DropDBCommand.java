import java.io.File;
import java.io.IOException;


public class DropDBCommand{


	public DropDBCommand(String ch) {

	}
    public void Execute(){
    	//suppression du dossier DB
    	deleteDB(DBParams.DBPath);

    	//remise � z�ro dans les classes inf�rieurs
    	try {
    		BufferManager.getInstance().FlushAll();
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
    		System.out.println(liste[i].getName());
    	}
    	if (liste !=null) {
    		 for(File item : liste){
    		        if(item.isFile())
    		        {
    		        	//PAS SUR QUON PUISSE SUPPRIMER LES GITIGNORE ...QUI SONT DANS CE DOSSIER
    		        	//TODO
    		        	//item.delete();

    		          //affichage des noms des fichiers/r�pertoires :
    		          //System.out.format("Nom du fichier: %s%n", item.getName());
    		        }
    		        else if(item.isDirectory())
    		        {
    		        	//appel recursif
    		        	deleteDB(item.getPath());


    		          //System.out.format("Nom du r�pertoire: %s%n", item.getName());

    		      }
    		      //source : https://waytolearnx.com/2020/03/lister-le-contenu-dun-dossier-en-java.html
    	     }

	    }


  }





}
