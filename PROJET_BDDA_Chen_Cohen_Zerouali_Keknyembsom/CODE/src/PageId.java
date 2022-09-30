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
}
