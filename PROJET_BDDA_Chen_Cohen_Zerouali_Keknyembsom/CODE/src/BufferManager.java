import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BufferManager {
	private static BufferManager instance;
	private int timeRef;
	private Frame tmp = new Frame();
	private Frame[] listeFrames;
	ArrayList<Frame> lru;

	public static BufferManager getInstance() {
		if(instance == null) {
			instance = new BufferManager();
		}
		return instance;
	}

	public void init () {
		listeFrames = new Frame[DBParams.frameCount];
		timeRef=0;
		for(int i=0;i<DBParams.frameCount;i++) {
			listeFrames[i] = new Frame();
		}
		lru = new ArrayList<Frame>();
	}

	private BufferManager() {

	}

	public void finish() { //pas util pour l'instant

	}

	 public ByteBuffer getPage(PageId pageId) throws IOException {
		 int nbCases = listeFrames.length;
		 ByteBuffer buffFrame = null;
		 Frame frameTmp; //Pour le lru
		 int indFrame=0;
		 int indFrameV=0;
		 Boolean ajoute=false;

		 for(int i=0; i<nbCases; i++) {
			 System.out.println("Frame :"+(listeFrames[i].getPageId()==null));
		 }

		 //Si la frame contient une page
		 while(indFrame<nbCases && (listeFrames[indFrame].getPageId()!=null)) {
			 //On vérifie si la frame contient la page demandée
			 if(listeFrames[indFrame].getPageId().compareTo(pageId)) {
				 System.out.println("page in the frame " + indFrame);
				 int pin_countPage = listeFrames[indFrame].getPin_count()+1;
				 listeFrames[indFrame].setPin_count(pin_countPage);

				 timeRef+=1; //Actualisation du "temps"
				 listeFrames[indFrame].setTemps_free(timeRef);
				 //On stocke le buffer de la frame de la page, qu'on va retourner
				 buffFrame = listeFrames[indFrame].getBuff();
			 }
			 indFrame+=1;
		 }

		 //Si la frame ne contient pas la page => page non chargée
		 while(indFrameV<nbCases && !ajoute) {
			 if(listeFrames[indFrameV].getPageId()==null && !pageId.isInFrame()) {
				 System.out.println("Frame "+indFrameV+" est vide ? "+(listeFrames[indFrameV].getPageId()==null));
				 //Si la page n'est pas dans la liste de frame
				 //On l'ajoute à la liste de frame => charger la page
				 System.out.println("page not in frame " + indFrameV);
				 listeFrames[indFrameV].setPageId(pageId); //Ajout de la page dans la frame vide
				 int pin_countPage = listeFrames[indFrameV].getPin_count()+1;
				 listeFrames[indFrameV].setPin_count(pin_countPage);

				 timeRef+=1;
				 listeFrames[indFrameV].setTemps_free(timeRef);
				 ajoute = true;
				 pageId.inFrame(ajoute);

				 DiskManager.getLeDiskManager().readPage(pageId, listeFrames[indFrameV].getBuff()); //Set le buffer de la frame associé le contenu de la page
				 buffFrame = listeFrames[indFrameV].getBuff(); //On attribue le buffer contenue dans la frame associé à la page pageId, qu'on va retourner
			 }
			 indFrameV+=1;
		 }

		 //Etablissement de la liste LRU afin d'appliquer la politique de remplaçement en cas de index out of bound
		 for(int i=0; i<nbCases; i++) {
			 System.out.println(listeFrames[i].getPageId()+": "+listeFrames[i].getTemps_free());
			 System.out.println("\ttemps "+timeRef);
			 //On vérifie si tous pin_count des pages contenus dans les frames sont à 0
			 if(listeFrames[i].getPin_count()==0) {
				 System.out.println("\tFrame "+i+": pin_count "+listeFrames[i].getPin_count());
				 lru.add(listeFrames[i]);
			 }
		 }

		 //Si index out of bound => need to appliquer la politique de remplaçement : LRU
		 int usedRecently = lru.get(0).getTemps_free();
		 frameTmp = lru.get(0);
		 for(int i=0; i<lru.size(); i++) {
 		 if(usedRecently > lru.get(i).getTemps_free()) {
			 usedRecently = lru.get(i).getTemps_free();
			 frameTmp = lru.get(i);
		 }
	 }
	 //Si la page contenu dans la frame a été modifié on sauvegarde les modifications sur disque
   if(frameTmp.getDirty() == 1) {
		 DiskManager.getLeDiskManager().WritePage(frameTmp.getPageId(), frameTmp.getBuff());
   }
  else {
	   Boolean trouve = false;
	   for(int i=0; i<nbCases && !trouve; i++) {
		   if(listeFrames[i] == frameTmp && !pageId.isInFrame()) {
			   listeFrames[i].setPageId(pageId);
			   listeFrames[i].setPin_count(1);
				 listeFrames[i].setTemps_free(timeRef);
			   DiskManager.getLeDiskManager().readPage(pageId, listeFrames[i].getBuff());
			   buffFrame = listeFrames[i].getBuff();

			   trouve = true;
		   }
	   }
   }
   return buffFrame;
	}

	public void FreePage(PageId pageId, int valdirty) {
		int nbCases = listeFrames.length;
		//on cherche la page pageId contenue dans la frame pour décrémenter la pin_count et actualiser le flag dirty
		for(int i=0; i<nbCases; i++) {
			if(listeFrames[i].getPageId()==pageId) {
				int pin_countPage = listeFrames[i].getPin_count()-1;
				listeFrames[i].setPin_count(pin_countPage);
				listeFrames[i].setDirty(valdirty);
			}
		}
	}

	public void FlushAll() throws IOException {
		int nbCases = listeFrames.length;
		DiskManager.getLeDiskManager();
		for(int i=0; i<nbCases; i++) {
			if(listeFrames[i].getDirty()==1) {
				PageId page = listeFrames[i].getPageId();
				ByteBuffer buff = listeFrames[i].getBuff();
				DiskManager.getLeDiskManager().WritePage(page, buff);
				listeFrames[i].setPin_count(0);
				listeFrames[i].setDirty(0);
				listeFrames[i].setBuff(ByteBuffer.allocate(DBParams.pageSize));
				listeFrames[i].setTemps_free(-1);
				listeFrames[i].getPageId(null);
			}
			else {
				reset();
			}
		}
	}
	public Frame[] getFrame() {
		return listeFrames;
	}

	public void reset(){
    init();
    for(Frame frame: listeFrames){
        frame.setPageId(null);
        frame.setBuff(ByteBuffer.allocate(DBParams.pageSize));
        frame.setTemps_free(-1);
        frame.setPin_count(0);
        frame.setDirty(0);
    }
  }
}
