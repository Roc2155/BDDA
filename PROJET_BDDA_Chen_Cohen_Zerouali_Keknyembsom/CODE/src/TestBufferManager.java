import java.io.IOException;
import java.util.Arrays;

public class TestBufferManager {
	public static void TestBuffer() {
		PageId pageId = new PageId(0, 1);
		PageId pageId2 = new PageId(0, 2);
		PageId pageId3 = new PageId(0, 3);

		BufferManager.getInstance().init();
		System.out.println(BufferManager.getInstance().getFrame().length);

		try {
			System.out.print("GET(0, 1) :  ");
			System.out.println(Arrays.toString(BufferManager.getInstance().getPage(pageId).array()));
			System.out.println("pin_count de la page (0, 1) : " + BufferManager.getInstance().getFrame()[0].getPin_count());
			System.out.println("dirty flag de la page (0, 1) : " + BufferManager.getInstance().getFrame()[0].getDirty());

			System.out.print("GET(0, 2) : ");
			BufferManager.getInstance().getPage(pageId2);
			System.out.println("[1] : " + BufferManager.getInstance().getFrame()[1].getPageId());
			System.out.println("pin_count de la page (0, 2) : " + BufferManager.getInstance().getFrame()[1].getPin_count());
			System.out.println("dirty flag de la page (0, 2) : " + BufferManager.getInstance().getFrame()[1].getDirty());

			System.out.print("GET(0, 1) : ");
			BufferManager.getInstance().getPage(pageId);
			System.out.println("pin_count de la page (0, 1) : " + BufferManager.getInstance().getFrame()[0].getPin_count());
			System.out.println("dirty flag de la page (0, 1) : " + BufferManager.getInstance().getFrame()[0].getDirty());

			System.out.println("FREE((0, 2), 0)"); //Si la page (0, 2) est allouée
			BufferManager.getInstance().FreePage(pageId2, 0);
			System.out.println("pin_count de la page (0, 2) : " + BufferManager.getInstance().getFrame()[1].getPin_count());
			System.out.println("dirty flag de la page (0, 2) : " + BufferManager.getInstance().getFrame()[1].getDirty());

			System.out.println("FREE((0, 1), 1)");
			BufferManager.getInstance().FreePage(pageId, 1);
			System.out.println("pin_count de la page (0, 1) : " + BufferManager.getInstance().getFrame()[0].getPin_count());
			System.out.println("dirty flag de la page (0, 1) : " + BufferManager.getInstance().getFrame()[0].getDirty());

			System.out.println("GET(0, 3) : ");
			BufferManager.getInstance().getPage(new PageId(0, 3));
			System.out.println("pin_count de la page (0, 3) : " + BufferManager.getInstance().getFrame()[2].getPin_count());
			System.out.println("dirty flag de la page (0, 3) : " + BufferManager.getInstance().getFrame()[2].getDirty());

			System.out.println("GET(1, 2) : ");
			BufferManager.getInstance().getPage(new PageId(1, 2));
			System.out.println("pin_count de la page (1, 2) : " + BufferManager.getInstance().getFrame()[3].getPin_count());
			System.out.println("dirty flag de la page (1, 2) : " + BufferManager.getInstance().getFrame()[3].getDirty());

			System.out.println("GET(1, 1) : ");
			BufferManager.getInstance().getPage(new PageId(1, 1));
			System.out.println("pin_count de la page (1, 1) : " + BufferManager.getInstance().getFrame()[4].getPin_count());
			System.out.println("dirty flag de la page (1, 1) : " + BufferManager.getInstance().getFrame()[4].getDirty());

			System.out.println("GET(0, 4) : ");
			BufferManager.getInstance().getPage(new PageId(0, 4));
			System.out.println("pin_count de la page (0, 4) : " + BufferManager.getInstance().getFrame()[1].getPin_count());
			System.out.println("dirty flag de la page (0, 4) : " + BufferManager.getInstance().getFrame()[1].getDirty());

			System.out.println("GET(1, 0)");
			BufferManager.getInstance().getPage(new PageId(1, 0));
			System.out.println("pin_count de la page (1, 0) : " + BufferManager.getInstance().getFrame()[0].getPin_count());
			System.out.println("dirty flag de la page (1, 0) : " + BufferManager.getInstance().getFrame()[0].getDirty());

			System.out.println("[1] : " + BufferManager.getInstance().getFrame()[1].getPageId());
			System.out.println(BufferManager.getInstance().getFrame().length);

			for(int i=0; i<BufferManager.getInstance().getFrame().length; i++) {
				System.out.println(BufferManager.getInstance().getFrame()[i].getPageId().toString());
			}

			Frame frame1 = BufferManager.getInstance().getFrame()[0];
			if(pageId.getFileIdx()!=frame1.getPageId().getFileIdx()&&pageId.getPageIdx()!=frame1.getPageId().getPageIdx()){
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
