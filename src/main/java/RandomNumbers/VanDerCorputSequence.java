package RandomNumbers;
/**
 * Class to generate a vanDerCorputSequence in order to get a low discrepancy.
 * 
 * @author Marcel Pommer
 *
 */

public class VanDerCorputSequence implements RandomNumberGenerator1D {
	private final int numberOfRandomNumbers;
	private int index = 0;
	
	public VanDerCorputSequence(int numberOfRandomNumbers) {
		this.numberOfRandomNumbers = numberOfRandomNumbers;
	}
	
	public VanDerCorputSequence() {
		this.numberOfRandomNumbers = 0;
		
	}
	
	public double getNumberIndexBase(int index, int base) {
		index++;
		double vanDerCorputNumber = 0.0;
		double factor = 1.0/(double) base;
		while(index>0) {
			vanDerCorputNumber += (index%base) * factor;
			factor /= base;
			index /= base;
		}
		return vanDerCorputNumber;
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
