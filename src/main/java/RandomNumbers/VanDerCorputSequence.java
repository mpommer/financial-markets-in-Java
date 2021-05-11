package RandomNumbers;

/**
 * Class to generate a vanDerCorputSequence in order to get a low discrepancy.
 * 
 * @author Marcel Pommer
 *
 */

public class VanDerCorputSequence implements RandomNumberGenerator1D {

	private int index = 0;
	

	public VanDerCorputSequence() {
		
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
	public double[] getRandomNumberSequenceDouble(int numberOfRandomNumbers) {
		double[] randomNumbers = new double[numberOfRandomNumbers];
		for(int index = 0; index<numberOfRandomNumbers;index++) {
			randomNumbers[index] = getNumberIndexBase(index, 2);
		}
		
		return randomNumbers;
	}

	@Override
	public double getNextDouble() {
		index ++;
		return getNumberIndexBase(this.index, 2);
	}

	@Override
	public double getNextDoubleBetweenZeroOne() {
		// TODO Auto-generated method stub
		return 0;
	}

}
