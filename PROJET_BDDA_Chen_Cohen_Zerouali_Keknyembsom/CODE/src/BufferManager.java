import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BufferManager {
	private static BufferManager instance;
	private int time;
	private Frame tmp = new Frame();
	private Frame[] listeDesFrames;
	public static BufferManager getInstance() {
		if(instance == null) {
			instance = new BufferManager();
		}
		return instance;
	}
	ArrayList<Frame> lru = new ArrayList();
	public void init () {
		listeDesFrames = new Frame[DBParams.frameCount];
		System.out.println(listeDesFrames.length);
		time =0;
		for(int i=0;i<DBParams.frameCount;i++) {
			listeDesFrames[i] = new Frame();
		}
	}
	private BufferManager() {
	}

	 public void finish() { //pas util pour l'instant

	   }

	 public ByteBuffer getPage(PageId pageId) throws IOException {
	        //Si on trouve la page dans la liste, on return
	        for (int i = 0; i < listeDesFrames.length; i++) {
	            System.out.println(listeDesFrames[i].getPID());
	        	if (listeDesFrames[i].getPID().getFileIdx()==pageId.getFileIdx() && listeDesFrames[i].getPID().getPageIdx()==pageId.getPageIdx()) {
	                listeDesFrames[i].setPin_count(listeDesFrames[i].getPin_count()+1);;
	                return listeDesFrames[i].getBuff();
	            }
	        }
	        for (int i = 0; i < listeDesFrames.length; i++) {
	            //Si on a une frame vide, on retourne
	        	if (listeDesFrames[i].estVide()) {
	                listeDesFrames[i].setPID(pageId);
	                listeDesFrames[i].setPin_count(1);
	                listeDesFrames[i].setDirty(0);
	                DiskManager.getLeDiskManager().readPage(pageId, listeDesFrames[i].getBuff());
	                listeDesFrames[i].toString();
	                return listeDesFrames[i].getBuff();
	            }
	        }
	        //On selectionne pour lru
	        for (int i = 0; i < listeDesFrames.length; i++) {
	            if (listeDesFrames[i].getPin_count() == 0) {
	                lru.add(listeDesFrames[i]);
	            }
	        }
	        
	        //Lru
	        int min = lru.get(0).getTemps_free();
	        tmp=lru.get(0);
	        for (int j = 1; j < lru.size(); j++) {
	            if (min > lru.get(j).getTemps_free()) {
	                min = lru.get(j).getTemps_free();
	                tmp = lru.get(j);
	            }
	        }
	        if (tmp.getDirty() == 1) {
	            DiskManager.getLeDiskManager().WritePage(tmp.getPID(), tmp.getBuff());
	        }
	        int i=0;
	        boolean trouvee=false;
	        while(i<listeDesFrames.length && !trouvee){
	            if(listeDesFrames[i].getTemps_free()==tmp.getTemps_free()){
	                listeDesFrames[i].setPID(pageId);
	                listeDesFrames[i].setPin_count(1);
	                listeDesFrames[i].setDirty(0);
	                DiskManager.getLeDiskManager().readPage(pageId, listeDesFrames[i].getBuff());  
	                listeDesFrames[i].toString();
	                trouvee=true; 
	            }
	            i++;
	        }
	        return listeDesFrames[i-1].getBuff();
	    }
	public void FreePage(PageId PID, int valdirty) {
		int cases = DBParams.frameCount;
		for (int i = 0; i < cases; i++) {
			if (listeDesFrames[i].getPID()==PID) {
				listeDesFrames[i].setDirty(valdirty);
				listeDesFrames[i].setPin_count(listeDesFrames[i].getPin_count()-1);
				time++;
				listeDesFrames[i].setTemps_free(time);
			}
		}
	}
	public void FlushAll() throws IOException {
		int cases = DBParams.frameCount;
		DiskManager disk = DiskManager.getLeDiskManager();
		for (int i = 0; i < cases; i++) {
			if(listeDesFrames[i].getDirty()!=0) {
				disk.WritePage(listeDesFrames[i].getPID(), listeDesFrames[i].getBuff());
				listeDesFrames[i].setDirty(0);
				listeDesFrames[i].setPin_count(0);
				listeDesFrames[i].setBuff(ByteBuffer.allocate(DBParams.pageSize));
				listeDesFrames[i].setTemps_free(0);
			}else {
				listeDesFrames[i].setBuff(ByteBuffer.allocate(DBParams.pageSize));
				listeDesFrames[i].setPin_count(0);
				listeDesFrames[i].setTemps_free(0);
			}
		}
	}
	public Frame[] getFrame() {
		return listeDesFrames;
	}

	public void FreePage(PageId pageIdFile, boolean b) {
		if(b) {
			FreePage(pageIdFile,1);
		}
		else {
			FreePage(pageIdFile,0);
		}
		// TODO Auto-generated method stub
		
	}





}
