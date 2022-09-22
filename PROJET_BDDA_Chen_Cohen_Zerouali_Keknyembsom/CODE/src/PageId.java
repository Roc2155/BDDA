
public class PageId {
	private int fileIdx;
	private int pageIdx;
	
	public PageId(int fileIdx, int pageIdx) {
		this.setFileIdx(fileIdx);
		this.setPageIdx(pageIdx);
	}

	public int getFileIdx() {
		return fileIdx;
	}



	public int getPageIdx() {
		return pageIdx;
	}


}
