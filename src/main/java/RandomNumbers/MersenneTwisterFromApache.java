package RandomNumbers;




public class MersenneTwisterFromApache implements RandomNumberGenerator1D {

	private final long seed;

	private final org.apache.commons.math3.random.MersenneTwister mersenneTwister;

	public MersenneTwisterFromApache(long seed) {
		this.seed = seed;
		mersenneTwister	= new org.apache.commons.math3.random.MersenneTwister(seed);
	}


	@Override
	public double[] getRandomNumberSequenceDouble(int numberOfRandomNumbers) {
		double[] sequenceRandomNumbers = new double[numberOfRandomNumbers];
		for(int i = 0; i< numberOfRandomNumbers; i++) {
			sequenceRandomNumbers[i] = mersenneTwister.nextDouble();
		}
		return sequenceRandomNumbers;
	}

	@Override
	public double getNextDouble() {
		return mersenneTwister.nextDouble();
	}

	@Override
	public double getNextDoubleBetweenZeroOne() {
		return getNextDouble();
	}
	


}
