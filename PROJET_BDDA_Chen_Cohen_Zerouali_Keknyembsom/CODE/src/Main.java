import java.io.IOException;

public class Main
{
    public static void main(String [] args) throws IOException, ClassNotFoundException {
        DBParams.DBPath = args[0];
        DBParams.pageSize = 4096;
        DBParams.maxPagesPerFile = 4;
        DBParams.frameCount = 2;

    }
}
