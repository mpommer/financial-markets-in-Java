package RandomNumbers;
/**
 * Class to generate a halton sequence in order to get a low discrepancy.
 * 
 * @author Marcel Pommer
 *
 */

public class HaltonSequence implements RandomNumberGenerator1D {
	private final int numberOfRandomNumbers;
	private int index = 0;
	
	public HaltonSequence(int numberOfRandomNumbers) {
		this.numberOfRandomNumbers = numberOfRandomNumbers;
	}
	
	public HaltonSequence() {
		this.numberOfRandomNumbers = 0;
		
	}
	
	public double getNumberIndexBase(int index, int base) {
		index++;
		double haltonNumber = 0.0;
		double factor = 1.0/(double) base;
		while(index>0) {
			haltonNumber += (index%base) * factor;
			factor /= base;
			index /= base;
		}
		return haltonNumber;
	}

	@Override
	public double[] getRandomNumberSequenceDouble() {
		double[] randomNumbers = new double[this.numberOfRandomNumbers];
		for(int index = 0; index<this.numberOfRandomNumbers;index++) {
			randomNumbers[index] = getNumberIndexBase(index, 2);
		}
		
		return randomNumbers;
	}

	@Override
	public double getNextDouble() {
		index ++;
		return getNumberIndexBase(this.index, 2);
	}

}
