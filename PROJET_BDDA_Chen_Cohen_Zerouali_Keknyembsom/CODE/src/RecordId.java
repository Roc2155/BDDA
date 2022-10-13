public class RecordId
{
    private PageId pageId;
    private int slotIdx;

    public RecordId(PageId pageId, int slotIdx)
    {
        this.pageId = pageId;
        this.slotIdx = slotIdx;
    }

    public PageId getPageId(){
        return pageId;
    }
    public int getSlotIdx() {
        return slotIdx;
    }
}
