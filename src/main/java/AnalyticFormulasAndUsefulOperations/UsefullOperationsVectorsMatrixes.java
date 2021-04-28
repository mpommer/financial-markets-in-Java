package AnalyticFormulasAndUsefulOperations;

/**
 * Collection of useful method for array and matrices like transpose and inverse. The class also contains some useful methods for data 
 * wrangling like cbind and rbind.
 * @author Marcel Pommer
 *
 */

public class UsefullOperationsVectorsMatrixes {
	
	
	/**
	 * Returns the minimum of the vector.
	 * @param vector
	 * @return Minimum
	 */
	public static double vectorAverage(double[] vector) {
		double sum = 0;
		for(int i = 0;i<vector.length;i++) {
			sum += vector[i];
		}
		
		return sum/vector.length;
	}
	
	
	/**
	 * Returns the minimum of the vector.
	 * @param vector
	 * @return Minimum
	 */
	public static double vectorMin(double[] vector) {
		double min = Double.MAX_VALUE;
		for(int i = 0;i<vector.length;i++) {
			min = min>vector[i] ? vector[i]:min;
		}
		
		return min;
	}
	
	/**
	 * Returns the maximum of the vector.
	 * @param vector
	 * @return Maximum
	 */
	public static double vectorMax(double[] vector) {
		double max = Double.MIN_NORMAL;
		for(int i = 0;i<vector.length;i++) {
			max = max<vector[i] ? vector[i]:max;
		}
		
		return max;
	}
	
	/**
	 * Returns the minimum of the matrix.
	 * @param matrix
	 * @return Minimum
	 */
	public static double matrixMin(double[][] matrix) {
		double min = Double.MAX_VALUE;
		for(int i = 0;i<matrix.length;i++) {
			for(int j = 0;j<matrix[0].length; j++) {
			min = min>matrix[i][j] ? matrix[i][j]:min;
			}
		}
		
		return min;
	}
	
	/**
	 * Returns the maximum of the matrix.
	 * @param matrix
	 * @return Maximum
	 */
	public static double matrixMax(double[][] matrix) {
		double max = Double.MIN_NORMAL;
		for(int i = 0;i<matrix.length;i++) {
			for(int j = 0;j<matrix[0].length; j++) {
			max = max<matrix[i][j] ? matrix[i][j]:max;
			}
		}
		
		return max;
	}
	
	/**
	 * Transposes the matrix.
	 * @param matrix
	 * @return Transpose of the matrix.
	 */
	public static double[][] matrixTranspose(double[][] matrix) {
		double[][] newMatrix = new double[matrix[0].length][matrix.length];
		for(int i = 0;i<matrix[0].length;i++) {
			for(int j = 0;j<matrix.length; j++) {
			newMatrix[i][j] = matrix[j][i];
			}
		}
		
		return newMatrix;
	}
	
	/**
	 * Returns the row of the matrix.
	 * @param matrix
	 * @param rowIndex
	 * @return vector.
	 */
	public static double[] matrixGetRow(double[][] matrix, int rowIndex) {
		double[] row = new double[matrix[0].length];
		for(int i = 0;i<matrix.length;i++) {		
			row[i] = matrix[rowIndex-1][i];			
		}
		
		return row;
	}
	
	/**
	 * Returns the column of the matrix.
	 * @param matrix
	 * @param columnIndex
	 * @return vector.
	 */
	public static double[] matrixGetColumn(double[][] matrix, int columnIndex) {
		double[] column = new double[matrix.length];
		for(int i = 0;i<matrix[0].length;i++) {		
			column[i] = matrix[i][columnIndex-1];			
		}
		
		return column;
	}
	
	/**
	 * Perfors a vector multiplication, i.e. v1^T * v2
	 * @param vector1
	 * @param vector2
	 * @return double
	 */
	
	public static double vectorMultiplication(double[] vector1, double[] vector2) {
		if(vector1.length != vector2.length) {
			throw new IllegalArgumentException("Vector 1 has dimension: " + vector1.length +
					"\nVector 1 has dimension: " + vector2.length +
					 "\nNo vector multiplication possible");
		}
		double vector = 0.0;
		for(int i = 0;i<vector1.length;i++) {		
			vector += vector1[i] * vector2[i];			
		}
		
		return vector;
	}
	
