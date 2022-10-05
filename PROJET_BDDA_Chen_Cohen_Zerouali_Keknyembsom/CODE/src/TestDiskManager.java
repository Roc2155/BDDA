import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.util.Arrays;

public class TestDiskManager {

  public static void TestEcriturePage(PageId pageId, ByteBuffer buff) {
    System.out.println("Avant de remplir le buff : " + Arrays.toString(buff.array()));
    for(int i=1; i<=DBParams.pageSize; i++) {
      buff.put((byte)i);
    }
    System.out.println("Après avoir remplis le buff : " + Arrays.toString(buff.array()));
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
    //ByteBuffer bufferTest = ByteBuffer.allocate(2);
    //System.out.println(bufferTest.position());
    //PageId pageIdTest = DiskManager.AllocPage(2);
    //System.out.println(pageIdTest.position());

	  System.out.println("Etat initial de la liste des pages non allouées : " + DiskManager.getLeDiskManager().getListeDePagesNonAlloue().toString());

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
    if(DiskManager.getLeDiskManager().getListeDePagesNonAlloue().contains(pageId)) {
      System.out.println("Page désallouée avec succès");
    }
    System.out.println(DiskManager.getLeDiskManager().getListeDePagesNonAlloue().toString());
  }


  public static void main (String [] args){
	  DBParams.DBPath = args[0];
	  DBParams.pageSize = 2;
	  DBParams.maxPagesPerFile = 4;
	  DBParams.frameCount = 2;

    PageId pageId = new PageId(2, 3);
    ByteBuffer buff = ByteBuffer.allocate(DBParams.pageSize);

    TestAllocPage();
    TestDeallocPage(pageId);
    TestEcriturePage(pageId, buff);
    TestLecturePage(pageId);
  }


}
