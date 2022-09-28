import java.util.ArrayList;
import java.util.Comparator;

public class PageId {
	private int fileIdx;
	private int pageIdx;
	static ArrayList<Integer> ListeDesFichiers = new ArrayList<Integer>();
	
	public PageId(int fileIdx, int pageIdx) {
		this.fileIdx = fileIdx;
		this.pageIdx=pageIdx;
		if(!ListeDesFichiers.contains(fileIdx)) {
			ListeDesFichiers.add(fileIdx);	
			ListeDesFichiers.sort(Comparator.naturalOrder());
		}
		
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
