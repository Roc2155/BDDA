import java.io.IOException;

public class Main
{
    public static void main(String [] args) throws IOException, ClassNotFoundException {
        DBParams.DBPath = args[0];
        DBParams.pageSize = 4096;
        DBParams.maxPagesPerFile = 4;
        DBParams.frameCount = 2;
        /*DiskManager diskManager = new DiskManager();
        PageId pageId1 = new PageId(1,0);
        //System.out.println(pageId1.compareTo(new PageId(1,0)));
        /*diskManager.DeallocPage(pageId1);
        diskManager.DeallocPage(new PageId(2,0));
        diskManager.DeallocPage(new PageId(3,0));*/
        /*PageId pageId =  diskManager.allocPage();
        //diskManager.ReadPage(pageId);
        System.out.println(pageId.getFileIdx()+", "+ pageId.getPageIdx());
        System.out.println(diskManager.getCurrentCountAllocPages());*/
        DiskManager.getLeDiskManager().allocPage();
        System.out.println(DiskManager.getLeDiskManager().getCurrentCountAllocPages());

    }
}
