import java.io.File;
import java.io.Serializable;

public class Fichier implements Serializable
{
    private int sizeRes;
    private String nomF;
    private int numF;

    public Fichier(String nomF)
    {
        this.nomF = nomF;
        sizeRes = 0;
        numF = 0;
    }

    public int getNumF() {
        return numF;
    }

    public void setNumF(int numF) {
        this.numF = numF;
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
