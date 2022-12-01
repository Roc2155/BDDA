import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.util.Arrays;

public class TestDiskManager {

  public static void TestEcriturePage(PageId pageId, ByteBuffer buff) {
    System.out.println("Tableau de buffer : " + Arrays.toString(buff.array()));
    try {
      DiskManager.getLeDiskManager().WritePage(pageId, buff);
    } catch(IOException e) {
      e.printStackTrace();
    }
    finally {
      System.out.println("Fermeture du fichier");
    }
  }

  public static void TestLecturePage(PageId pageId) {
    try {
    	ByteBuffer bf = ByteBuffer.allocate(DBParams.pageSize);
    	DiskManager.getLeDiskManager().readPage(pageId, bf);
    } catch(IOException e) {
    	e.printStackTrace();
    }
    finally {
      System.out.println("Fermeture du fichier");
    }
  }

  public static void TestAllocPage() {
	  try {
      System.out.println("Etat initial de la liste des pages non allouées : " + DiskManager.getLeDiskManager().getListeDePagesNonAlloue().toString());
    } catch(IOException e) {
      e.printStackTrace();
    }
    //C'est censé créer un nouveau fichier selon ceux qui sont déja dans le repertoire
    try {
      DiskManager.getLeDiskManager().allocPage();
      DiskManager.getLeDiskManager().allocPage();

      DiskManager.getLeDiskManager().allocPage();

      System.out.println("Etat de la liste de pages allouées après allocation des pages: " + DiskManager.getLeDiskManager().getListeDePagesAlloue().toString());
  	  System.out.println("Etat de la liste de pages non allouées : " + DiskManager.getLeDiskManager().getListeDePagesNonAlloue().toString());
    } catch(IOException e) {
      System.out.println("err");
      e.printStackTrace();
    }
    try {
      DiskManager.getLeDiskManager().allocPage();
      System.out.println("Etat de la liste de pages allouées après allocation des pages: " + DiskManager.getLeDiskManager().getListeDePagesAlloue().toString());
  	  System.out.println("Etat de la liste de pages non allouées : " + DiskManager.getLeDiskManager().getListeDePagesNonAlloue().toString());
    } catch(IOException e) {
      e.printStackTrace();
    }
    try {
      DiskManager.getLeDiskManager().allocPage();
      System.out.println("Etat de la liste de pages allouées après allocation des pages: " + DiskManager.getLeDiskManager().getListeDePagesAlloue().toString());
  	  System.out.println("Etat de la liste de pages non allouées : " + DiskManager.getLeDiskManager().getListeDePagesNonAlloue().toString());
    } catch(IOException e) {
      e.printStackTrace();
    }
	  //doit afficher 3 pages dans le tableau d'allocation
  }


  public static void TestDeallocPage(PageId pageId) {
    try {
      DiskManager.getLeDiskManager().DeallocPage(pageId);
    } catch(IOException e) {
        e.printStackTrace();
    }
    try {
      if(DiskManager.getLeDiskManager().getListeDePagesNonAlloue().contains(pageId)) {
        System.out.println("Page désallouée avec succès");
      }
      System.out.println(DiskManager.getLeDiskManager().getListeDePagesNonAlloue().toString());
    } catch(IOException e) {
      e.printStackTrace();
    }
  }


  public static void main (String [] args){
	  DBParams.DBPath = args[0];
	  DBParams.pageSize = 5;
	  DBParams.maxPagesPerFile = 5; //On aura max 5 pages : 0, 1, 2, 3, 4
	  DBParams.frameCount = 2;
	  
    PageId pageId = new PageId(2, 1);
    PageId pageId1 = new PageId(0, 1);
    PageId pageId2 = new PageId(0, 2);
    ByteBuffer buff = ByteBuffer.wrap("test".getBytes()); //On convertie la chaine de caractère en tableau binaire qu'on met dans un tampon

    try {
		    System.out.println("Etat de la liste de pages allouées : " + DiskManager.getLeDiskManager().getListeDePagesAlloue().toString());
	  } catch (IOException e) {
		   e.printStackTrace();
	  }

    TestAllocPage();
    TestDeallocPage(pageId); //Test : désalocation de la page 1 du fichier 2
    //Résultat attendu : erreur si page non allouée sinon désallocation avec succès

    TestEcriturePage(pageId, buff); //Test : écriture du mot 'test' sur la page 1 du fichier 2
    //Résultat attendu : erreur car page désallouée
    TestLecturePage(pageId);//Lecture : lecture sur la page 1 du fichier 2
    //Résultat attendu : erreur car page désallouée

    TestEcriturePage(pageId1, buff); //Test : écriture du mot 'test' sur la page 1 du fichier 0
    //Résultat attendu : écriture avec succès car 1er fichier créé avec les pages 0, 1, 2
    TestLecturePage(pageId1);//Test : lecture sur la page 1 du fichier 0
    //Résultat attendu : lecture avec succès

    TestEcriturePage(pageId2, buff); //Test : écriture du mot 'test' sur la page 1 du fichier 0
    //Résultat attendu : écriture avec succès car 1er fichier créé avec les pages 0, 1, 2

    //TestDeallocPage(new PageId(0, 2));
  }


}
