import java.nio.ByteBuffer;

public class DiskManager {
	private static int CurrentAllocPages=0;

	public PageId AllocPage () {
		return null;
	}

	public void WritePage (PageId pageId, ByteBuffer buff) {

	}
	public void ReadPage (PageId pageId, ByteBuffer buff) {

	}
	public void DeallocPage (PageId pageId ) {

	}
	public int GetCurrentCountAllocPages() {
		return CurrentAllocPages;
	}

}
