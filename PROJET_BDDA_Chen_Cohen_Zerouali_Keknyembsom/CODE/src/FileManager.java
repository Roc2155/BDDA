import java.io.IOException;
import java.nio.ByteBuffer;

public class FileManager
{
    private FileManager fileManager;

    private FileManager(){
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public PageId createNewHeaderPage() throws IOException {
        PageId pageId = DiskManager.getLeDiskManager().allocPage();
        BufferManager.getInstance().getPage(pageId);
        ByteBuffer byteBuffer = ByteBuffer.wrap("0000".getBytes());
        DiskManager.getLeDiskManager().WritePage(pageId,byteBuffer);
        return null;
    }
}
