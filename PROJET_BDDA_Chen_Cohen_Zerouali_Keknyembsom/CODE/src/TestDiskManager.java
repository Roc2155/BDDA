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
	  diskmanager.AllocPage();
	  System.out.println(DiskManager.ListeDePagesAlloue.toString());
	  System.out.println(DiskManager.ListeDePagesNonAlloue.toString());

	  
	  
  }


  public static void main(String[] args) {
    DBParams.DBPath = args[0];
    DBParams.pageSize = 2;
    DBParams.maxPagesPerFile = 4;
    TestAllocPage();
    
  }
}
