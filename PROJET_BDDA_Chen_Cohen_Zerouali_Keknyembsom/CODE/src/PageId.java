
public class PageId {
	private int fileIdx;
	private int pageIdx;
	
	public PageId(int fileIdx, int pageIdx) {
		this.fileIdx = fileIdx;
		this.pageIdx=pageIdx;
	}

	public int getFileIdx() {
		return fileIdx;
	}



	public int getPageIdx() {
		return pageIdx;
	}
	private boolean equals(PageId pageid) {
		if(this.getPageIdx()==pageid.getPageIdx()&&this.getFileIdx()==pageid.getFileIdx()) {
			return true;
		}
		else {
			return false;
		}
	}

}
