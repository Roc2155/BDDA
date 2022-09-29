import java.nio.ByteBuffer;

public class Frame {
	private static ByteBuffer buff;
	private int pin_count;
	private boolean dirty;
	public Frame() {
		ByteBuffer.allocate(DBParams.pageSize);	
	}
	public ByteBuffer getBuff() {
		return buff;
	}
	public int getPin_count() {
		return pin_count;
	}
	public void setPin_count(int pin_count) {
		this.pin_count = pin_count;
	}
	public boolean isDirty() {
		return dirty;
	}
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

}
