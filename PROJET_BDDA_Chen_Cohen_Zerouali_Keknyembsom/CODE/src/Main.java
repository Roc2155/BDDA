public class Main {

	public static void main(String [] args) {
		DBParams.DBPath= args[0];
		DBParams.pageSize=4096;
		DBParams.maxPagesPerFile=4;
		System.out.println("test");
		
		
	}

}
