import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class TestDiskManager {

  public static void TestEcriturePage(PageId pageId, ByteBuffer buff) {

  }

  public static void TestLecturePage(PageId pageId, ByteBuffer buff) {


  }

  public static void TestAllocPage() {
    //ByteBuffer bufferTest = ByteBuffer.allocate(2);
    //System.out.println(bufferTest.position());
    //PageId pageIdTest = DiskManager.AllocPage(2);
    //System.out.println(pageIdTest.position());

	  System.out.println("Etat de la liste des pages non allouées : " + DiskManager.getLeDiskManager().getListeDePagesNonAlloue().toString());

    DiskManager.getLeDiskManager().allocPage();//C'est sensé creer un nouveau fichier selon ceux qui sont déja dans le repertoire
	  System.out.println("Etat de la liste de pages allouées après allocation des pages: " + DiskManager.getLeDiskManager().getListeDePagesAlloue().toString());
	  System.out.println("Etat de la liste de pages non allouées : " + DiskManager.getLeDiskManager().getListeDePagesNonAlloue().toString());

	  DiskManager.getLeDiskManager().allocPage();
	  System.out.println(DiskManager.getLeDiskManager().getListeDePagesAlloue().toString());
	  System.out.println(DiskManager.getLeDiskManager().getListeDePagesNonAlloue().toString());

    DiskManager.getLeDiskManager().allocPage();
	  System.out.println(DiskManager.getLeDiskManager().getListeDePagesAlloue().toString());
	  System.out.println(DiskManager.getLeDiskManager().getListeDePagesNonAlloue().toString());
	  //doit afficher 3 pages dans le tableau d'allocation
  }


  public static void TestDeallocPage(PageId pageId) {
    DiskManager.getLeDiskManager().DeallocPage(pageId);
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
    /*
        DiskManager diskmanager = DiskManager.getLeDiskManager();
        if(diskmanager == null) {
          System.out.println("r");
        }
        else {
          System.out.println("ok");
        }
    */
	  //ByteBuffer buff = new ByteBuffer();
	  //TestDiskManager.TestEcriturePage(PageId pageId, ByteBuffer buff);
   //TestAllocPage();
  }


}
