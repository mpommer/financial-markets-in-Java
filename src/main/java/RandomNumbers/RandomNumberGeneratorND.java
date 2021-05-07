package RandomNumbers;

public interface RandomNumberGeneratorND {
	
	double[][] getRandomNumberSequenceDouble();
	
	double[] getNextDouble();
	
	int getDimension();

}
