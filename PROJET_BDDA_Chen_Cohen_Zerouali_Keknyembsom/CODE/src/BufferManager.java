import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BufferManager {
	private static BufferManager instance;
	private int time;
	private Frame tmp = new Frame();
	private Frame[] listeDesFrames;

	public static BufferManager getInstance() {
		if(instance == null) {
			instance = new BufferManager();
		}
		return instance;
	}

	ArrayList<Frame> lru = new ArrayList<Frame>();

	public void init () {
		listeDesFrames = new Frame[DBParams.frameCount];
		System.out.println(listeDesFrames.length);
		time =0;
		for(int i=0;i<DBParams.frameCount;i++) {
			listeDesFrames[i] = new Frame();
		}
	}

	private BufferManager() {

	}

	public void finish() { //pas util pour l'instant

	 }

	 public ByteBuffer getPage(PageId pageId) throws IOException {
		 int cases = listeFrames.length;
		 ByteBuffer buffFrame = null;
		 DiskManager dm = DiskManager.getLeDiskManager();
		 Frame frameTmp;
		 int k = 0;
		 int j=0;

		 for(int i=0; i<cases; i++) {
			 System.out.println("est vide ? " + (listeFrames[i].getPageId()==null));
			 System.out.println("******************************" + i + "*************************");
			 //Si la frame contient une page
			 if(listeFrames[i].getPageId()!=null) {
				 if(listeFrames[i].getPageId().compareTo(pageId)) {
					 //si la page est dans la liste de frame
					 System.out.println("page in frame");

					 int pin_countPage = listeFrames[i].getPin_count()+1;
					 listeFrames[i].setPin_count(pin_countPage);

					 System.out.println(listeFrames[i].getPageId().toString());

					 //On stocke le buffer de la frame de la page, qu'on va retourner
					 buffFrame = listeFrames[i].getBuff();

					 System.out.println("buff de frame : " + Arrays.toString((listeFrames[i].getBuff()).array()));
					 System.out.println("buff : " + Arrays.toString(buffFrame.array()));

					 return buffFrame;
				 }
			 }
		 }

		 for(int i=0; i<cases; i++) {
			 System.out.println("est vide ? " + (listeFrames[i].getPageId()==null));

			 //Si la page n'est pas dans la liste de frame
			 //On l'ajoute à la liste de frame
			 if(listeFrames[i].getPageId()==null) {
				 System.out.println("******************************" + i + "*************************");
				 System.out.println("page not in frame");

				 listeFrames[i].setPageId(pageId); //Ajout de la page dans la frame vide

				 System.out.println(listeFrames[i].getPageId());

				 int pin_countPage = listeFrames[i].getPin_count()+1;
				 listeFrames[i].setPin_count(pin_countPage);

				 dm.readPage(pageId, listeFrames[i].getBuff()); //Set le buffer de la frame associé le contenu de la page
				 buffFrame = listeFrames[i].getBuff(); //On attribue le buffer contenue dans la frame associé à la page pageId, qu'on va retourner

				 System.out.println("buff de frame : " + Arrays.toString((listeFrames[i].getBuff()).array()));
				 System.out.println("buff : " + Arrays.toString(buffFrame.array()));

				 return buffFrame;
			 }
		 }

		 //Etablissement de la liste LRU afin d'appliquer la politique de remplaçement en cas de index out of bound
		 for(int i=0; i<cases; i++) {
			 System.out.println("******************************" + i + "*************************");
			 //On vérifie si tous pin_count des pages contenus dans les frames sont à 0
			 if(listeFrames[i].getPin_count()==0) {
				 lru.add(listeFrames[i]);
				 System.out.println("taille lru = " + lru.size());
				 System.out.println(lru.get(i).getPageId().toString());
			 }
		 }

		 System.out.println("index out of bound ! => LRU ");
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
		 dm.WritePage(frameTmp.getPageId(), frameTmp.getBuff());
   }
  else {
	   Boolean trouve = false;
	   for(int i=0; i<cases && !trouve; i++) {
		   if(listeFrames[i] == frameTmp) {
			   listeFrames[i].setPageId(pageId);
			   listeFrames[i].setPin_count(1);
			   dm.readPage(pageId, listeFrames[i].getBuff());
			   buffFrame = listeFrames[i].getBuff();

			   trouve = true;
		   }
	   }
   }
   return buffFrame;
	}

	public void FreePage(PageId PID, int valdirty) {

	}
	public void FlushAll() throws IOException {

	}
	public Frame[] getFrame() {
		return listeDesFrames;
	}

	public void FreePage(PageId pageIdFile, boolean b) {
		if(b) {
			FreePage(pageIdFile,1);
		}
		else {
			FreePage(pageIdFile,0);
		}
		// TODO Auto-generated method stub

	}
	public void reset(){
        init();
        for(Frame frame: listeDesFrames){
            frame.setPID(new PageId(-1,0));
            frame.setBuff(ByteBuffer.allocate(DBParams.pageSize));
            frame.setTemps_free(-1);
            frame.setPin_count(0);
            frame.setDirty(false);
        }
    }





}
