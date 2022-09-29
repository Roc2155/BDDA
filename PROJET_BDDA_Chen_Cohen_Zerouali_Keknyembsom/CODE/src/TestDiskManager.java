import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class TestDiskManager {

  public static void TestEcriturePage(int fileIdx) {

  }

  public static void TestLecturePage(int fileIdx) {
	  

  }

  public static void TestAllocPage() {
    //ByteBuffer bufferTest = ByteBuffer.allocate(2);
    //System.out.println(bufferTest.position());
    //PageId pageIdTest = DiskManager.AllocPage(2);
    //System.out.println(pageIdTest.position());
	  System.out.println(DiskManager.ListeDePagesAlloue);
	  System.out.println(DiskManager.ListeDePagesNonAlloue);
	  DiskManager diskmanager = new DiskManager();
	  diskmanager.AllocPage();//C'est sensé creer un nouveau fichier selon ceux qui sont déja dans le repertoire
	  System.out.println(DiskManager.ListeDePagesAlloue.toString());
	  System.out.println(DiskManager.ListeDePagesNonAlloue.toString());
	  
	  diskmanager.AllocPage();
	  System.out.println(DiskManager.ListeDePagesAlloue.toString());
	  System.out.println(DiskManager.ListeDePagesNonAlloue.toString());
	  diskmanager.AllocPage();
	  System.out.println(DiskManager.ListeDePagesAlloue.toString());
	  System.out.println(DiskManager.ListeDePagesNonAlloue.toString());
	  //doit afficher 3 pages dans le tableau d'allocation
	  
  }



}
