package BinomialModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import AnalyticFormulasAndUsefulOperations.UsefullOperationsVectorsMatrixes;
import net.finmath.plots.Plots;

public class BinomialModelSmart implements BinomialModel {

	public double initialValue;
	public double decreaseIfDown;
	public double increaseIfUp;
	int numberOfTimes;
	public double interestRate;
	public double riskneutralProbability;
	double[][] realizations = null;
	
	public BinomialModelSmart(double initialValue, double decreaseIfDown, double increaseIfUp,
            int numberOfTimes, double interestRate) {
		this.initialValue = initialValue;
		this.decreaseIfDown = decreaseIfDown;
		this.increaseIfUp = increaseIfUp;
		this.numberOfTimes = numberOfTimes;
		this.interestRate = interestRate;
		this.riskneutralProbability = (1 + interestRate - decreaseIfDown)/ (increaseIfUp - decreaseIfDown);
	}
	
	

	@Override
	public double[][] generateRealizations() {
		if(this.realizations != null) {return this.realizations;}
		
		double[][] realizations = new double[numberOfTimes][numberOfTimes];

//		in any state start with initial value
		realizations[0][0] = initialValue;
		
		for(int j = 1;j <numberOfTimes; j++) {
			realizations[j][0] = increaseIfUp*realizations[j-1][0];
			for(int i = 1; i< numberOfTimes; i++) {
			realizations[j][i] = decreaseIfDown * realizations[j-1][i-1];
			}
		}		
		this.realizations = realizations;
		return realizations;
	}



	@Override
	public double getDiscountedAverageValueAtTime(int timeIndex) {
		double sum = 0.0;
		double[] probabilities = getProbabilitiesOfRealizationsAtGivenTime(timeIndex);
		double[] realizations = getRealizationsAtTime(timeIndex);
		if(timeIndex == 0 ) {			
			return initialValue;
		} else {
			for(int i =0; i<timeIndex; i++) {
				sum += probabilities[i] * realizations[i];
		}
		}
		return sum* Math.pow(1+interestRate, -timeIndex);
	}

	@Override
	public double probabilityOfGainAtTime(int timeIndex) {
		double sum = 0.0;
		double[] probabilities = getProbabilitiesOfRealizationsAtGivenTime(timeIndex);
		if(timeIndex == 0 ) {
			return sum;
		} else {
			int threshold = findThreshold(timeIndex);
			for(int i = 0; i<timeIndex-threshold;i++) {
				sum += probabilities[i];
			}
		}
		
		return 100 * sum;
	}


	@Override
	public double[] getRealizationsAtTime(int timeIndex) {
		double[][] realizations = generateRealizations();
		double[] realizationsAtTime = UsefullOperationsVectorsMatrixes.matrixGetRow(realizations, timeIndex);
		return realizationsAtTime;
	}
	
	
	public double[] getProbabilitiesOfRealizationsAtGivenTime(int timeIndex) {
		double[] probabilities = new double[timeIndex];
		if(timeIndex == 0 ) {
			double[] re = {0.0};
			return re;
		}else {
			for(int i = 0;i <timeIndex ; i++) {
				probabilities[i] = binomial(timeIndex, i)*Math.pow(riskneutralProbability,timeIndex-i)
						* (Math.pow(1-riskneutralProbability, i));
			}
			
		}
		
		return probabilities;
	}
	
	/**
	 * It returns the smallest integer k such that (u)^kd^(timeIndex-k) > 1,
     * i.e., such that the realization of the process given by k ups and
     * timeIndex - k downs, discounted at time 0, is bigger than the initial value.
	 * @param timeIndex
	 * @return
	 */
	public int findThreshold(int timeIndex) {
		
		return (int) Math.floor(Math.log(Math.pow((1+interestRate)/decreaseIfDown,timeIndex))/Math.log(increaseIfUp/decreaseIfDown));
	}
	
	/**
	 * binomial coeffcient needed to calculate the threshold
	 * @param n
	 * @param k
	 * @return
	 */
	private static long binomial(int n, int k) {
        if ((n == k) || (k == 0))
            return 1;
        else
            return binomial(n - 1, k) + binomial(n - 1, k - 1);
    }

	public double[] getEvolutionDiscountedAverageValue() {
		double[] evolutionDiscountedAverage = new double[numberOfTimes];
		for(int i = 0; i<numberOfTimes;i++) {
			evolutionDiscountedAverage[i] = getDiscountedAverageValueAtTime(i);
		}
		return evolutionDiscountedAverage;
	}

	public void plotEvolutionDiscountedAverageValue() {
		final List<Double> evolutionDiscountedAverage = new ArrayList<Double>();
		final List<Double> times = new ArrayList<Double>();
		final double[] discountedArray = getEvolutionDiscountedAverageValue();
		
		for(int i = 0; i<numberOfTimes;i++) {
			evolutionDiscountedAverage.add(discountedArray[i]);
			times.add((double) i);
			}
 		Plots.createPlotScatter(times, evolutionDiscountedAverage,0,0, 2)
		.setTitle("Evolution of the Discounted Average smart model")
		.setXAxisLabel("time")
		.setYAxisLabel("Discounted Average")
		.setYAxisNumberFormat(new DecimalFormat("0.00")).show();	}
	
	public double[] getEvolutionProbabilitiesOfGain() {
		double[] evolutionProbabilitiesOfGain = new double[numberOfTimes];
		for(int i = 0; i<numberOfTimes;i++) {
			evolutionProbabilitiesOfGain[i] = probabilityOfGainAtTime(i);
		}
		return evolutionProbabilitiesOfGain;
	}

	public void plotEvolutionProbabilitiesOfGain() {
		final List<Double> evolutionProbabilitiesOfGain = new ArrayList<Double>();
		final List<Double> times = new ArrayList<Double>();
		final double[] probabilitiOfGain = getEvolutionProbabilitiesOfGain();
		
		for(int i = 0; i<numberOfTimes;i++) {
			evolutionProbabilitiesOfGain.add(probabilitiOfGain[i]);
			times.add((double) i);
			}
 		Plots.createPlotScatter(times, evolutionProbabilitiesOfGain,0,0, 2)
		.setTitle("Evolution of the Discounted Average")
		.setXAxisLabel("time")
		.setYAxisLabel("Discounted Average")
		.setYAxisNumberFormat(new DecimalFormat("0.00")).show();	}
	
	
}
