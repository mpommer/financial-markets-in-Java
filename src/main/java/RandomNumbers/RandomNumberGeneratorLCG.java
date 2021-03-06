package RandomNumbers;

/**
 * Generator of pseudo random numbers. The class offers the opportunity to generate long/double ([0,1]) and standard normal 
 * distributied numbers. 
 * 
 * @author Marcel Pommer
 *
 */

public class RandomNumberGeneratorLCG implements  RandomNumberGenerator1D{
	
	private long[] randomNumbersLong;
	private double[] randomNumbersDouble;
	private double[] randomNumbersStandardNormal;
	private long modulus = 156121L; 
	private final long a =  99656L; 
	private final long c = 969;
	private long seed; 
	private int numberOfPseudoRandomNumbers;
	private int countLong = 1;
	private int countDouble = 1;
	private int countStandardNormal = 1;
	
	/**
	 * Construction which offers the opportunity to create modulus and seed. 
	 * Caution! If seed is greater than modulus, normal distribution will fail!
	 * 
	 * @param numberOfPseudoRandomNumbers
	 * @param seed
	 * @param modulus
	 */
	public RandomNumberGeneratorLCG(int numberOfPseudoRandomNumbers, long seed, long modulus) {
		this.numberOfPseudoRandomNumbers = numberOfPseudoRandomNumbers;
		this.seed = seed;
		this.modulus = modulus;
	}
	
	
	/**
	 * Construction which offers the opportunity to create modulus and seed. 
	 * 	 * 
	 * @param numberOfPseudoRandomNumbers
	 * @param seed
	 */
	public RandomNumberGeneratorLCG(int numberOfPseudoRandomNumbers, long seed) {
		this.numberOfPseudoRandomNumbers = numberOfPseudoRandomNumbers;
		this.seed = seed;
	}


	private void generate() {
		randomNumbersLong = new long[numberOfPseudoRandomNumbers + 1];
		randomNumbersLong[0] = seed; 
		for (int indexOfInteger = 0; indexOfInteger < numberOfPseudoRandomNumbers; indexOfInteger++) {
			long congruence;
			long observedNumber = a * randomNumbersLong[indexOfInteger] + c;
			if(observedNumber>=0) {
				congruence = observedNumber % modulus;
			} else {
				long overFlow =observedNumber - Long.MIN_VALUE +  1;
				long remainderNegativePart = - (Long.MIN_VALUE % modulus);
				long remainderOverflowPart = (overFlow-1) % modulus;
				congruence = (remainderOverflowPart + remainderNegativePart) % modulus;
			}
			randomNumbersLong[indexOfInteger + 1] = congruence;
		}
	}
	
	private void generateDouble() {
		if (randomNumbersLong == null) {
			generate();
			}
		randomNumbersDouble = new double[numberOfPseudoRandomNumbers + 1];
		for(int i =0; i<numberOfPseudoRandomNumbers + 1;i++) {
			randomNumbersDouble[i] = ((double) randomNumbersLong[i])/((double)this.modulus);
		}
	}
	
	private void generateStandardNormal() {
		if (randomNumbersLong == null) {
			generate();
			generateDouble();
			} else if(randomNumbersDouble == null) {
				generateDouble();
			}
		randomNumbersStandardNormal = new double[numberOfPseudoRandomNumbers + 1];
		for(int i = 0; i<numberOfPseudoRandomNumbers;i=i+2) {
			randomNumbersStandardNormal[i] = Math.sqrt(-2.0*Math.log(randomNumbersDouble[i])) *
				Math.cos(2.0*Math.PI*randomNumbersDouble[i+1]);
			randomNumbersStandardNormal[i+1] = Math.sqrt(-2.0*Math.log(randomNumbersDouble[i])) *
					Math.sin(2.0*Math.PI*randomNumbersDouble[i+1]);
		}
	}
		
	
	
	/**
	 * getter method for the sequence of pseudo random standard normal distributed number.
	 *
	 * @return the sequence of pseudo random numbers
	 */
	public double[] getRandomNumberSequenceStandardNormal() {
		if (randomNumbersStandardNormal == null) {
			generateStandardNormal();
			return randomNumbersStandardNormal;
		}
		return randomNumbersStandardNormal; 
	}
	
	
	/**
	 * getter method for the sequence of pseudo random natural numbers double.
	 *
	 * @return the sequence of pseudo random numbers
	 */
	public double[] getRandomNumberSequenceDouble(int numberOfRandomNumbers) {
		if (randomNumbersDouble == null) {
			generateDouble();
			return randomNumbersDouble;
		}
		return randomNumbersDouble; 
	}

	
	/**
	 * getter method for the sequence of pseudo random natural numbers
	 *
	 * @return the sequence of pseudo random numbers
	 */
	public long[] getRandomNumberSequenceLong() {
		if (randomNumbersLong == null) {
			generate();
			return randomNumbersLong;
		}
		return randomNumbersLong; 
	}

	/**	
	 * @return the next Long number of the sequence of pseudo random numbers
	 */
	public long getNextInteger() {
		long[] sequence = getRandomNumberSequenceLong();
		return sequence[countLong++];
	}
	
	/**	
	 * @return the next double number of the sequence of pseudo random numbers
	 */
	public double getNextDouble() {
		double[] sequence = getRandomNumberSequenceDouble(this.numberOfPseudoRandomNumbers);
		return sequence[countDouble++];
	}
	
	/**	
	 * @return the next standard normal number of the sequence of pseudo random numbers
	 */
	public double getNextStandardNormal() {
		double[] sequence = getRandomNumberSequenceStandardNormal();
		return sequence[countStandardNormal++];
	}

	/**
	 * getter method for the modulus
	 *
	 * @return the modulus of the congruence that generates the pseudo random
	 *         numbers
	 */
	public long getModulus() {
		return this.modulus;
	}

	/**
	 * getter method for the length of the simulated sequence
	 *
	 * @return the length of the simulated sequence
	 */
	public int getNumberOfPseudoRandomNumbers() {
		return numberOfPseudoRandomNumbers;
	}


	@Override
	public double getNextDoubleBetweenZeroOne() {
		return getNextDouble()/this.modulus;
	}

}
