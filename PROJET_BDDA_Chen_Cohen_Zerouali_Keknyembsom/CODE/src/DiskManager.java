import java.nio.ByteBuffer;

public class DiskManager {
	private static int CurrentAllocPages=0;
	PageId AllocPage () {
		return null;
	}
	
	void WritePage (PageId pageId, ByteBuffer buff) {
		
	}
	void ReadPage (PageId pageId, ByteBuffer buff) {
		
	}
	void DeallocPage (PageId pageId ) {
		
	}
	int GetCurrentCountAllocPages() {
		return CurrentAllocPages;
	}

}
