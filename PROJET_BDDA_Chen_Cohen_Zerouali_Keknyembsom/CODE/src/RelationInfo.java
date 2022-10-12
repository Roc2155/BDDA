
public class RelationInfo {
	private String name;
	private int nbr_col;
	private ColInfo[] col;
		
	
	public RelationInfo(String name, int nbr_col, ColInfo[] col) {
		this.name = name;
		this.nbr_col = nbr_col;
		this.col = col;
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNbr_col() {
		return nbr_col;
	}
	public void setNbr_col(int nbr_col) {
		this.nbr_col = nbr_col;
	}
	public ColInfo[] getCol() {
		return col;
	}
	public void setCol(ColInfo[] col) {
		this.col = col;
	}
}