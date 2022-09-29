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

	  System.out.println(DiskManager.ListeDePagesAlloue);
	  System.out.println(DiskManager.ListeDePagesNonAlloue);
	  DiskManager.getLeDiskManager().allocPage();//C'est sensé creer un nouveau fichier selon ceux qui sont déja dans le repertoire
	  System.out.println(DiskManager.ListeDePagesAlloue.toString());
	  System.out.println(DiskManager.ListeDePagesNonAlloue.toString());

	//  DiskManager.getLeDiskManager().AllocPage();
	  System.out.println(DiskManager.ListeDePagesAlloue.toString());
	  System.out.println(DiskManager.ListeDePagesNonAlloue.toString());
//	  DiskManager.getLeDiskManager().AllocPage();
	  System.out.println(DiskManager.ListeDePagesAlloue.toString());
	  System.out.println(DiskManager.ListeDePagesNonAlloue.toString());
	  //doit afficher 3 pages dans le tableau d'allocation

/*    DiskManager diskmanager = DiskManager.getLeDiskManager();
    if(diskmanager == null) {
      System.out.println("r");
    }
    else {
      System.out.println("ok");
    }*/
  }

public static void main (String [] args){
	 DBParams.DBPath = args[0];
	 DBParams.pageSize = 2;
	 DBParams.maxPagesPerFile = 4;
	 DBParams.frameCount = 2;
	 //ByteBuffer buff = new ByteBuffer();
	 //TestDiskManager.TestEcriturePage(PageId pageId, ByteBuffer buff);

//   DiskManager.getLeDiskManager();
   TestAllocPage();
   //DiskManager.LeDiskManager.AllocPage();
}



}