	/**
	 * Perfors a vector multiplication, i.e. v1 * factor
	 * @param vector1
	 * @param factor
	 * @return double array
	 */
	
	public static double[] vectorMultiplicationWithDouble(double[] vector1, double factor) {

		double[] vector = new double[vector1.length];
		for(int i = 0;i<vector1.length;i++) {		
			vector[i] = vector1[i] * factor;			
		}
		
		return vector;
	}
	
	/**
	 * Perfors a vector/matrix multiplication, i.e. v1^T * m1
	 * @param vector1
	 * @param matrix1
	 * @return vector.
	 */
	public static double[] vectorMatrixMultiplication(double[] vector1, double[][] matrix1) {
		if(vector1.length != matrix1.length) {
			throw new IllegalArgumentException("Vector 1 has dimension: " + vector1.length +
					"\nmatrix 1 has dimension: " + matrix1.length +
					 "\nNo vector multiplication possible");
		}
		
		double[] vector = new double[matrix1[0].length];
		for(int i = 0;i<matrix1[0].length;i++) {
			double sum = 0.0;
			for(int j = 0;j<matrix1.length;i++) {
			sum += vector1[i] * matrix1[i][j];
			}
			vector[i] = sum;
		}
		
		return vector;
	}
	
	/**
	 * Perfors a matrix multiplication, i.e. m1 * m2.
	 * @param matrix1
	 * @param matrix2
	 * @return matrix.
	 */
	public static double[][] matrixMalMatrixMultiplication(double[][] matrix1, double[][] matrix2) {
		if(matrix1[0].length != matrix2.length) {
			throw new IllegalArgumentException("Matrix 1 has dimension: " + matrix1[0].length +
					"\nmatrix 1 has dimension: " + matrix2.length +
					 "\nNo vector multiplication possible");
		}
		
		double[][] matrix = new double[matrix1.length][matrix2[0].length];
		for(int i = 0;i<matrix1.length;i++) {
			for(int j = 0;j<matrix2[0].length;i++) {
				double sum = 0.0;
				for(int n = 0;n<matrix1[0].length;n++) {
					sum += matrix1[i][n] * matrix1[n][j];
				}
				matrix[i][j] = sum;
			}
			
		}
		
		return matrix;
	}
	
	/**
	 * Creates a matrix containing all information needed to create a histogram. The first row contains doubles indicating the border
	 * of the bins. The second row contains the corresponding frequencies, i.e. hist[1][0] contains the frequency of the first bin. 
	 * Since their is one border bin more than bins itself, the last entry in the second rows does NOT correspond to any frequency 
	 * and is filled with Double.NAN.
	 * 
	 * @param vector
	 * @param minBin
	 * @param maxBin
	 * @param numberOfBins
	 * @return histogram matrix.
	 */
	public static double[][] histogramm(double[] vector, double minBin, double maxBin, int numberOfBins) {
		double binSize = (maxBin - minBin)/numberOfBins;
		double[][] histogram = new double[2][numberOfBins+1];
		for(int i = 0; i<=numberOfBins;i++) {
			histogram[0][i] = minBin + i*binSize;
		}
		
		for(int i = 0;i<vector.length;i++) {
			for(int j = 0; j<numberOfBins;j++) {
				if(vector[i]>=histogram[0][j] & vector[i]<histogram[0][j+1]) {
					histogram[1][j] ++;
					break;
				}
			}
		}
		
		histogram[1][numberOfBins] = Double.NaN;
		
		return histogram;
	}
	
	/**
	 * Prints the histogramm. Each row print the bin as well as the corresponding frequencies.
	 * 
	 * @param vector
	 * @param minBin
	 * @param maxBin
	 * @param numberOfBins
	 */
	public static void printHistogramm(double[] vector, double minBin, double maxBin, int numberOfBins) {
		double[][] hist = histogramm(vector, minBin, maxBin, numberOfBins);
		for(int i = 0; i<numberOfBins;i++) {
			System.out.println("Occurances in the bin [" + hist[0][i] + "," + hist[0][i+1] + ") : " + hist[1][i]);
		}
		
	}
	
