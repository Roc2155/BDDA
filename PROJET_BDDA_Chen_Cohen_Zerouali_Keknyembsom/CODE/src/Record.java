import java.nio.ByteBuffer;


public class Record {
	
	private RelationInfo relInfo;
	private String[] values;
	
	
	public Record(RelationInfo rel) {
		this.setRelInfo(rel);
	}
	
	
	public void writeToBuffer(ByteBuffer buff, int position) {
		buff.position(position);
		for(int i=0;i<=relInfo.getNbr_col();i++) {
			String values_type = relInfo.getCol()[i].getCol_type();
			String[] tab_check = values_type.split("string");

			if(values_type == "int") {
				buff.putInt(Integer.parseInt(values[i]));
			}
			
			if(values_type == "float") {
				buff.putFloat(Float.parseFloat(values[i]));
			}
			
			if(tab_check[0] == "string") {
				for(int j=0;j<= Integer.parseInt(tab_check[1]);j++ ) {
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
		for(int i=0;i<=relInfo.getNbr_col();i++) {
			String values_type = relInfo.getCol()[i].getCol_type();
			String[] tab_check = values_type.split("string");

			if(values_type == "int") {
				values[i] = String.valueOf(buff.getInt());
				
			}
			
			if(values_type == "float") {
				values[i] = String.valueOf(buff.getFloat());
				
				
			}
			
			if(tab_check[0] == "string") {
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
}