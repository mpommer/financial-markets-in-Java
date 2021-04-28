package BinomialModel;

public interface BinomialModel {
	
	double[][] generateRealizations();
	
	double[] getRealizationsAtTime(int timeIndex);
	
	double getDiscountedAverageValueAtTime(int timeIndex);
	
	double probabilityOfGainAtTime(int timeIndex);
	

}