	/**
	 *  Substraction of two array.
	 * @param vector1
	 * @param vector2
	 * @return Array1 - Array 2
	 */
	public static double[] arraySub(double[] vector1, double[] vector2) {
		double[] newVector = new double[vector1.length];
		for(int i = 0; i<vector1.length;i++) {
			newVector[i] = vector1[i] - vector2[i];
		}
		return newVector;
	}
	
	/**
	 * Substracting the array from the rwo.
	 * @param matrix
	 * @param vector2
	 * @param row
	 * @return new matrix
	 */
	public static double[][] MatrixArraySubstraction(double[][] matrix, double[] vector2, int row) {
		double[][] newMatrix = matrix;
		for(int i = 0; i<matrix[0].length;i++) {
			newMatrix[row][i] = newMatrix[row][i] - vector2[i];
		}
		return newMatrix;
	}
	
	/**
	 * Division of the row by the dividend.
	 * @param matrix
	 * @param division
	 * @param row
	 * @return matrix
	 */
	public static double[][] MatrixRowDivision(double[][] matrix, double division, int row) {
		double[][] newMatrix = matrix;
		for(int i = 0; i<matrix[0].length;i++) {
			newMatrix[row][i] = newMatrix[row][i] / division;
		}
		return newMatrix;
	}
	
	/**
	 * Addition of two arrays.
	 * @param vector1
	 * @param vector2
	 * @return vector.
	 */
	public static double[] arrayADD(double[] vector1, double[] vector2) {
		double[] newVector = new double[vector1.length];
		for(int i = 0; i<vector1.length;i++) {
			newVector[i] = vector1[i] + vector2[i];
		}
		return newVector;
	}
	
	/**
	 * Matrix (dim x dim) initializations with 1 on the diagonals and otherweise 0.
	 * @param dim
	 * @return matrix
	 */
	public static double[][] matrixInitializeEye(int dim) {
		double[][] matrix = new double[dim][dim];
		for(int i =0;i<matrix.length;i++) {
			for(int j= 0;j<matrix.length;j++) {
				matrix[i][j] = i==j ? 1:0;
			}
		}
		
		return matrix;
	}
	
	/**
	 * Returns the inverse of the matrix.
	 * @param matrix
	 * @return the inverse matrix
	 */
	public static double[][] matrixInverse(double[][] matrix){
		if(matrix[0].length != matrix.length) {
			throw new IllegalArgumentException("Matrix 1 has dimensions and has thus no inverse: ");
		}
		double[][] inverse = matrixInitializeEye(matrix.length);
		

		for(int i = 0; i< matrix.length;i++) {
			for(int j = i+1; j < matrix.length; j++) {
				double dividend = matrix[j][i]/matrix[i][i];
				double[] subArrayM1 = new double[matrix.length];
				double[] subArrayIN = new double[matrix.length];
				for(int m= 0; m<matrix.length;m++) {
					subArrayM1[m] = matrix[i][m] * dividend;
					subArrayIN[m] = inverse[i][m] * dividend;
					}
				matrix = MatrixArraySubstraction(matrix, subArrayM1, j);
				inverse = MatrixArraySubstraction(inverse, subArrayIN, j);				
			}
		}
		
		for(int i = 0; i<matrix.length; i++) {
			double dividend = matrix[i][i];
			matrix = MatrixRowDivision(matrix, dividend, i);
			inverse = MatrixRowDivision(inverse, dividend, i);
		}
		
		
		for(int i = matrix.length-1; i>0 ;i--) {
			for(int j = i-1; j >= 0; j--) {
				double dividend = matrix[j][i]/matrix[i][i];
				double[] subArrayM1 = new double[matrix.length];
				double[] subArrayIN = new double[matrix.length];
				for(int m= 0; m<matrix.length;m++) {
					subArrayM1[m] = matrix[i][m] * dividend;
					subArrayIN[m] = inverse[i][m] * dividend;
					}
				matrix = MatrixArraySubstraction(matrix, subArrayM1, j);
				inverse = MatrixArraySubstraction(inverse, subArrayIN, j);				
			}
		}	
		return inverse;
		
	}
	
