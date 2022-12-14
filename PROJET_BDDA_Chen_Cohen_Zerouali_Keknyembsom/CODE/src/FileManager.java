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
    public PageId createNewHeaderPage() throws IOException
	{
    	BufferManager.getInstance().init();

		PageId headerPageId = DiskManager.getLeDiskManager().allocPage();
		System.out.println("HeaderPage alloué : " + headerPageId.toString());

		ByteBuffer buf = BufferManager.getInstance().getPage(headerPageId);

		buf.putInt(0, 0); //4 octets 0 au début de la page allouée indiquant le nombre de page indéxée dans la HeaderPage
		//Initialisation à 0 car HeaderPage vide

		BufferManager.getInstance().FreePage(headerPageId, 1); //dirty = 1 car écriture de 4 octets 0 en début de la page

		return headerPageId;
	}

    public PageId addDataPage(RelationInfo relInfo) throws IOException {
		DiskManager dm = DiskManager.getLeDiskManager();
		BufferManager bm = BufferManager.getInstance();
		//bm.init();

		PageId pageId = dm.allocPage(); //Page contenant les records

		ByteBuffer buffDataPage = bm.getPage(pageId);
		int slotDirectory = DBParams.pageSize-8;
		buffDataPage.putInt(slotDirectory+4, 0); //Ecriture des derniers 4 octets 0 à la fin de la page
		buffDataPage.putInt(slotDirectory, 0); //Ecriture des premiers 4 octets 0 à la fin de la page

		System.out.println("GET(" + pageId.toString() + ") " + bm.getPage(pageId));

		bm.FreePage(pageId, 1); //dirty = 1 car écriture de 8 octets 0 en fin de la page

		addDataPageToHeaderPage(relInfo, pageId);

		return pageId; //DataPage initialisée
	}

    //Méthodeprivé permettant d'ajouter la data page au headerPage
    private void addDataPageToHeaderPage(RelationInfo relInfo, PageId pageId) throws IOException {
		BufferManager bm = BufferManager.getInstance();
		//bm.init();
		//try {
			ByteBuffer buffHeaderPage = bm.getPage(relInfo.getHeaderPageId());
			int nbDataPageIndexee = buffHeaderPage.getInt(0); //On récupère les 4 octets en début de la page
			int enTete = 4; //4 octets qu'on réserve pour indiquer le nombre de pages indéxées
			int idDataPage = 8; //8 octets car 2 entiers : fileIdx et pageIdx
			int espaceDispoDataPage = 4;
			int nextSlotDispo = nbDataPageIndexee * (idDataPage + espaceDispoDataPage) + enTete;
			int tailleDataPage = DBParams.pageSize - 8; //DataPage encore vide => Ne contient pas encore de record

			buffHeaderPage.putInt(nextSlotDispo, pageId.getFileIdx()); //Ecrit 4 octets fileIdx à l'indice nextPageId dans le HeaderPage
			buffHeaderPage.putInt(nextSlotDispo + 4, pageId.getPageIdx()); //Ecrit 4 octets pageIdx à l'indice nextPageId + 4 dans le HeaderPage
			buffHeaderPage.putInt(nextSlotDispo+ 8, tailleDataPage); //Ecrit 4 octets la taille de la DataPage à l'indice après les 8 octets écrits dans le HeaderPage


			nbDataPageIndexee++;
			buffHeaderPage.putInt(0, nbDataPageIndexee); //Màj du nombre de DataPage dans le HeaderPage

			bm.FreePage(relInfo.getHeaderPageId(), 1);
		/*}
		catch(IOException e) {
			e.printStackTrace();
		}*/
    }

    public PageId getFreeDataPageId(RelationInfo relInfo, int sizeRecord) throws FileNotFoundException, EmptyStackException, IOException {
    	BufferManager bm = BufferManager.getInstance();
    	//bm.init();
    	PageId dataPageId = FileManager.getInstance().addDataPage(relInfo);
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
    			int pageIdx = espaceDispoDataPage-4;
    			dataPageId.setFileIdx(bufferHeaderPage.getInt(fileIdx));
    			dataPageId.setPageIdx(bufferHeaderPage.getInt(pageIdx));
    			dispo = true;
    			bm.FreePage(headerPageId, 0);
    		}
    	}
    	bm.FreePage(headerPageId, 0);

		return dataPageId;
	}

    public RecordId writeRecordToDataPage(Record record, PageId dataPageId) throws IOException {
    	//BufferManager.getInstance().init();
		RecordId recordId = null;
		//try {
			ByteBuffer buffDataPage = BufferManager.getInstance().getPage(dataPageId);
			int slotDirectory = DBParams.pageSize-4; //Donne la position de là où commence l'espace libre sur la data page
			int posEspaceDispo = buffDataPage.getInt(slotDirectory); //Les derniers 4 octets du slot directory qui se trouve en fin de page donne la position de l'espace disponible
			int posNSlots = slotDirectory-4; //Donne la position du nombre de slots dans le directory (4 octets à partir des 8 derniers octets de la page)

			record.writeToBuffer(buffDataPage, posEspaceDispo);

			//Ajout d'un record dans la data page => incrémenter le nombre de slots dans le directory
			int nSlots = buffDataPage.getInt(posNSlots);
			//Indexer la position du record dans la data page
			int nbRecordSlots = (nSlots+1)*8; //next slot pour indexe un record dont 4 octets donnant la position de début dand la page du ième record et 4 autres pour la taille du ième record
			int nextRecordSlot = (posNSlots) - nbRecordSlots;
			buffDataPage.putInt(nextRecordSlot, posEspaceDispo); //On insère dans le slot à l'indice indexeRecordSlot, l'indice où se trouvera le record (à posEspaceDispo)
			buffDataPage.putInt(nextRecordSlot+4, record.getWrittenSize()); //On insère la taille du record dans le slot suivant de 4 octets

			//Màj du directory
			buffDataPage.putInt(posNSlots, nSlots+1); //Mets à jour le nombre de slot dans la data page
			posEspaceDispo = posEspaceDispo+record.getWrittenSize();
			buffDataPage.putInt(slotDirectory, posEspaceDispo); //Mets à jour la position du début de l'espace libre après avoir écrit le record sur la data page

			BufferManager.getInstance().FreePage(dataPageId, 1);

			//Màj de l'espace dispo de la data page dans la headerPage de la relation
			PageId headerPage = record.getRelInfo().getHeaderPageId();
			ByteBuffer buffHeaderPage = BufferManager.getInstance().getPage(headerPage);
			int indEspaceDispoDataPage = buffHeaderPage.getInt(0)*12;
			int tailleNewRecord = record.getWrittenSize()-8; //8 octets pour les slots indiquants la position du record dans la data page ainsi que sa taille
			int espaceDispoDataPage = buffHeaderPage.getInt(indEspaceDispoDataPage)-tailleNewRecord;
			buffHeaderPage.putInt(indEspaceDispoDataPage, espaceDispoDataPage); //Màj de l'espace dispo de la data page dans le header page

			BufferManager.getInstance().FreePage(dataPageId, 1);
			System.out.println("nSlots: "+nSlots);
			recordId = new RecordId(dataPageId, nSlots);
		/*} catch (IOException e) {
			e.printStackTrace();
		}*/

		return recordId;
    }

    public List<Record> getRecordsInDataPage(RelationInfo relInfo, PageId dataPageId) throws IOException {
    	//BufferManager.getInstance().init();
    	 List<Record> listRecords = new ArrayList<Record>();
    	 BufferManager.getInstance().init();
    	 int posRecord = 0;
    	 //try {
			ByteBuffer buffDataPage = BufferManager.getInstance().getPage(dataPageId);
			int slotDirectory = DBParams.pageSize-4;
			int posNSlots = slotDirectory-4;
			int nSlots = buffDataPage.getInt(posNSlots); //Nombre de record indexé dans le directory
			int debRecordSlot = posNSlots-(nSlots*8); //Chaque slot de record contient 8 octets (position et taille du record)
			for(int i=0; i<nSlots; i++) {
				Record record = new Record(relInfo);
				posRecord = buffDataPage.getInt(debRecordSlot); //On récupère le record indexé dans le slot
				record.readFromBuffer(buffDataPage, posRecord);
				listRecords.add(record);
				debRecordSlot+=8; //Next slot du record suivant
			}
			BufferManager.getInstance().FreePage(dataPageId, 1);
		/*} catch (IOException e) {
			e.printStackTrace();
		}*/
    	 return listRecords;
    }

    public List<PageId> getAllDataPages(RelationInfo relInfo) throws IOException {
    	//BufferManager.getInstance().init();
    	List<PageId> listePageId = new ArrayList<PageId>();
    	PageId headerPage = relInfo.getHeaderPageId();
    	int posDataPage = 4; //Position de la première data page dans le header page
    	int infoDataPage = 12; //4 octets (fileIdx), 4 octets (pageIdx) et 4 octets (espace dispo dans la data page)
    	//try {
    		ByteBuffer buffHeaderPage = BufferManager.getInstance().getPage(headerPage);
    		int nbPagesIndexee = buffHeaderPage.getInt(0); //Nombre de data page indexees sur 4 octets
    		for(int i=0; i<nbPagesIndexee; i++) {
    			int fileIdDataPage = buffHeaderPage.getInt(posDataPage);
    			int pageIdDataPage = buffHeaderPage.getInt(posDataPage+4);
    			PageId pageId = new PageId(fileIdDataPage, pageIdDataPage);
    			listePageId.add(pageId);
    			System.out.println("listeTaille : "+listePageId.size());
    			posDataPage+=infoDataPage; //Next dataPage
    		}
    		BufferManager.getInstance().FreePage(headerPage, 1);
    	/*}
    	catch(IOException e) {
    		e.printStackTrace();
    	}*/
    	return listePageId;
    }

    public RecordId insertRecordIntoRelation(Record record) throws IOException {
    	return writeRecordToDataPage(record, getFreeDataPageId(record.getRelInfo(), record.recordSizeFromValues()));
	}

    public List<Record> getAllRecords(RelationInfo relInfo) throws IOException {
    	//BufferManager.getInstance().init();
    	List<Record> allRecords = new ArrayList<Record>();
    	List<PageId> allDataPages = getAllDataPages(relInfo);
    	List<Record> listeRecordInDataPage = new ArrayList<Record>();
    	int nbDataPage = allDataPages.size();
    	for(int i=0; i<nbDataPage; i++) {
    		listeRecordInDataPage = getRecordsInDataPage(relInfo, allDataPages.get(i)); //On récupère tous les records de chaque data page
    		for(int j=0; j<listeRecordInDataPage.size(); i++) {
    			allRecords.add(listeRecordInDataPage.get(j)); //Pour chaque record de chaque data page du header page, on ajoute le record
    		}
    	}
    	return allRecords;
    }
}
