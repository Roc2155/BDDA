import java.nio.ByteBuffer;

public class Frame {

	private PageId PID;
	private ByteBuffer buff;
	private int pin_count;
	private int dirty;
	private int temps_free;
	
	public Frame() {
	
	}
	
	public PageId getPID() {
		return PID;
	}
	public void setPID(PageId pID) {
		PID = pID;
	}
	public ByteBuffer getBuff() {
		return buff;
	}
	public void setBuff(ByteBuffer buff) {
		this.buff = buff;
	}
	public int getPin_count() {
		return pin_count;
	}
	public void setPin_count(int pin_count) {
		this.pin_count = pin_count;
		
	
	}
	public int getDirty() {
		return dirty;
	}
	public void setDirty(int dirty) {
		this.dirty = dirty;
	}



	public int getTemps_free() {
		return temps_free;
	}

	public void setTemps_free(int temps_free) {
		this.temps_free = temps_free;
	}

	
}