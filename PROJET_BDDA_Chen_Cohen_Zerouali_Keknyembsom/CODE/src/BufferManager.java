import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BufferManager {
	private static int FrameCount = DBParams.frameCount;
	DiskManager diskmanager = DiskManager.getLeDiskManager();
	public static boolean setFrame = false;
	private static ArrayList<Frame> ListeDesFrames = new ArrayList<Frame>();
	private static ArrayList<PageId> ListeDesPages = new ArrayList<PageId>();
	
	public void setAllFrame() {//A lancer au debut du programme
		for(int i = 0; i<FrameCount;i++) {
			ListeDesFrames.add(new Frame());
			ListeDesPages.add(null);
		}
	}
	
	
	public ByteBuffer GetPage(PageId pageid) {
		ArrayList<PageId> ListeDesPagesVides = new ArrayList<PageId>();
		ArrayList<PageId> ListeDesPagesPleines = new ArrayList<PageId>();
		for(PageId pageidlook : ListeDesPages) {
			if(pageidlook==pageid) {
				return ListeDesFrames.get(ListeDesPages.indexOf(pageidlook)).getBuff();//Si on trouve la page dans la liste on la retourne directement
			}
			else if(pageidlook==null) {
				ListeDesPagesVides.add(pageidlook);
			}
			else {
				ListeDesPagesPleines.add(pageidlook);
			}
			
		}
		
		if(!ListeDesPagesVides.isEmpty()) {//Si au moins une casePage vide
			ListeDesPages.add(ListeDesPages.lastIndexOf(ListeDesPagesVides.get(0)),pageid);
			int emplacemnet = ListeDesPages.lastIndexOf(pageid);
			ListeDesFrames.get(emplacemnet).isDirty();//on le met dirty
			ListeDesFrames.get(emplacemnet).setPin_count(ListeDesFrames.get(emplacemnet).getPin_count()+1);//On incremente le pincount
			return ListeDesFrames.get(emplacemnet).getBuff();
		}
		else {//Si aucunes casesPages vides
			ArrayList<Frame> ListeDesFramesAPin0 = new ArrayList<Frame>();
			for(Frame frame : ListeDesFrames) {
				if(frame.getPin_count()==0) {
					ListeDesFramesAPin0.add(frame);
				}
			if(ListeDesFrames.size()==0) {
				System.out.println("ERROR CRITICAL");//Aucune frame à pin 0
			}
			//Creer UNE FILE ou on rentrer chaque page à chaque utilisation, et on prendra celle la moins recente 
				
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
