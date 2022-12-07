import java.io.File;

public class DropDb{

  
    public static void execute(){
        BufferManager.getInstance().reset();
        Catalog.getCatalog().reset();
        DiskManager.getLeDiskManager().vide();
        supprimerFichier();

    }
  
    public static void supprimerFichier(){
        File lesFic = new File(DBParams.DBPath);
        File [] liste = lesFic.listFiles();
        for(int i=0; i<liste.length; i++){
            liste[i].delete();
        }
    }
}