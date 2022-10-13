import java.io.Serializable;

public class ColInfo implements Serializable
{
    private String nom;
    private Type type;

    public ColInfo(Type test, String nom)
    {
        this.nom = nom.toLowerCase();
        this.type = test;
    }

    public String getNom(){
        return nom;
    }
    public String getType(){
        return type.getNomTYpe();
    }
    public int getTaille()
    {
        return type.getTaille();
    }
}
