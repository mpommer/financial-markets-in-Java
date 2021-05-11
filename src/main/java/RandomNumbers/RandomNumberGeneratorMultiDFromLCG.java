package RandomNumbers;

/**
 * Implementation of the multidimensional random number generator.
 * 
 * @author Marcel Pommer
 *
 */

public class RandomNumberGeneratorMultiDFromLCG implements RandomNumberGeneratorND {
	final private int dimension;
	int numberOfRandomNumbers;
	private final RandomNumberGenerator1D ranGenerator;
	private int count = 0;
	
	public RandomNumberGeneratorMultiDFromLCG(int dimension,RandomNumberGenerator1D ranGenerator,
			int numberOfRandomNumbers) {
		this.dimension = dimension;
		this.ranGenerator = ranGenerator;
		this.numberOfRandomNumbers = numberOfRandomNumbers;
	}
	
	public RandomNumberGeneratorMultiDFromLCG(int dimension,RandomNumberGenerator1D ranGenerator) {
		this.dimension = dimension;
		this.ranGenerator = ranGenerator;
		}

	@Override
	public double[][] getRandomNumberSequenceDouble() {
		double[][] ranMatrix = new double[dimension][numberOfRandomNumbers];
		for(int i = 0; i<dimension; i++) {
			for(int j =0; j< numberOfRandomNumbers; j++) {
				ranMatrix[i][j] = ranGenerator.getNextDoubleBetweenZeroOne();
			}
		}
		
		return ranMatrix;
	}

	@Override
	public double[] getNextDouble() {
		count ++;
		double[] ranArray = new double[dimension];
		for(int i = 0; i<dimension; i++) {
			ranArray[i] = ranGenerator.getNextDouble();
		}
		return ranArray;
	}

	@Override
	public int getDimension() {
		return dimension;
	}

}
