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
  }


  public static void main(String[] args) {
    //DBParams.DBPath = args[0];
    //DBParams.pageSize = 2;
    //DBParams.maxPagesPerFile = 4;
    TestAllocPage();
    
  }
}
