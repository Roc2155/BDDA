import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BufferManager {
	private static int FrameCount = DBParams.frameCount;
	DiskManager diskmanager = DiskManager.getLeDiskManager();
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
			int emplacemnet = ListeDesPages.lastIndexOf(pageid);
			ListeDesFrames.get(emplacemnet).isDirty();//on le met dirty
			ListeDesFrames.get(emplacemnet).setPin_count(ListeDesFrames.get(emplacemnet).getPin_count()+1);//On incremente le pincount
			return ListeDesFrames.get(emplacemnet).getBuff();
		}
		else {
			ArrayList<PageId> ListeDesPropres = new ArrayList<PageId>();
			for(Frame frame : ListeDesFrames) {
				if(!frame.isDirty()) {
					ListeDesPropres.add(ListeDesPages.get(ListeDesFrames.lastIndexOf(frame)));			
				}
				
			}
			if(ListeDesPropres.size()==0) {
				int valeurMinPinCount = 1147483647;
				Frame frameCible = new Frame();
				for(Frame frame : ListeDesFrames) {
					if(frame.getPin_count()<valeurMinPinCount) {
						valeurMinPinCount=frame.getPin_count();
						frameCible=frame;
					}
				}
				int framePose = ListeDesFrames.lastIndexOf(frameCible);
				int pagePose = framePose;
				FreePage(ListeDesPages.get(pagePose),true);
				ListeDesPages.set(pagePose, pageid);
				return ListeDesFrames.get(pagePose).getBuff();	
				//Politique de remplacement
			}
			else {
				
				ArrayList<Frame> ListeDesFramesPropres = new ArrayList<Frame>();
				for(Frame frame : ListeDesFrames) {
					if(ListeDesPropres.contains(ListeDesPages.get(ListeDesFrames.lastIndexOf(frame)))) {
						ListeDesFramesPropres.add(frame);
					}
				}
				int valeurMinPinCount = 1147483647;
				Frame frameCible = new Frame();
				for(Frame frame : ListeDesFramesPropres) {
					if(frame.getPin_count()<valeurMinPinCount) {
						valeurMinPinCount=frame.getPin_count();
						frameCible=frame;
					}
				}
				int framePose = ListeDesFrames.lastIndexOf(frameCible);
				int pagePose = framePose;
				ListeDesPages.set(pagePose, pageid);
				return ListeDesFrames.get(pagePose).getBuff();	
				
			}
		}
		
		
		
	}
	public void FreePage(PageId pageid, boolean valdirty) {
			
	}
	public void FlushAll() {
		for(Frame frame : ListeDesFrames) {
			if(frame.isDirty()) {
				FreePage(ListeDesPages.get(ListeDesFrames.lastIndexOf(frame)), true);
				frame.setDirty(false);
			}
		}
		
	}

}
