import java.io.Serializable;
public class Type implements Serializable
{
    //VARCHAR(), INTEGER(4), REAL(4);

    private String nomTYpe;
    private int taille;
    public Type (String nomTYpe, int taille)
    {
        this.nomTYpe = nomTYpe;
        this.taille = taille;
    }

    public String getNomTYpe(){
        return this.nomTYpe;
    }

    public int getTaille() {
        return taille;
    }
}
