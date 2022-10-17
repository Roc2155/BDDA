import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.EmptyStackException;

public class FileManager {
    private static FileManager instance = null;
    public static final FileManager getInstance()
	{
		if (instance == null)
			instance = new FileManager();
		return instance;
	}
    public PageId createNewHeaderPage() throws IOException
	{
		DiskManager dm = DiskManager.getLeDiskManager();
		BufferManager  bm = BufferManager.getInstance();

		PageId pageId = dm.allocPage();

		ByteBuffer buf = bm.getPage(pageId);

		EcrirePageIdToBuff(new PageId(-1, 0), buf, true);
		EcrirePageIdToBuff(new PageId(-1, 0), buf, false);

		bm.FreePage(pageId, 1);

		return pageId;
	}
    
    public PageId addDataPage(RelationInfo relInfo) throws IOException
	{
		DiskManager dm = DiskManager.getLeDiskManager();
		BufferManager bm = BufferManager.getInstance();

		PageId pageId = dm.allocPage();

		ByteBuffer bufHeaderPage = bm.getPage(relInfo.getHeaderPageId());
		ByteBuffer buf = bm.getPage(pageId);
		PageId nextPageId = LirePageIdToBuff(bufHeaderPage, true);

		EcrirePageIdToBuff(pageId, bufHeaderPage, true);
		EcrirePageIdToBuff(relInfo.getHeaderPageId(), buf, true);
		EcrirePageIdToBuff(nextPageId, buf, false);
		
		bm.FreePage(pageId, 1);
		bm.FreePage(relInfo.getHeaderPageId(), 1);

		return pageId;
	}
    public PageId getFreeDataPageId(RelationInfo relInfo) throws FileNotFoundException, EmptyStackException, IOException
	{ 
		BufferManager bm = BufferManager.getInstance();
		PageId pageIdHeaderPage = relInfo.getHeaderPageId();
		ByteBuffer bufHp = bm.getPage(pageIdHeaderPage);
		PageId pageId = LirePageIdToBuff(bufHp, true);

		if(pageId.getFileIdx() == -1){
			return this.addDataPage(relInfo);
		}

		
		bm.FreePage(pageIdHeaderPage, 0);

		return pageId;
	}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void EcrirePageIdToBuff(PageId pageId, ByteBuffer buf, boolean first)
	{
		String tmp = pageId.getFileIdx() + "" + pageId.getPageIdx();

		int pageIdInt = Integer.valueOf(tmp);

		if(first)
			buf.putInt(0, pageIdInt);
		else
			buf.putInt(8, pageIdInt);
    }
    
    
    public PageId LirePageIdToBuff(ByteBuffer buf, boolean first)
   	{
   		int pageIdint = first ? buf.getInt(0) : buf.getInt(3);

   		int fileIdx = pageIdint / 10;
   		int pageIdx = pageIdint % 10;
   		PageId pageId = new PageId(fileIdx, pageIdx); 

   		return pageId;
       }
}
