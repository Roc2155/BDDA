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
		 int res=0;
		 ArrayList<ColInfo> colinf = relInfo.getListe();
		 for(int i=0;i<colinf.size();i++) {
			 String type=colinf.get(i).getType();
			 switch(type) {
			 	case("INTEGER"):
			 	case("REAL"):
			 		res+=8;
			 		break;
			 	default:
			 		res+=Integer.parseInt(type.substring(8,type.length()-1))*2;
			 		res+=4;
			 }
		 }
		 res+=4;
		 return res;
	 }

	
	//Méthode qui prend en paramètre un buffer (alloué par l’appelant) et un entier pos correspondant à une position dans le buffer. 
	//Elle écrit les valeurs du Record dans le buffer en partant de la position pos
	 public void writeToBuffer(ByteBuffer buff,int pos) {
		 buff.position(pos);
		 List<ColInfo> colinf = relInfo.getListe();
		 int adresVal=pos+(relInfo.getNb()+1)*4;
		 int tmpPos=pos;
		 for (int i=0; i< values.size();i++) {
			 String type =colinf.get(i).getType();
			 switch(colinf.get(i).getType()) {
			 	case("INTEGER"):
			 		buff.putInt(tmpPos,adresVal);

			 		tmpPos+=4;
			 		int valeur=Integer.parseInt(values.get(i));
			 		buff.putInt(adresVal,valeur);
			 		adresVal+=4;
			 		break;

			 	case("REAL"):
			 		buff.putInt(tmpPos,adresVal);

			 		tmpPos+=4;
		 			float val=Float.parseFloat(values.get(i));
		 			buff.putFloat(adresVal, val);
		 			adresVal+=4;
		 			break;

			 	default:
			 		int taillemem= Integer.parseInt(type.substring(8,type.length()-1));
			 		buff.putInt(tmpPos,adresVal);
			 		tmpPos+=4;
			 		String mot =values.get(i);
			 		int taillemot =mot.length();
			 		for(int y=0;y<taillemem;y++) {
			 			if(taillemot>y) {
			 				buff.putChar(adresVal,mot.charAt(y));
			 			}else {
			 				buff.putChar(adresVal,' ');
			 			}
			 			adresVal+=2;
			 		}

			 }
		 }
		 buff.putInt(tmpPos,adresVal);
	 }

	//Méthode qui prend en paramètre un buffer (alloué par l’appelant) et un entier pos correspondant à une position dans le buffer. 
	//Elle lit les valeurs du Record depuis le buffer à partir de pos.
	 public void readFromBuffer(ByteBuffer buff,int pos) {
		 values.clear();
		 buff.position(pos);
		 ArrayList<ColInfo> colinf = relInfo.getListe();
		 int tmpPos=pos;

		 for (int i=0; i< colinf.size();i++) {
			 String type =colinf.get(i).getType();
			 switch(type) {
			 	case("INTEGER"):
			 		Integer valeur =buff.getInt(tmpPos);
			 		values.add(valeur.toString());
			 		tmpPos+=4;
			 		break;

			 	case("REAL"):
		 			Float val=buff.getFloat(tmpPos);
		 			values.add(val.toString());
		 			tmpPos+=4;
		 			break;

			 	default:
			 		int adressdebut = buff.getInt(tmpPos);
			 		int adressfin = buff.getInt(tmpPos+4);
			 		int taille =adressfin - adressdebut;
			 		StringBuffer sb =new StringBuffer();
			 		for(int x=0;x>taille;x++) {
			 			sb.append(buff.getChar());
			 		}
			 		values.add(sb.toString());
			 		tmpPos+=4;

			 }
		 }
	 }
	
	
	 public int recordSizeFromValues(){
	        String type;
	        int tailleChaine, writtenSize=0;
	        for(int i=0 ; i<values.size() ; i++){
	            type = relInfo.getListe().get(i).getType();
	            switch (type) {
	                case "INTEGER":
	                    writtenSize += 4;
	                    break;
	                case "REAL":
	                    writtenSize += 4;
	                    break;
	                default:
	                    tailleChaine = values.get(i).length();
	                    writtenSize +=  tailleChaine*2;
	                    break;

	            }
	        }
	        return writtenSize+(1+relInfo.getNb()) * 4;
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
