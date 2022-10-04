import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BufferManager {
	private static int FrameCount = DBParams.frameCount;
	DiskManager diskmanager = DiskManager.getLeDiskManager();
	public static boolean setFrame = false;
	private static ArrayList<Frame> ListeDesFrames = new ArrayList<Frame>();
	private static ArrayList<PageId> ListeDesPages = new ArrayList<PageId>();
	private int time;
	
	public void setAllFrame() {//A lancer au debut du programme
		for(int i = 0; i<FrameCount;i++) {
			ListeDesFrames.add(new Frame());
			ListeDesPages.add(null);
		}
		time=0;
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
			ListeDesPages.set(ListeDesPages.lastIndexOf(ListeDesPagesVides.get(0)),pageid);//On rajoute pageid a un emplacement precis de la liste
			int emplacemnet = ListeDesPages.lastIndexOf(pageid);
			Frame tmpframe = ListeDesFrames.get(emplacemnet);
			tmpframe.setPin_count(tmpframe.getPin_count()+1);//On incremente le pincount
			
			return tmpframe.getBuff();
		}
		else {//Si aucunes casesPages vides
			ArrayList<Frame> ListeDesFramesAPin0 = new ArrayList<Frame>();//Liste des pages a pin 0
			for(Frame frame : ListeDesFrames) {
				if(frame.getPin_count()==0) {
					ListeDesFramesAPin0.add(frame);//on y ajoute toute les frames à pin 0
				}
			if(ListeDesFrames.size()==0) {
				System.out.println("ERROR CRITICAL");//Aucune frame à pin 0
				return null;
			}
			Frame framecible = null;;
			int tempsreference = 9999999;
			for(Frame frame2 : ListeDesFramesAPin0) {
				if(tempsreference<frame2.getTime()) {//Parmis les pages à 0 on prends la plus ancienne LRU
					tempsreference=frame2.getTime();
					framecible=frame2;
				}	
			}
			PageId pageADelete = ListeDesPages.get(ListeDesFrames.lastIndexOf(framecible));
			FreePage(pageADelete,true);
			ListeDesPages.set(ListeDesFrames.lastIndexOf(framecible), pageid);
			int emplacement = ListeDesPages.lastIndexOf(pageid);
			
			
			return ListeDesFrames.get(emplacement).getBuff();
			
			}
		}
		return null;//si pblm
		
		
		
	}
	public void FreePage(PageId pageid, boolean valdirty) {
			Frame tmpFrame = ListeDesFrames.get(ListeDesPages.lastIndexOf(pageid));
			tmpFrame.setPin_count(tmpFrame.getPin_count()-1);
			tmpFrame.setDirty(valdirty);
			tmpFrame.setTime(time);
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
