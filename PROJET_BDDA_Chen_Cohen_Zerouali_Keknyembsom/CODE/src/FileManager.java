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


    public static final FileManager getInstance()
	{
		if (instance == null)
			instance = new FileManager();
		return instance;
	}

  public PageId createNewHeaderPage() throws IOException {
    DiskManager dm = DiskManager.getLeDiskManager();
    BufferManager  bm = BufferManager.getInstance();
    bm.init();

    PageId headerPageId = dm.allocPage();
    System.out.println("HeaderPage alloué : " + headerPageId.toString());

    ByteBuffer buf = bm.getPage(headerPageId);
    buf.putInt(0, 0);

    bm.FreePage(headerPageId, 1);
    return headerPageId;
  }
    public PageId getFreeDataPageId(RelationInfo relInfo, int sizeRecord) throws FileNotFoundException, IOException, EmptyStackException
	{

    }
    public PageId addDataPage(RelationInfo relInfo) throws IOException {


      }

    public ArrayList<PageId> getAllDataPages(RelationInfo relInfo) throws IOException {

	  }

  public RecordId writeRecordToDataPage(Record record, PageId pageId) throws IOException {

	  }



  public ArrayList<Record> getRecordsInDataPage(RelationInfo relInfo, PageId pageId) throws IOException {

	  }
  public RecordId insertRecordIntoRelation(Record record) throws IOException {

	    return writeRecordToDataPage(record,getFreeDataPageId(record.getRelInfo(), record.recordSizeFromValues()));
	  }
  public ArrayList<Record> getAllRecords(RelationInfo relInfo) throws IOException {



	  }




}