	/**
	 * merges two matrixes along the rows.
	 * @param matrix1
	 * @param matrix2
	 * @return erges matrix
	 */
	public static double[][] cBindMatrix(double[][] matrix1, double[][] matrix2){
		if(matrix1.length != matrix2.length) {
			throw new IllegalArgumentException("Matrixes need to have the same number of rows!");
		}
		double[][] matrix3 = new double[matrix1.length][matrix1[0].length + matrix2[0].length];
		
		for(int i = 0;i<matrix1.length;i++) {
			for(int j = 0;j<matrix1[0].length;j++) {
				matrix3[i][j] = matrix1[i][j];
			}
		}
		
		for(int i = 0;i<matrix1.length;i++) {
			for(int j = matrix1[0].length;j<matrix1[0].length + matrix2[0].length;j++) {
				matrix3[i][j] = matrix2[i][j - matrix1[0].length];
			}
		}
		
		return matrix3;		
	}
	
	/**
	 * merges the matrix with the array along the rows.
	 * @param matrix
	 * @param vector
	 * @return merged matrix
	 */
	public static double[][] cBindVector(double[][] matrix, double[] vector){
		if(matrix.length != vector.length) {
			throw new IllegalArgumentException("Matrixes need to have the same number of rows as array length!");
		}
		
		double[][] matrix1 = new double[matrix.length][matrix[0].length +1];
		
		for(int i = 0;i<matrix.length;i++) {
			for(int j = 0;j<matrix[0].length;j++) {
				matrix1[i][j] = matrix[i][j];
			}
		}
		
		for(int i = 0;i<matrix1.length;i++) {			
			matrix1[i][matrix[0].length] = vector[i];
			}
		
		return matrix1;		
	}
	
	/**
	 * merges two matrixes along the columns.
	 * @param matrix1
	 * @param matrix2
	 * @return merged matrix
	 */
	public static double[][] rBindMatrix(double[][] matrix1, double[][] matrix2){
		if(matrix1[0].length != matrix2[0].length) {
			throw new IllegalArgumentException("Matrixes need to have the same number of columns!");
		}
		double[][] matrix3 = new double[matrix1.length + matrix2.length][matrix1[0].length];
		
		for(int i = 0;i<matrix1.length;i++) {
			for(int j = 0;j<matrix1[0].length;j++) {
				matrix3[i][j] = matrix1[i][j];
			}
		}
		
		for(int i = matrix1.length;i<matrix1.length + matrix2.length;i++) {
			for(int j = 0;j<matrix2[0].length;j++) {
				matrix3[i][j] = matrix2[i - matrix1.length][j];
			}
		}
		
		return matrix3;		
	}
	
	/**
	 * merges the matrix with the array along the columns.
	 * @param matrix
	 * @param vector
	 * @return merged matrix
	 */
	public static double[][] rBindVector(double[][] matrix, double[] vector){
		if(matrix[0].length != vector.length) {
			throw new IllegalArgumentException("Matrixes need to have the same number of columns as array length!");
		}
		
		double[][] matrix1 = new double[matrix.length +1][matrix[0].length];
		
		for(int i = 0;i<matrix.length;i++) {
			for(int j = 0;j<matrix[0].length;j++) {
				matrix1[i][j] = matrix[i][j];
			}
		}
		
		for(int i = 0;i<vector.length;i++) {			
			matrix1[matrix.length][i] = vector[i];
			}
		
		return matrix1;		
	}
	
	/**
	 * Appends a double to an array.
	 * @param vector
	 * @param append
	 * @return new array.
	 */
	public static double[] vectorAppend(double[] vector, double append) {
		double[] vector2 = new double[vector.length +1];
		for(int i = 0; i< vector.length; i++) {
			vector2[i] = vector[i];
		}
		vector2[vector.length] = append;
		return vector2;		
	}
	
	

}
