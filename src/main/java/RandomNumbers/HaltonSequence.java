package RandomNumbers;

public class HaltonSequence implements RandomNumberGeneratorND {
	private final int dim;
	private final int[] basis;
	
	public HaltonSequence(int dim, int[] basis) {
		if(dim != basis.length) {
			throw new IllegalArgumentException("DImension does not correcpond to basis vector");
		}
		this.dim = dim;
		this.basis = basis;		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getNextDouble() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDimension() {
		// TODO Auto-generated method stub
		return 0;
	}

}
