import java.io.IOException;
import java.nio.ByteBuffer;

public class BufferManager {
	private static BufferManager instance; 
	private int time;
	private Frame[] listeDesFrames;
	public static BufferManager getInstance() {
		if(instance == null) {
			instance = new BufferManager();
		}
		return instance;
	}
	
	public void init () {
		listeDesFrames = new Frame[DBParams.frameCount];
		time =0;  
		for(int i=0;i<DBParams.frameCount;i++) {
			listeDesFrames[i] = new Frame();
		}
	}
	private BufferManager() {
	}
	
	 public void finish() { //pas util pour l'instant
		   
	   }
	 
public ByteBuffer getPage(PageId PID) throws IOException {
		
		ByteBuffer b = ByteBuffer.allocate(DBParams.pageSize);
		int cases = DBParams.frameCount;
		int j = 0;
		DiskManager disk = DiskManager.getLeDiskManager();
		for (int i = 0; i < cases; i++) {
			if (listeDesFrames[i].getPID() != null) {
				if (listeDesFrames[i].getPID().compareTo(PID)) {
					listeDesFrames[i].setPin_count(listeDesFrames[i].getPin_count() + 1);
					System.out.print(listeDesFrames[i].getBuff());//Special test
					return listeDesFrames[i].getBuff();
				}
			}
		}
		for (int i = 0; i < cases; i++) {
			if (listeDesFrames[i].getPID() == null) {
				listeDesFrames[i].setPin_count(listeDesFrames[i].getPin_count() + 1);
				disk.ReadPage(PID);
				listeDesFrames[i].setBuff(b);
				listeDesFrames[i].setPID(PID);
				return listeDesFrames[i].getBuff();
			}
		}
		
		int cpt = listeDesFrames[0].getTemps_free();
		
		
		for (int i = 1; i < cases; i++) {
			if (cpt > listeDesFrames[i].getTemps_free()) {
				cpt = listeDesFrames[i].getTemps_free();
				j=i;
			}
		}
		if(listeDesFrames[j].getPin_count()==0 && listeDesFrames[j].getDirty()==0) {
			listeDesFrames[j].setPin_count(listeDesFrames[j].getPin_count() + 1);
			disk.ReadPage(PID);
			listeDesFrames[j].setBuff(b);
			listeDesFrames[j].setPID(PID);
			return listeDesFrames[j].getBuff();

		}
		if(listeDesFrames[j].getPin_count()==0 && listeDesFrames[j].getDirty()!=0) {
			disk.WritePage(listeDesFrames[j].getPID(), listeDesFrames[j].getBuff());
			listeDesFrames[j].setPin_count(listeDesFrames[j].getPin_count() + 1);
			disk.ReadPage(PID);
			listeDesFrames[j].setBuff(b);
			listeDesFrames[j].setPID(PID);
			return listeDesFrames[j].getBuff();
		}
		System.out.println("fin");
		return null;

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
	
	
	
	
	
}
