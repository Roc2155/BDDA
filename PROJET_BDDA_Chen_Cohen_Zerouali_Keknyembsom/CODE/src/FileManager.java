import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.EmptyStackException;
import java.util.List;
import java.util.ArrayList;

public class FileManager {

    private static FileManager instance = null;
    private int slotIdcpt = 0;

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
		//EcrirePageIdToBuff(new PageId(-1, 0), buf, false);

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

  public RecordId writeRecordToDataPage(Record record, PageId pageId) throws IOException {
    BufferManager bm = BufferManager.getInstance();
    RecordId recordID = null;
    //Accès à la page
    //=> pin_count++
    try {
      ByteBuffer laPage = bm.getPage(pageId);
      String fichier = Integer.toString(pageId.getFileIdx());//Transformation du file name en String
      try {
        String n = "F" + fichier + ".bdda";
        File file = new File(DBParams.DBPath + "/" + n);
        RandomAccessFile randomaccessfile = new RandomAccessFile(file, "rw");
        randomaccessfile.seek(pageId.getPageIdx()*DBParams.pageSize);
        ByteBuffer buff;
        for(int i=0; i<record.getValues().size(); i++) {
          buff = ByteBuffer.wrap(record.getValues().get(1).getBytes());
          randomaccessfile.write(buff.array());
        }
        randomaccessfile.close();
        recordID = new RecordId(pageId, slotIdcpt);
        slotIdcpt++;
      }catch (IOException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return recordID;
  }

  public List<PageId> getAllDataPages(RelationInfo relInfo) {
	  List<PageId> listeDePageIds = new ArrayList<PageId>();
	  PageId headerPageId = relInfo.getHeaderPageId();
	  BufferManager bufferManager = BufferManager.getInstance();
	  bufferManager.init();
	  ByteBuffer buff;
	  System.out.println(headerPageId.toString());
	  try {

		  buff = bufferManager.getPage(headerPageId);
		  for(int i=0; i<buff.array().length; i++) {
			  System.out.println(buff.array()[i]);
		  }

		 //System.out.println("f");
		 //System.out.println(bufferManager.getPage(headerPageId));
	  }
	  catch(IOException e) {
		  e.printStackTrace();
	  }
	  bufferManager.FreePage(headerPageId, 0);

	  return listeDePageIds;
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
