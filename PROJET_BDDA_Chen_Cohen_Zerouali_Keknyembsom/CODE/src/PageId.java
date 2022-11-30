import java.io.Serializable;

public class PageId implements Serializable
{
    private int fileIdx;
    private int pageIdx;

    public PageId(int fileIdx, int pageIdx)
    {
        this.fileIdx = fileIdx;
        this.pageIdx = pageIdx;
    }

    public PageId() {
		// TODO Auto-generated constructor stub
	}
    public void setFileIdx(int fileIdx) {
    	this.fileIdx=fileIdx;
    }
    public void setPageIdx(int pageIdx) {
    	this.fileIdx=pageIdx;
    }

	public int getFileIdx() {
        return this.fileIdx;
    }

    public int getPageIdx()
    {
        return this.pageIdx;
    }

    public boolean compareTo(PageId o) {
        if ((this.getPageIdx() == o.getPageIdx()) && (this.getFileIdx() == o.getFileIdx()))
            return true;
        else
            return false;
    }
    public String toString() {
    	return ""+this.getFileIdx()+"FileID"+this.getPageIdx()+"PID";
    }
}
