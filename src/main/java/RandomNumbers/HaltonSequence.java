package RandomNumbers;

public class HaltonSequence implements RandomNumberGeneratorND {
	private final int dim;
	private final int[] basis;
	private int numberOfRandomNumbers =0;
	private int counter = 0;
	
	public HaltonSequence(int dim, int[] basis) {
		if(dim != basis.length) {
			throw new IllegalArgumentException("DImension does not correcpond to basis vector");
		}
		this.dim = dim;
		this.basis = basis;		
	}
	
	public HaltonSequence(int dim, int[] basis, int numberOfRandomNumbers) {
		if(dim != basis.length) {
			throw new IllegalArgumentException("DImension does not correcpond to basis vector");
		}
		this.dim = dim;
		this.basis = basis;
		this.numberOfRandomNumbers = numberOfRandomNumbers;
	}

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

}
