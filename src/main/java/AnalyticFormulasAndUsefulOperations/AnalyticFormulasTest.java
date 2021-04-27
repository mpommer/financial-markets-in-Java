package AnalyticFormulasAndUsefulOperations;



public class AnalyticFormulasTest {

	public static void main(String[] args) {
		char a = ' ';
		char z = '.';
	
		System.out.println("a :" + (int) a );
		System.out.println("z :" + (int) z );
		int shift = 27;
		int t = shift % 26;
		System.out.println(t);

		/*
		double[][] matrix = {{1,2,4},{4,3,8},{6,3,6}};
		double[][] matrix2 = {{1,2},{4,3}};

		double[] testVector = {1,2,3,4,1,2,3,4,5,6,7};
		double minBin = 1;
		double maxBin = 7;
		int numberbBins = 6;
				
		UsefullOperationsVectorsMatrixes test = new UsefullOperationsVectorsMatrixes();
		
		double[][] m = test.matrixInverse(matrix2);
		double[][] m1 = test.matrixInverse(matrix);
		for(int i = 0;i<3;i++)
			for(int j = 0; j<3; j++) {
			System.out.println(m1[i][j]);
			}
	*/}
	
	
	public static String Cipherof(String message, int shift) {
		char[] result = message.toCharArray();
		
		for(int i = 0; i<result.length;i++) {
			result[i] = (char) (result[i]+shift);
		}

		String newMessage = new String(result);
		
		
		return newMessage;
	}

}
