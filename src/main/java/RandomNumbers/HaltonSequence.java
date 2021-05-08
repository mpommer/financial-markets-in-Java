package RandomNumbers;

/**
 * Generates a halton sequence (multi dimensional random number) with a low discrepancy.
 * 
 * @author Marcel Pommer
 *
 */

public class HaltonSequence implements RandomNumberGeneratorND {
	private final int dim;
	private final int[] basis;
	private int numberOfRandomNumbers =0;
	private int counter = 0;
	
	/**
	 * Constructor if no certain number of points is requested.
	 * 
	 * @param dim
	 * @param basis
	 */
	public HaltonSequence(int dim, int[] basis) {
		if(dim != basis.length) {
			throw new IllegalArgumentException("DImension does not correcpond to basis vector");
		}
		this.dim = dim;
		this.basis = basis;		
	}
	
	/**
	 * Constructor in order to get a certain number of points of the series.
	 * @param dim
	 * @param basis
	 * @param numberOfRandomNumbers
	 */
	public HaltonSequence(int dim, int[] basis, int numberOfRandomNumbers) {
		if(dim != basis.length) {
			throw new IllegalArgumentException("DImension does not correcpond to basis vector");
		}
		this.dim = dim;
		this.basis = basis;
		this.numberOfRandomNumbers = numberOfRandomNumbers;
	}

	/**
	 * Gets the index`te number of the halton series.
	 * @param index
	 * @return
	 */
	public double[] haltonSequencePointFromIndex(int index) {
		double[] haltonPoint = new double[dim];
		VanDerCorputSequence vanDerCorput = new VanDerCorputSequence();
		for(int i =0; i<dim;i++) {
			haltonPoint[i] = vanDerCorput.getNumberIndexBase(index, basis[i]);
		}
		return haltonPoint;
	}

	@Override
	public double[][] getRandomNumberSequenceDouble() {
		double[][] randomNumberSequence = new double[numberOfRandomNumbers][dim];
		for(int index = 0; index<numberOfRandomNumbers; index++) {
			double[] haltonSequence = haltonSequencePointFromIndex(index);
			for(int i = 0;i<dim;i++) {
				randomNumberSequence[index][i] = haltonSequence[i];
			}
		}
		return randomNumberSequence;
	}

	@Override
	public double[] getNextDouble() {
		counter++;
		return haltonSequencePointFromIndex(counter);
	}

	@Override
	public int getDimension() {
		return dim;
	}
	
	/**
	 * returns the basis.
	 * @return
	 */
	public int[] getBasis() {
		return this.basis;
	}
	
	/**
	 * returns the current index(counter) to keep track of how many numbers have been asked for.
	 * @return
	 */
	public int index() {
		return counter;
	}

}
