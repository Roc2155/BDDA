import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BufferManager {
	private static int FrameCount = DBParams.frameCount;
	DiskManager diskmanager = new DiskManager();
	public static boolean setFrame = false;
	private static ArrayList<Frame> ListeDesFrames = new ArrayList<Frame>();
	
	public void setAllFrame() {
		for(int i = 0; i<FrameCount;i++) {
			ListeDesFrames.add(new Frame());
		}
	};
	
	
	public ByteBuffer GetPage(PageId pageid) {
		if(ListeDesFrames.size()<FrameCount) {//Si on peut rajouter des frames on en rajoute
			int i;
			for(Frame frame : ListeDesFrames) {
				if(frame!=null) {
					ListeDesFrames.add(frame);
					i = ListeDesFrames.lastIndexOf(frame);
				}
				else {}
			}
			
			
			
			
			return ListeDesFrames.get(ListeDesFrames.size()-1).getBuff();
			
			
		}
		else {//Pas de possibilité de rajout de frame, on va donc devoir verifier si elles sont vide, puis eventuellement en liberer
			
		}//Faire des comparaisons avec les dirty et pincount dans les frames de la liste des frames
		
		
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
