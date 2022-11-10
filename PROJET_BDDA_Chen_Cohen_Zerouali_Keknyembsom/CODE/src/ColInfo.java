import java.io.Serializable;

public class ColInfo implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6292426020758456681L;
	private String nom;
    private String type;

    public ColInfo(String nom, String type) {
        this.nom = nom;
        this.type = type;

    }

    public String getNom(){
        return nom;
    }
    public String getType(){
        return this.type;
    }
   
}
