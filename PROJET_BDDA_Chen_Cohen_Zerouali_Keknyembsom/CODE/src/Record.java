import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Record {
    private RelationInfo relInfo;
    public ArrayList<String> values;
    public int sizeValeur;
    public RecordId rid;

    public ArrayList<String> getValues() {
    	return this.values;
    }
    public Record(RelationInfo relation, ArrayList<String> values){
        this.relInfo=relation;
        this.values=values;
    }
    public Record(RelationInfo relation) {
        this.relInfo = relation;
        values = new ArrayList<>();
        sizeValeur=0;
    }

    public void setRelInfo(RelationInfo relInfo) {
        this.relInfo = relInfo;
    }

    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	
    	for(int i=0; i<values.size()/relInfo.getNbrCol();i++) {
    		int tmp=0;
    		for(int j=0; j<this.relInfo.getNbrCol();j++) {
    			sb.append(values.get(tmp)+" ");
    			tmp++;
    		}
    		sb.append("\n");
    	}
    	return sb.toString();
    }
    public RelationInfo getRelInfo() {
        return relInfo;
    }


   
    public void writeToBuffer(ByteBuffer buff, int pos) {
        String type;
        int tmpInt;
        float tmpFloat;
        int  k = 0;

        for (int i = 0; i < values.size() && k <= relInfo.getNbrCol() * 4; i++, k += 4) {
            type = relInfo.getInfoColonne().get(i).getType();
            buff.position(k);
            buff.putInt((1+relInfo.getNbrCol()) * 4  + sizeValeur);
            buff.position(pos + sizeValeur);
            switch (type) {
                case "INTEGER":
                    tmpInt = Integer.parseInt(values.get(i));
                    buff.putInt(tmpInt);
                    sizeValeur += 4;
                    break;

                case "REAL":
                    tmpFloat = Float.parseFloat(values.get(i));
                    buff.putFloat(tmpFloat);
                    sizeValeur += 4;
                    break;
                default:
                    for (int j = 0;   j < values.get(i).length() ; j++) {
                        buff.putChar(values.get(i).charAt(j));
                    }
                    sizeValeur += 2 * values.get(i).length();
                    break;
            }

        }
        buff.position(k);
        buff.putInt((1+relInfo.getNbrCol()) * 4  + sizeValeur);
        sizeValeur += (relInfo.getNbrCol()+1)*4;
    }
    public int getWrittenSize(){
        return sizeValeur;
    }

    public void readFromBuffer(ByteBuffer buff, int pos) {
        String type;
        int tmpInt, tailleChaine, i, j,k;
        float tmpFloat;
        char[] tmpVarchar;
        String chaine;
        values.clear();
        
        
        
        for (k = 0; k < relInfo.getNbrCol(); k++) {
            type = relInfo.getInfoColonne().get(k).getType();
            buff.position(pos+recordSizeFromValues());
            switch (type) {
                case "INTEGER":
                    tmpInt = buff.getInt();
                    values.add(String.valueOf(tmpInt));
                    break;

                case "REAL":
                    tmpFloat = buff.getFloat();
                    values.add(String.valueOf(tmpFloat));
                    break;

                default:
                    tailleChaine = buff.getInt((k+1)*4) - buff.getInt(k*4);
                    tmpVarchar = new char[tailleChaine/2];
                    for (i = pos+recordSizeFromValues(), j = 0; i < pos+recordSizeFromValues()+ tailleChaine ; i+=2, j++) {
                        
                        tmpVarchar[j] = buff.getChar(i);
                    }
                    chaine = new String(tmpVarchar);
                    values.add(chaine);
                    System.out.println(values.get(k).length());

                    break;
            }


        }

    }


   
    public int recordSizeFromValues(){
        String type;
        int tailleChaine, writtenSize=0;
        for(int i=0 ; i<values.size() ; i++){
            type = relInfo.getInfoColonne().get(i).getType();
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
        return writtenSize;
    }
}