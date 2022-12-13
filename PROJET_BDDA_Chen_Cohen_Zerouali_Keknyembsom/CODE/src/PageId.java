import java.io.Serializable;

public class PageId implements Serializable
{
    private int fileIdx;
    private int pageIdx;
    private Boolean pageInFrame;

    public PageId(int fileIdx, int pageIdx)
    {
        this.fileIdx = fileIdx;
        this.pageIdx = pageIdx;
        pageInFrame = false;
    }

    public PageId() {
		// TODO Auto-generated constructor stub
	  }

    public void setFileIdx(int fileIdx) {
    	this.fileIdx=fileIdx;
    }

    public void setPageIdx(int pageIdx) {
    	this.pageIdx=pageIdx;
    }

	  public int getFileIdx() {
      return this.fileIdx;
    }

    public int getPageIdx() {
      return this.pageIdx;
    }

    public boolean compareTo(PageId o) {
      if ((this.getPageIdx() == o.getPageIdx()) && (this.getFileIdx() == o.getFileIdx()))
          return true;
      else
          return false;
    }

    public void inFrame(Boolean pageInFrame) {
      this.pageInFrame = pageInFrame;
    }

    public Boolean isInFrame() {
      return pageInFrame;
    }

    public String toString() {
    	StringBuffer s = new StringBuffer();
      s.append("Page("+fileIdx+", "+pageIdx+")");
      return s.toString();
    }
}
