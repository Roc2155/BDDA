import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Record {
	 private RelationInfo relInfo;
	 private List<String> values;

	 public Record(RelationInfo relInfo) {
		 this.relInfo=relInfo;
		 this.values=new ArrayList<String>();
	 }


	 public Record(RelationInfo relInfo, ArrayList<String> valeursRecords) {
		// TODO Auto-generated constructor stub
		 this.relInfo=relInfo;
		 this.values=valeursRecords;
	}

	//Méthode qui prend en paramètre un buffer (alloué par l’appelant) et un entier pos correspondant à une position dans le buffer.
	//Elle écrit les valeurs du Record dans le buffer en partant de la position pos
	public void writeToBuffer(ByteBuffer buffer, int position) {
		buffer.position(position); //position dans le buffer
		List<ColInfo> listColInfo = relInfo.getListe();
		int currentPos = position;
		int nVal = relInfo.getNb();
		int offsetDirectory = (nVal+1)*4; //Car chaque entier fait 4 octets => taille en octets dur offsetDirectory
		int posVal = position+offsetDirectory;
		for(int i=0; i<values.size(); i++) { //Pour chaque valeur du record
			String typeCol = listColInfo.get(i).getType();
			String valeur = listColInfo.get(i).getNom();
			System.out.println("Nom de la colonne : "+listColInfo.get(i).getNom()+" Type de la colonne : "+typeCol);
			if(typeCol.equals("REAL")) {
				//Set le offsetDirectory => indexer la valeur dans le buffer
				buffer.putInt(currentPos, posVal); //la position de la valeur dans les 4 premiers octets du offsetDirectory
				currentPos+=4;
				//Conversion de la valeur en type correcte pour l'écrire dans le buffer
				float val = Float.parseFloat(valeur);
				buffer.putFloat(posVal, val); //On écrit la valeur val à la position indiqué dans le offsetDirectory
				posVal+=4; //Car un float vaut 4 octets
			}
			else if(typeCol.equals("INTEGER")) {
				buffer.putInt(currentPos, posVal);
				currentPos+=4;

				int val = Integer.parseInt(valeur);
				buffer.putFloat(posVal, val);
				posVal+=4;
			}
			else { //Si la valeur devrait être de type VARCHAR
				int sizeType = "VARCHAR(".length();
				//Le nombre de caractère
				int nbCarac = Integer.parseInt(typeCol.substring(sizeType, typeCol.length()-1));
				buffer.putInt(currentPos, posVal);
				currentPos+=4;

				for(int j=0; j<nbCarac; j++) {
					Character carac = valeur.charAt(j);
					if(nbCarac == valeur.length()) {
						buffer.putChar(posVal, carac);
					}
					//Dans le cas où le mot est plus petit que la taille max du VARCHAR
					else if(valeur.length()<j){
					 buffer.putChar(posVal, ' ');
					}
					posVal+=2; //Car 1 caractère vaut 2 octets
				}
			}
		}
		//Position de fin de la dernière valeur
		buffer.putInt(currentPos, posVal);
	}

	//Méthode qui prend en paramètre un buffer (alloué par l’appelant) et un entier pos correspondant à une position dans le buffer.
	//Elle lit les valeurs du Record depuis le buffer à partir de pos.
	 public void readFromBuffer(ByteBuffer buff,int pos) {

	 }

 	public int getWrittenSize() {
 		 return 0;
 	 }


	 public int recordSizeFromValues(){
		 return 0;
	 }

	 public void add(String val) {
		 values.add(val);
	 }

	 public RelationInfo getRelInfo() {
	   return relInfo;
	 }

	 public List<String> getValues() {
		 return values;
	 }
}
