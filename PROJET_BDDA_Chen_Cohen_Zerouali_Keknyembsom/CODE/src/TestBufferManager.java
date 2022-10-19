import java.io.IOException;



public class TestBufferManager {
	public static void TestBuffer() {
		PageId pid = new PageId(0, 1);
		PageId pid2 = new PageId(0, 2);
		PageId pid3 = new PageId(0, 3);

		try {
			System.out.print("GET(0, 1) :  ");
			BufferManager.getInstance().getPage(pid);
			System.out.print("GET(0, 2) : ");
			BufferManager.getInstance().getPage(pid2);
			System.out.println("GET(0, 1) : ");
			BufferManager.getInstance().getPage(pid);
			System.out.println("FREE((0, 2), 0)"); //Si la page (0, 2) ets allouée
			BufferManager.getInstance().FreePage(pid2, 0);
			System.out.println("FREE((0, 1), 1)");
			BufferManager.getInstance().FreePage(pid, 1);
			System.out.print("GET(0, 3) : ");
			BufferManager.getInstance().getPage(pid3);

			Frame frame1 = BufferManager.getInstance().getFrame()[0];
			if(pid.getFileIdx()!=frame1.getPID().getFileIdx()&&pid.getPageIdx()!=frame1.getPID().getPageIdx()){
				System.out.println("Erreur");
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String [] args) {
		DBParams.DBPath = args[0];
    DBParams.pageSize = 5;
    DBParams.maxPagesPerFile = 5;
    DBParams.frameCount = 2;
		BufferManager.getInstance().init();
		//TestDiskManager.main(args); //Ecriture sur (0, 1) et page (0, 2) désallouée
		TestBufferManager.TestBuffer();
	}


}
