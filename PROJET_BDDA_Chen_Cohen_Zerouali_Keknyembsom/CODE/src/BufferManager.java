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

	ArrayList<Frame> lru = new ArrayList<Frame>();

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
	     return null;
	  }

	public void FreePage(PageId PID, int valdirty) {

	}
	public void FlushAll() throws IOException {

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
	public void reset(){
        init();
        for(Frame frame: listeDesFrames){
            frame.setPID(new PageId(-1,0));
            frame.setBuff(ByteBuffer.allocate(DBParams.pageSize));
            frame.setTemps_free(-1);
            frame.setPin_count(0);
            frame.setDirty(false);
        }
    }





}
