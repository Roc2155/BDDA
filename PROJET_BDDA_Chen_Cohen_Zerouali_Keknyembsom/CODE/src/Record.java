import java.nio.ByteBuffer;


public class Record {

	private RelationInfo relInfo;
	private String[] values;


	public Record(RelationInfo rel) {
		this.setRelInfo(rel);
	}


	public void writeToBuffer(ByteBuffer buff, int position) {
		buff.position(position);
		for(int i=0;i<=relInfo.getNbrCol();i++) {
			String values_type = relInfo.getList().get(i).getType();
			String[] tab_check = values_type.split("VARCHAR");

			if(values_type == "INTEGER") {
				buff.putInt(Integer.parseInt(values[i]));
			}

			if(values_type == "REAL") {
				buff.putFloat(Float.parseFloat(values[i]));
			}

			if(tab_check[0] == "VARCHAR") {
				for(int j=0;j<= Integer.parseInt(tab_check[1]);j++ ) {//On convertit en int la parenthese de varchar pour savoir le nombre de repet
					String[] sp = values[i].split("");
					for(int z=0;z<=sp.length;z++) {
						buff.putChar(sp[z].charAt(0));
					}

				}
			}

		}

	}


	public void readFromBuffer(ByteBuffer buff,int position) {
		buff.position(position);
		for(int i=0;i<=relInfo.getNbrCol();i++) {
			String values_type = relInfo.getList().get(i).getType();
			String[] tab_check = values_type.split("VARCHAR");

			if(values_type == "INTEGER") {
				values[i] = String.valueOf(buff.getInt());

			}

			if(values_type == "REAL") {
				values[i] = String.valueOf(buff.getFloat());


			}

			if(tab_check[0] == "VARCHAR") {
				StringBuffer sb = new StringBuffer();
				for(int j=0;j<= Integer.parseInt(tab_check[1]);j++ ) {
					buff.getChar();
					sb.append(buff.getChar());
				}
				values[i] = sb.toString();
			}
		}

	}

	public RelationInfo getRelInfo() {
		return relInfo;
	}


	public void setRelInfo(RelationInfo relInfo) {
		this.relInfo = relInfo;
	}


	public String[] getValues() {
		return values;
	}


	public void setValues(String[] values) {
		this.values = values;
	}

	public String toString() {
		StringBuilder chaine = new StringBuilder("[");
		for(int i=0; i<values.length; i++) {
			chaine.append(values[i]+" ");
		}
		chaine.replace(chaine.length()-1, chaine.length(), "]");
		return chaine.toString();
	}
}
