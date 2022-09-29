import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BufferManager {
	private static int FrameCount = DBParams.frameCount;
	DiskManager diskmanager = new DiskManager();
	public static boolean setFrame = false;
	private static ArrayList<Frame> ListeDesFrames = new ArrayList<Frame>();
	private static ArrayList<PageId> ListeDesPages = new ArrayList<PageId>();
	
	public void setAllFrame() {
		for(int i = 0; i<FrameCount;i++) {
			ListeDesFrames.add(new Frame());
		}
	};
	
	
	public ByteBuffer GetPage(PageId pageid) {
		ArrayList<PageId> ListeDesPagesVides = new ArrayList<PageId>();
		ArrayList<PageId> ListeDesPagesPleines = new ArrayList<PageId>();
		for(PageId pageidlook : ListeDesPages) {
			if(pageidlook==pageid) {
				return ListeDesFrames.get(ListeDesPages.indexOf(pageidlook)).getBuff();
			}
			else if(pageidlook==null) {
				ListeDesPagesVides.add(pageidlook);
			}
			else {
				ListeDesPagesPleines.add(pageidlook);
			}
			
		}
		//si il y a des cases de pages vides on met dans cette cases
		if(!ListeDesPagesVides.isEmpty()) {
			ListeDesPages.add(ListeDesPages.lastIndexOf(ListeDesPagesVides.get(0)),pageid);
		}
		
		//Faire des comparaisons avec les dirty et pincount dans les frames de la liste des frames
		
		
	}
	public void FreePage(PageId pageid, boolean valdirty) {
			
	}
	public void FlushAll() {
		for(Frame frame : ListeDesFrames) {
			if(frame.isDirty()) {
				diskmanager.WritePage(frame.get, null);
			}
		}
		
	}

}
