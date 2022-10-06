import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.util.Arrays;

public class TestDiskManager {

  public static void TestEcriturePage(PageId pageId, ByteBuffer buff) {
    System.out.println("Tableau de buffer : " + Arrays.toString(buff.array()));
    try {
      DiskManager.getLeDiskManager().WritePage(pageId, buff);
      System.out.println("Ecriture avec succès !");
    }
    finally {
      System.out.println("Fermeture du fichier");
    }
  }

  public static void TestLecturePage(PageId pageId) {
    DiskManager.getLeDiskManager().ReadPage(pageId);
  }

  public static void TestAllocPage() {
	  try {
      System.out.println("Etat initial de la liste des pages non allouées : " + DiskManager.getLeDiskManager().getListeDePagesNonAlloue().toString());
    } catch(IOException e) {
      e.printStackTrace();
    }
    //C'est sensé creer un nouveau fichier selon ceux qui sont déja dans le repertoire
    try {
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
	  DBParams.pageSize = 2;
	  DBParams.maxPagesPerFile = 4;
	  DBParams.frameCount = 2;

    PageId pageId = new PageId(2, 3);
    PageId pageId1 = new PageId(1, 0);
    ByteBuffer buff = ByteBuffer.wrap("test".getBytes());

    try {
		    System.out.println("Etat de la liste de pages allouées après allocation des pages: " + DiskManager.getLeDiskManager().getListeDePagesAlloue().toString());
	  } catch (IOException e) {
		   e.printStackTrace();
	  }

    TestAllocPage();
    TestDeallocPage(pageId);
    TestEcriturePage(pageId, buff);
    TestEcriturePage(pageId1, buff);
    TestLecturePage(pageId);
    TestLecturePage(pageId1);
  }


}
