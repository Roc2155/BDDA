import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;
public class DiskManager {
	private static int CurrentCountAllocPages=0;
	static ArrayList<PageId> ListeDePagesNonAlloue = new ArrayList<PageId>(); 
	static ArrayList<PageId> ListeDePagesAlloue = new ArrayList<PageId>(); 
	public PageId AllocPage() {
		if(ListeDePagesNonAlloue.size()>0) {
			int NombreAleatoire = new Random().nextInt(ListeDePagesNonAlloue.size()+1);
			PageId pageid = ListeDePagesNonAlloue.get(NombreAleatoire);//On prends une page aléatoire parmis les pages disponibles
			ListeDePagesNonAlloue.remove(NombreAleatoire);
			CurrentCountAllocPages = CurrentCountAllocPages + 1;
			ListeDePagesAlloue.add(pageid);
			return pageid;			
		}
		else {//Si aucune pages disponibles, création d'un nouveau fichier
			boolean cherchenomdispo = false;
			int i = 0;
			ListeDePagesNonAlloue.clear();
			while(!cherchenomdispo) {
				
				String path = DBParams.DBPath+"F"+i+".bdda";
				System.out.println(path);
				File file = new File(path);
				
				
				if(!file.exists()) {//si il nexiste pas on cree un file, on met la premiere page en actif et les autres en non actives
					try {
						
						
						boolean isfilecreated = file.createNewFile();
						
						PageId pageid = new PageId(i,0);
						ListeDePagesAlloue.add(pageid);
						for(int j = 0; j<DBParams.maxPagesPerFile-1;j++) {
							PageId pageid1 = new PageId(i,j+1);
							ListeDePagesNonAlloue.add(pageid1);
						}
						return pageid;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					
					i=i+1;
				}
				
		}
			}
		return null;//Si on sort de la boucle avec une erreur de creation
		}	
	public void ReadPage (PageId pageId, ByteBuffer buff) {
		String fichier = Integer.toString(pageId.getFileIdx());//Transformation du file name en String
		try {
			RandomAccessFile randomaccessfile = new RandomAccessFile(fichier, "r");
			randomaccessfile.seek(pageId.getPageIdx()*DBParams.pageSize);
			byte[] tableaudebyte = new byte[DBParams.pageSize];
			randomaccessfile.read(tableaudebyte);
			buff = ByteBuffer.wrap(tableaudebyte);
			buff.rewind();
			randomaccessfile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void WritePage (PageId pageId, ByteBuffer buff) {
		String fichier = Integer.toString(pageId.getFileIdx());//Transformation du file name en String
		try {
			RandomAccessFile randomaccessfile = new RandomAccessFile(fichier, "w");
			randomaccessfile.seek(pageId.getPageIdx());
			randomaccessfile.write(buff.array());
			
			
			
			randomaccessfile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void DeallocPage (PageId pageId ) {	
		
		CurrentCountAllocPages = CurrentCountAllocPages - 1;
		ListeDePagesAlloue.remove(pageId);
		ListeDePagesNonAlloue.add(pageId);
	}
	public int GetCurrentCountAllocPages() {
		return CurrentCountAllocPages;
	}

}