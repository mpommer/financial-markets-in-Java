package BinomialModel;

public interface BinomialModel {
	
	/**
	 * generates the realizations
	 * @return
	 */
	double[][] generateRealizations();
	
	/**
	 * returns the realizations(array) at given time.
	 * @param timeIndex
	 * @return
	 */
	double[] getRealizationsAtTime(int timeIndex);
	
	/**
	 * dicsounted average at time tiIndex
	 * @param timeIndex
	 * @return value of the process
	 */
	double getDiscountedAverageValueAtTime(int timeIndex);
	
	/**
	 * calculates the probability of gain at given time.
	 * @param timeIndex
	 * @return probability of gaining
	 */
	double probabilityOfGainAtTime(int timeIndex);
	
	

}
