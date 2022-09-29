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
	static List<Fichier> listF = new ArrayList<>();
    	private static String path_name = DBParams.DBPath + "/save.data";
	private static File save = new File(path_name);
	private InputStream is;
    	private ObjectInputStream ois;


	private static DiskManager LeDiskManager;
	
	private DiskManager() throws IOException {
        	if (save.exists()) {
            		is = new FileInputStream(save);
            		ois = new ObjectInputStream(is);
        		}
		LeDiskManager = new DiskManager();
    	}

	public static DiskManager getLeDiskManager() {
		return LeDiskManager;
	}

	public PageId allocPage() throws IOException, ClassNotFoundException {
        	if (save.length() != 0)
            		inList();
        	for (Fichier f : listF)
        	{
            		System.out.println(f.getFile());
        	}
        	int i1 = 0;
        	Fichier nomFDisp = fIsDisp();
        	if (listF.isEmpty()) {
         	   nomFDisp = creerF();
       		} else if (nomFDisp == null) {
            		nomFDisp = creerF();
        	}
        	for (Fichier fichier : listF)
         	   i1++;
      		System.out.println(i1);
        	assert nomFDisp != null;
        	PageId pageId = new PageId(i1-1,nomFDisp.getSize());
        	ecriture(nomFDisp);
        	outList();
        	return pageId;
        //ecriture
    	}
	public void ReadPage (PageId pageId, ByteBuffer buff) {
		String fichier = Integer.toString(pageId.getFileIdx());//Transformation du file name en String
		String path = DBParams.DBPath+"F"+fichier+".bdda";
		try {
			RandomAccessFile randomaccessfile = new RandomAccessFile(path, "r");
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
	
	private void ecriture(Fichier f) throws IOException {
        FileWriter fw = new FileWriter(f.getFile(),true);
        for (int i = 0; i < 4096; i++)
        {
            fw.write(" ");
        }
        fw.close();
        f.setSize(f.getSize()+1);
    	}
	
	private Fichier creerF() throws IOException {
        StringBuffer nomF = new StringBuffer();
        int i1 = 0;
        if (listF.isEmpty()) {
            nomF.append("F").append(i1).append(".bdda");
        }
        else
        {
            for (Fichier fichier : listF)
                i1++;
            nomF.append("F").append(i1).append(".bdda");
        }
        Fichier fichier = new Fichier(nomF.toString());
        File myFile = new File(DBParams.DBPath+"/"+nomF);

        if (myFile.createNewFile()){
        }
        listF.add(fichier);
        return fichier;
    	}
	
	private Fichier fIsDisp() {
        if (listF.isEmpty())
            return null;
        for (Fichier fichier : listF) {
            if (fichier.getSize() < DBParams.maxPagesPerFile)
                return fichier;
        }
        return null;
   	}
	
	 private void outList() throws IOException {
        OutputStream os = new FileOutputStream(save);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        for (Fichier fichier : listF)
            oos.writeObject(fichier);
    	}
	
	private void inList() {
        boolean restaL = false;
        try {
            restaL = true;
            while (restaL) {
                try {
                    Fichier nF = (Fichier) ois.readObject();
                    listF.add(nF);
                    restaL = true;
                } catch (Exception e) {
                    restaL = false;
                }
            }
            ois.close();
        } catch (Exception e) {
            restaL = false;
        }
    	}

}
