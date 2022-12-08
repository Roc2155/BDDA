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


	public int getWrittenSize() {
		 return 0;
	 }


	//Méthode qui prend en paramètre un buffer (alloué par l’appelant) et un entier pos correspondant à une position dans le buffer.
	//Elle écrit les valeurs du Record dans le buffer en partant de la position pos
	 public void writeToBuffer(ByteBuffer buff,int pos) {

	 }

	//Méthode qui prend en paramètre un buffer (alloué par l’appelant) et un entier pos correspondant à une position dans le buffer.
	//Elle lit les valeurs du Record depuis le buffer à partir de pos.
	 public void readFromBuffer(ByteBuffer buff,int pos) {

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
