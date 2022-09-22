public class TestDiskManager {

  public static void TestEcriturePage(int fileIdx) {

  }

  public static void TestLecturePage(int fileIdx) {

  }

  public static void TestAllocPage() {
    
  }

  public static void main(String[] args) {
    DBParams.DBPath = args[0];
    DBParams.pageSize = 2;
    DBParams.maxPagesPerFile = 4;
  }
}
