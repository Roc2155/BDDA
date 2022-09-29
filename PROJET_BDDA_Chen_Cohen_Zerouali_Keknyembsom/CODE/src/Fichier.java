import java.io.File;
import java.io.Serializable;

public class Fichier implements Serializable
{
    private int sizeRes;
    private String nomF;

    public Fichier(String nomF)
    {
        this.nomF = nomF;
        sizeRes = 0;
    }

    public String getFile()
    {
        return nomF;
    }
    public int getSize() {
        return sizeRes;
    }

    public void setSize(int size) {
        this.sizeRes = size;
    }
}
