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
		    DiskManager dm = DiskManager.getLeDiskManager();
		    BufferManager bm = BufferManager.getInstance();
		    bm.init();

        PageId pageId = dm.allocPage(); //Page contenant les records

	      ByteBuffer buffDataPage = bm.getPage(pageId);
    		int slotDirectory = DBParams.pageSize-8;
    		buffDataPage.putInt(slotDirectory+4, 0); //Ecriture des derniers 4 octets 0 à la fin de la page
    		buffDataPage.putInt(slotDirectory, 0); //Ecriture des premiers 4 octets 0 à la fin de la page

    		System.out.println("GET(" + pageId.toString() + ") " + bm.getPage(pageId));

    		bm.FreePage(pageId, 1); //dirty = 1 car écriture de 8 octets 0 en fin de la page

    		initHeaderPage(relInfo, pageId); //Ajouter la nouvelle DataPage au HeaderPage de la relationInfo
	      return pageId; //DataPage initialisée
    }

    private void initHeaderPage(RelationInfo relInfo, PageId pageId) {
    	 DiskManager dm = DiskManager.getLeDiskManager();
		   BufferManager bm = BufferManager.getInstance();
		   bm.init();

		   try {
         ByteBuffer buffHeaderPage = bm.getPage(relInfo.getHeaderPageId());
	       int nbPageIndexee = buffHeaderPage.getInt(0); //On récupère les 4 octets en début de la page
	       int enTete = 4; //4 octets qu'on réserve pour indiquer le nombre de pages indéxées
			   int tailleIdPage = 8, nbEspaceDispoPage = 4;
			   int nextPageId = enTete + tailleIdPage + nbEspaceDispoPage;
			   int tailleDataPage = DBParams.pageSize - 8; //DataPage encore vide => Ne contient pas encore de record

			   buffHeaderPage.putInt(nextPageId, pageId.getFileIdx());
			   buffHeaderPage.putInt(nextPageId + 4, pageId.getPageIdx());
			   buffHeaderPage.putInt(nextPageId + 8, tailleDataPage);


			   nbPageIndexee++;
			   buffHeaderPage.putInt(0, nbPageIndexee);

			   bm.FreePage(relInfo.getHeaderPageId(), 1);
		   }
		   catch(IOException e) {
			    e.printStackTrace();
		   }
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
