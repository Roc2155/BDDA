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
        PageId pageIdFile = DiskManager.getLeDiskManager().allocPage();
        System.out.println("header page id "+pageIdFile.toString());
        BufferManager.getInstance().init();
        ByteBuffer bufferHeaderPage = BufferManager.getInstance().getPage(pageIdFile);
        bufferHeaderPage.putInt(0, 0);
        BufferManager.getInstance().FreePage(pageIdFile, true);
        return pageIdFile;
      }
    public PageId getFreeDataPageId(RelationInfo relInfo, int sizeRecord) throws FileNotFoundException, IOException, EmptyStackException 
	{
    	PageId pageId = new PageId();
        int j = 0;        
        ByteBuffer bufferHeaderPage = BufferManager.getInstance().getPage(relInfo.getHeaderPageId());
        
      
        for (int i = 12; i < bufferHeaderPage.capacity(); i += 12) {//modulo 12
        	if (bufferHeaderPage.getInt(i) >= sizeRecord) {
        		System.out.println("im here");
        		j = i - 8;
        		pageId.setFileIdx(bufferHeaderPage.getInt(j));
              
        		j = i - 4;
        		pageId.setPageIdx(bufferHeaderPage.getInt(j));
        		
        		BufferManager.getInstance().FreePage(relInfo.getHeaderPageId(), false);
        		return pageId;//On a trouvé la page 
            	}
         }
         BufferManager.getInstance().FreePage(relInfo.getHeaderPageId(), false);
          
         return FileManager.getInstance().addDataPage(relInfo);        
       
    }
    public PageId addDataPage(RelationInfo relInfo) throws IOException {
        
        PageId pageId = DiskManager.getLeDiskManager().allocPage();

        ByteBuffer bufferDataPage = BufferManager.getInstance().getPage(pageId);  
        bufferDataPage.putInt((DBParams.pageSize) - 8, 0);
        bufferDataPage.putInt((DBParams.pageSize) - 4, 0);

        BufferManager.getInstance().FreePage(pageId, true);

      

   
        ByteBuffer bufferHeaderPage = BufferManager.getInstance().getPage(relInfo.getHeaderPageId());
        int dataPage = bufferHeaderPage.getInt(0);
        int espaceDispo = (dataPage * 12 + 4);


        bufferHeaderPage.putInt(espaceDispo, pageId.getFileIdx());
        bufferHeaderPage.putInt(espaceDispo + 4, pageId.getPageIdx());
        bufferHeaderPage.putInt(espaceDispo+8,DBParams.pageSize - 8);

       
        dataPage++;
        bufferHeaderPage.putInt(0, dataPage);




        BufferManager.getInstance().FreePage(relInfo.getHeaderPageId(), true);

        return pageId;
      }
    
    public ArrayList<PageId> getAllDataPages(RelationInfo relInfo) throws IOException {
    	System.out.println(relInfo.getHeaderPageId());
	    ByteBuffer bufferHeaderPage = BufferManager.getInstance().getPage(relInfo.getHeaderPageId());	   
	    int nbDataPage = bufferHeaderPage.getInt(0), positionDataPage = 4;
	    System.out.println(nbDataPage);
	    ArrayList<PageId> listeDataPage = new ArrayList<>();
	    PageId pidTemp = new PageId();

	    
	    for (int i = 0; i < nbDataPage; i++) {
	      pidTemp = new PageId(bufferHeaderPage.getInt(positionDataPage),bufferHeaderPage.getInt(positionDataPage+4));
	      listeDataPage.add(pidTemp);
	      positionDataPage += 12;
	    }
	    BufferManager.getInstance().FreePage(relInfo.getHeaderPageId(), true);
	    return listeDataPage;
	  }

  public RecordId writeRecordToDataPage(Record record, PageId pageId) throws IOException {
	  System.out.println("pageId = "+pageId.toString());
	    ByteBuffer bufferDataPage = BufferManager.getInstance().getPage(pageId);
	    
	
	    int positionDispo = bufferDataPage.getInt(DBParams.pageSize - 4);
	    record.writeToBuffer(bufferDataPage, positionDispo);
	   


	    int nbSlot = bufferDataPage.getInt(DBParams.pageSize - 8);
	    int positionInsertionSlot = (DBParams.pageSize - 8) - ((nbSlot+1) * 8);
	    bufferDataPage.putInt(positionInsertionSlot, positionDispo);
	    bufferDataPage.putInt(positionInsertionSlot + 4, record.getWrittenSize());

	    
	    bufferDataPage.putInt(DBParams.pageSize - 8, nbSlot + 1);



	    
	    positionDispo = positionDispo + record.getWrittenSize();
	    bufferDataPage.putInt(DBParams.pageSize - 4, positionDispo);



	    BufferManager.getInstance().FreePage(pageId, true);

	    
	    ByteBuffer bufferHeaderPage = BufferManager.getInstance().getPage(record.getRelInfo().getHeaderPageId());
	    int posNbOctetsLibreDP = bufferHeaderPage.getInt(0) * 12;
	    bufferHeaderPage.getInt(posNbOctetsLibreDP);
	    int newOctetsLibre = bufferHeaderPage.getInt(posNbOctetsLibreDP)-record.getWrittenSize()-8;
	    bufferHeaderPage.putInt(posNbOctetsLibreDP, newOctetsLibre);
	    
	    BufferManager.getInstance().FreePage(record.getRelInfo().getHeaderPageId(),true);
	    return new RecordId(pageId, nbSlot);
	  }

  

  public ArrayList<Record> getRecordsInDataPage(RelationInfo relInfo, PageId pageId) throws IOException {
	    ByteBuffer bufferDataPage = BufferManager.getInstance().getPage(pageId);
	    int posDebutRecord = 0;

	    
	    ArrayList <Record> listeRecords = new ArrayList<Record>();
	    

	    
	    int nbSlot = bufferDataPage.getInt(DBParams.pageSize - 8);
	    int posDebutSlot = DBParams.pageSize - 8 - nbSlot * 8;

	    
	    
	    int idxPosDebutRecord=posDebutSlot;
	    for (int i =0; i < nbSlot; i++) {
	      Record recordTemp=new Record(relInfo);
	      posDebutRecord = bufferDataPage.getInt(idxPosDebutRecord);
	      recordTemp.readFromBuffer(bufferDataPage, posDebutRecord);
	      listeRecords.add(recordTemp);
	      idxPosDebutRecord+=8;


	    }
	    BufferManager.getInstance().FreePage(pageId, true);
	    return listeRecords;
	  }
  public RecordId insertRecordIntoRelation(Record record) throws IOException {

	    return writeRecordToDataPage(record,getFreeDataPageId(record.getRelInfo(), record.recordSizeFromValues()));
	  }
  public ArrayList<Record> getAllRecords(RelationInfo relInfo) throws IOException {
	    
	  
	    ArrayList<Record> listeRecords = new ArrayList<>();
	    ArrayList<PageId> dataPage = getAllDataPages(relInfo);
	    ArrayList<Record> listeTemp = new ArrayList<>();

	    
	    for (int i = 0; i < dataPage.size(); i++) {
	      listeTemp = getRecordsInDataPage(relInfo, dataPage.get(i));
	      for (int j = 0; j < listeTemp.size(); j++) {
	        listeRecords.add(listeTemp.get(j));
	      }
	    }
	    return listeRecords;
	  }
  


  
}
