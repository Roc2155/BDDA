import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main(String [] args) throws IOException, ClassNotFoundException {
        DBParams.DBPath = args[0];
        DBParams.pageSize = 4096;
        DBParams.maxPagesPerFile = 4;
        DBParams.frameCount = 2;
        
        DBManager.getLeDBManager().Init();
		Scanner sc=new Scanner(System.in);
		String cmd;
		do{
			menu();
            System.out.println("Entrez une commande");
            cmd= sc.nextLine();
            switch (cmd){
                case "EXIT": DBManager.getLeDBManager().Finish();
                break;
                default: DBManager.getLeDBManager().processCommand(cmd);
                break;
            }
        }while (!cmd.equals("EXIT"));
        DBManager.getLeDBManager().Finish();


        sc.close();
    }
    private static void menu () {
    	System.out.println("Entrez la commande CREATE TABLE de la forme suivance : CREATE TABLE NomRelation (NomCol_1:TypeCol_1,NomCol_2:TypeCol_2, ...\n"
    			+ "NomCol_NbCol:TypeCol_NbCol)");
    	//to do
    	
    }

}
