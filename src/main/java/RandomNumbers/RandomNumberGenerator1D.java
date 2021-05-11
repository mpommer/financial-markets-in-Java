package RandomNumbers;

public interface RandomNumberGenerator1D {
	
	double[] getRandomNumberSequenceDouble(int numberOfRandomNumbers);
	
	double getNextDouble();
	
	double getNextDoubleBetweenZeroOne();
	
	default int getDimension(){
		return 1;
	}

}
