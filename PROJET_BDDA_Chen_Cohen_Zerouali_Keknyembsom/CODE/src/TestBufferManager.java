import java.io.IOException;



public class TestBufferManager {
	public static void TestBuffer() {
		PageId pid = new PageId(0, 1);
		PageId pid2 = new PageId(0, 2);
		PageId pid3 = new PageId(0, 3);
		
		try {
			BufferManager.getInstance().getPage(pid);
			BufferManager.getInstance().getPage(pid2);
			BufferManager.getInstance().getPage(pid);
			BufferManager.getInstance().FreePage(pid2, 1);
			BufferManager.getInstance().FreePage(pid, 0);
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
        DBParams.pageSize = 4;
        DBParams.maxPagesPerFile = 4;
        DBParams.frameCount = 2;
		BufferManager.getInstance().init();
		TestBufferManager.TestBuffer();
	}
		
	
}
