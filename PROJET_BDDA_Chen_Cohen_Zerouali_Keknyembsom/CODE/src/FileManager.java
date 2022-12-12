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
      BufferManager bm = BufferManager.getInstance();
      bm.init();

      PageId headerPageId = dm.allocPage();
      System.out.println("HeaderPage alloué : " + headerPageId.toString());

      ByteBuffer buf = bm.getPage(headerPageId);
      buf.putInt(0, 0);

      bm.FreePage(headerPageId, 1);
      return headerPageId;
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

    		addDataPageToHeaderPage(relInfo, pageId); //Ajouter la nouvelle DataPage au HeaderPage de la relationInfo
	      return pageId; //DataPage initialisée
    }

    private void addDataPageToHeaderPage(RelationInfo relInfo, PageId pageId) {
		   BufferManager bm = BufferManager.getInstance();
		   bm.init();

		   try {
         ByteBuffer buffHeaderPage = bm.getPage(relInfo.getHeaderPageId());
	       int nbDataPageIndexee = buffHeaderPage.getInt(0); //On récupère les 4 octets en début de la page
	       int enTete = 4; //4 octets qu'on réserve pour indiquer le nombre de pages indéxées
			   int idDataPage = 8; //8 octets car 2 entiers : fileIdx et pageIdx
         int espaceDispoDataPage = 4;
			   int nextSlotDispo = enTete +(nbDataPageIndexee*(idDataPage + espaceDispoDataPage));
			   int tailleDataPage = DBParams.pageSize - 8; //DataPage encore vide => Ne contient pas encore de record

			   buffHeaderPage.putInt(nextSlotDispo, pageId.getFileIdx()); //Ecrit 4 octets fileIdx à l'indice nextPageId dans le HeaderPage
			   buffHeaderPage.putInt(nextSlotDispo + 4, pageId.getPageIdx()); //Ecrit 4 octets pageIdx à l'indice nextPageId + 4 dans le HeaderPage
			   buffHeaderPage.putInt(nextSlotDispo + 8, tailleDataPage); //Ecrit 4 octets la taille de la DataPage à l'indice après les 8 octets écrits dans le HeaderPage

			   nbDataPageIndexee++;
			   buffHeaderPage.putInt(0, nbDataPageIndexee); //Màj du nombre de DataPage dans le HeaderPage

			   bm.FreePage(relInfo.getHeaderPageId(), 1);
		   }
		   catch(IOException e) {
			    e.printStackTrace();
		   }
    }

    public PageId getFreeDataPageId(RelationInfo relInfo, int sizeRecord) throws FileNotFoundException, EmptyStackException, IOException {
    	BufferManager bm = BufferManager.getInstance();
      bm.init();
    	PageId dataPageId = null;
    	PageId headerPageId = relInfo.getHeaderPageId();
    	ByteBuffer bufferHeaderPage = bm.getPage(headerPageId); //contient toutes les informations sur les data page
    	Boolean dispo = false;
    	int indEspaceDispoDataPage = 12; //à partir de 0 octet, tous les 12e on a les information sur l'espace disponible des data page
    	int tailleHeaderPage = bufferHeaderPage.capacity();
    	for(; indEspaceDispoDataPage<tailleHeaderPage && !dispo; indEspaceDispoDataPage+=12) {
    		int espaceDispoDataPage = bufferHeaderPage.getInt(indEspaceDispoDataPage);
    		//Si il reste encore de l'espace disponible
    		if(espaceDispoDataPage>=sizeRecord) {
    			//on récupère la page
    			int fileIdx = espaceDispoDataPage-8;
    			int pageIdx = fileIdx+4;
    			dataPageId.setFileIdx(bufferHeaderPage.getInt(fileIdx));
    			dataPageId.setPageIdx(bufferHeaderPage.getInt(pageIdx));
    			dispo = true;
    			bm.FreePage(headerPageId, 0);
    		}
    	}
    	bm.FreePage(headerPageId, 0);
		  return dataPageId;
	 }

   public RecordId writeRecordToDataPage(Record record, PageId dataPageId) {
    	BufferManager.getInstance().init();
		  RecordId recordId = null;
	    try {
  		  ByteBuffer buffDataPage = BufferManager.getInstance().getPage(dataPageId);
  	    int slotDirectory = DBParams.pageSize-4; //Donne la position de là où commence l'espace libre sur la data page
  			int posEspaceDispo = buffDataPage.getInt(slotDirectory); //Les derniers 4 octets du slot directory qui se trouve en fin de page donne la position de l'espace disponible
  			int posNSlots = slotDirectory -4; //Donne la position du nombre de slots dans le directory (4 octets à partir des 8 derniers octets de la page)

  			record.writeToBuffer(buffDataPage, posEspaceDispo);

  			//Ajout d'un record dans la data page => incrémenter le nombre de slots dans le directory
  			int nSlots = buffDataPage.getInt(posNSlots);
  			nSlots+=1;
  			//Indexer la position du record dans la data page
  			int nbRecordSlots = (nSlots)*8; //next slot pour indexe un record dont 4 octets donnant la position de début dand la page du ième record et 4 autres pour la taille du ième record
  			int nextRecordSlot = (posNSlots) - nbRecordSlots;
  			buffDataPage.putInt(nextRecordSlot, posEspaceDispo); //On insère dans le slot à l'indice indexeRecordSlot, l'indice où se trouvera le record (à posEspaceDispo)
  			buffDataPage.putInt(nextRecordSlot+4, record.getWrittenSize()); //On insère la taille du record dans le slot suivant de 4 octets
  			//Màj du directory
  			buffDataPage.putInt(posNSlots, nSlots); //Mets à jour le nombre de slot dans la data page
  			posEspaceDispo = posEspaceDispo+record.getWrittenSize();
  			buffDataPage.putInt(slotDirectory, posEspaceDispo); //Mets à jour la position du début de l'espace libre après avoir écrit le record sur la data page

  			BufferManager.getInstance().FreePage(dataPageId, 1);

  			//Màj de l'espace dispo de la data page dans la headerPage de la relation
  			PageId headerPage = record.getRelInfo().getHeaderPageId();
  			ByteBuffer buffHeaderPage = BufferManager.getInstance().getPage(headerPage);
  			int indEspaceDispoDataPage = buffHeaderPage.getInt(0)*12;
  			int tailleNewRecord = record.getWrittenSize()-8; //8 octets pour les slots indiquants la position du record dans la data page ainsi que sa taille
  			int espaceDispoDataPage = buffHeaderPage.getInt(indEspaceDispoDataPage)-tailleNewRecord;
  			buffHeaderPage.putInt(buffHeaderPage.getInt(indEspaceDispoDataPage), espaceDispoDataPage); //Màj de l'espace dispo de la data page dans le header page

  			BufferManager.getInstance().FreePage(dataPageId, 1);
  			recordId = new RecordId(dataPageId, nSlots);
  		}
      catch (IOException e) {
  			e.printStackTrace();
  		}
  		return recordId;
    }

    public ArrayList<PageId> getAllDataPages(RelationInfo relInfo) throws IOException {
      return null;
	  }



    public ArrayList<Record> getRecordsInDataPage(RelationInfo relInfo, PageId pageId) throws IOException {
      return null;
	  }

    public RecordId insertRecordIntoRelation(Record record) throws IOException {
	    return writeRecordToDataPage(record,getFreeDataPageId(record.getRelInfo(), record.recordSizeFromValues()));
	  }

    public ArrayList<Record> getAllRecords(RelationInfo relInfo) throws IOException {
      return null;
	  }
}
