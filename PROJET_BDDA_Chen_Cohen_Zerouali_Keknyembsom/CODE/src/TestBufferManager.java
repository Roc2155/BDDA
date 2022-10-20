import java.io.IOException;



public class TestBufferManager {
	public static void TestBuffer() {
		PageId pid = new PageId(0, 1);
		PageId pid2 = new PageId(0, 2);
		PageId pid3 = new PageId(0, 3);

		System.out.println(BufferManager.getInstance().getFrame());
		try {


			System.out.print("GET(0, 1) :  ");
			BufferManager.getInstance().getPage(pid);
			System.out.println("dirty flag de la page (0, 1) : " + BufferManager.getInstance().getFrame()[0].getDirty());
			System.out.println("pin_count de la page (0, 1) : " + BufferManager.getInstance().getFrame()[0].getPin_count());

			System.out.print("GET(0, 2) : ");
			BufferManager.getInstance().getPage(pid2);
			System.out.println("[1] : " + BufferManager.getInstance().getFrame()[1].getPID());
			System.out.println("pin_count de la page (0, 2) : " + BufferManager.getInstance().getFrame()[1].getPin_count());
			System.out.println("dirty flag de la page (0, 2) : " + BufferManager.getInstance().getFrame()[1].getDirty());

			System.out.print("GET(0, 1) : ");
			BufferManager.getInstance().getPage(pid);
			System.out.println("pin_count de la page (0, 1) : " + BufferManager.getInstance().getFrame()[0].getPin_count());
			System.out.println("dirty flag de la page (0, 1) : " + BufferManager.getInstance().getFrame()[0].getDirty());

			System.out.println("FREE((0, 2), 0)"); //Si la page (0, 2) est allouée
			BufferManager.getInstance().FreePage(pid2, 0);
			System.out.println("dirty flag de la page (0, 2) : " + BufferManager.getInstance().getFrame()[1].getDirty());
			System.out.println("pin_count de la page (0, 2) : " + BufferManager.getInstance().getFrame()[1].getPin_count());

			System.out.println("FREE((0, 1), 1)");
			BufferManager.getInstance().FreePage(pid, 1);
			System.out.println("dirty flag de la page (0, 1) : " + BufferManager.getInstance().getFrame()[0].getDirty());
			System.out.println("pin_count de la page (0, 1) : " + BufferManager.getInstance().getFrame()[0].getPin_count());

			System.out.println("GET(0, 3) : ");
			BufferManager.getInstance().getPage(new PageId(0, 3));
			System.out.println("dirty flag de la page (0, 3) : " + BufferManager.getInstance().getFrame()[2].getDirty());
			System.out.println("pin_count de la page (0, 3) : " + BufferManager.getInstance().getFrame()[2].getPin_count());

			System.out.println("GET(1, 2) : ");
			BufferManager.getInstance().getPage(new PageId(1, 2));
			System.out.println("dirty flag de la page (1, 2) : " + BufferManager.getInstance().getFrame()[3].getDirty());
			System.out.println("pin_count de la page (1, 2) : " + BufferManager.getInstance().getFrame()[3].getPin_count());

			System.out.println("GET(1, 1) : ");
			BufferManager.getInstance().getPage(new PageId(1, 1));
			System.out.println("dirty flag de la page (1, 1) : " + BufferManager.getInstance().getFrame()[4].getDirty());
			System.out.println("pin_count de la page (1, 1) : " + BufferManager.getInstance().getFrame()[4].getPin_count());

			System.out.println("GET(0, 4) : ");
			BufferManager.getInstance().getPage(new PageId(0, 4));
			System.out.println("dirty flag de la page (0, 4) : " + BufferManager.getInstance().getFrame()[4].getDirty());
			System.out.println("pin_count de la page (0, 4) : " + BufferManager.getInstance().getFrame()[4].getPin_count());

			System.out.println("[1] : " + BufferManager.getInstance().getFrame()[1].getPID());
			System.out.println(BufferManager.getInstance().getFrame().length);

			for(int i=0; i<BufferManager.getInstance().getFrame().length; i++) {
				System.out.println(BufferManager.getInstance().getFrame()[i].getPID().toString());
			}

			Frame frame1 = BufferManager.getInstance().getFrame()[0];
			if(pid.getFileIdx()!=frame1.getPID().getFileIdx()&&pid.getPageIdx()!=frame1.getPID().getPageIdx()){
				System.out.println("Erreur");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		DBParams.DBPath = args[0];
		DBParams.pageSize = 5;
		DBParams.maxPagesPerFile = 4;
		DBParams.frameCount = 5;
		BufferManager.getInstance().init();
		//TestDiskManager.main(args); //Ecriture sur (0, 1) et page (0, 2) désallouée
		TestBuffer();
	}
}
