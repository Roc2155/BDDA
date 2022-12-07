public class SelectCondition {


	private String compareValue;
    private String operande;
    private int indiceColonne;
    private static final String[] operateurs = {"=","<",">","<>","<=",">="};
    

    public SelectCondition(int iColonne,String operande, String compareValue) {
        this.indiceColonne = iColonne;
        this.compareValue = compareValue;
        this.operande=operande;
    }

    public boolean verifConditionInt(int valeurRecord){
        switch (this.operande){
            case "=":
			return Integer.valueOf(this.compareValue)==valeurRecord;
            case "<":
                return valeurRecord < Integer.valueOf(this.compareValue);
            case ">":
                return valeurRecord > Integer.valueOf(this.compareValue);
            case "<=":
                return valeurRecord <= Integer.valueOf(this.compareValue);
            case ">=":
                return valeurRecord >= Integer.valueOf(this.compareValue);
            case "<>":
                return valeurRecord != Integer.valueOf(this.compareValue);
            default:
                System.out.println("Operateur incorrect");
                return false;
        }
    }
    
    public  boolean verifConditionString(String valeurRecord){
        
        switch (this.operande){
            case "=":
			return this.compareValue.equals(valeurRecord);
            case "<>":
                return valeurRecord.compareTo(this.compareValue) != 0;
            default:
                System.out.println("Operateur incorrect");
                return false;
        }
    }

    
    public boolean verifConditionReal(Float valeurRecord){
        switch (this.operande){
            case "=":
			    return Float.compare(valeurRecord, Float.valueOf(compareValue)) == 0;
            case "<":
                return  Float.compare(valeurRecord, Float.valueOf(compareValue))<0;
            case ">":
                return Float.compare(valeurRecord, Float.valueOf(compareValue))>0;
            case "<=":
                return Float.compare(valeurRecord, Float.valueOf(compareValue))<=0;
            case ">=":
                return Float.compare(valeurRecord, Float.valueOf(compareValue))>=0;
            case "<>":
                return Float.compare(valeurRecord, Float.valueOf(compareValue))!=0;
            default:
                System.out.println("Operateur incorrect");
                return false;
        }
    }

    public String toString(){
        return "Valeur: "+compareValue+"\noperateur: "+operande+"\nindice: "+indiceColonne;
    }

    public int getIndiceColonne() {
        return indiceColonne;
    }

    public void setIndiceColonne(int indiceColonne) {
        this.indiceColonne = indiceColonne;
    }

    public static String[] getOperateur() {
        return operateurs;
    }

    public String getValeurComparaison() {
        return compareValue;
    }

    public void setValeurComparaison(String compareValue) {
        this.compareValue = compareValue;
    }

    public String getOp() {
        return operande;
    } 

    public void setOp(String operande) {
        this.operande = operande;
    }
}


    

    

