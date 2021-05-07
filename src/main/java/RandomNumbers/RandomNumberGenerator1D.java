package RandomNumbers;

public interface RandomNumberGenerator1D {
	
	double[] getRandomNumberSequenceDouble();
	
	double getNextDouble();
	
	default int getDimension(){
		return 1;
	}

}
