import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String [] args) throws IOException, ClassNotFoundException {
        DBParams.DBPath = args[0];
        DBParams.pageSize = 4096;
        DBParams.maxPagesPerFile = 4;
        DBParams.frameCount = 2;


        DBManager.getLeDBManager().Init();
    		Scanner sc=new Scanner(System.in);
    		String chaine;
    		while (true){
          menu();
    			System.out.println("Veuillez choisir une commande : \n");
    			chaine=sc.nextLine();
    			if (chaine.equals("EXIT")){
    				DBManager.getLeDBManager().Finish();
    				break;
    			} else
    				DBManager.getLeDBManager().ProcessCommand(chaine);
    		}
    		sc.close();
    }

    private static void menu () {
    	System.out.println("DROPDB de la forme suivante:  DROPDB \n");
    	System.out.println("CREATE TABLE de la forme suivance : CREATE TABLE NomRelation (NomCol_1:TypeCol_1,NomCol_2:TypeCol_2, ...\n"
    			+ "NomCol_NbCol:TypeCol_NbCol) \n");
    	System.out.println("INSERT de la forme suivante : INSERT INTO nomRelation VALUES (val1,val2,...,valn) \n");
    	System.out.println("SELECT de la forme suivante :  SELECT * FROM nomRelation WHERE nomColonne1OPvaleur1\r\n"
    			+ " AND nomColonne2OPvaleur2\r\n"
    			+ " ...\r\n"
    			+ " AND nomColonnekOPvaleurk \n");
    	System.out.println("EXIT \n");
    }

}
