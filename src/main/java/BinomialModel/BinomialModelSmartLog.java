package BinomialModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import AnalyticFormulasAndUsefulOperations.UsefullOperationsVectorsMatrixes;
import net.finmath.plots.Plots;

public class BinomialModelSmartLog implements BinomialModel {

	double initialValue;
	double decreaseIfDown;
	double increaseIfUp;
	int numberOfTimes;
	double interestRate;
	double riskneutralProbability;
	double[][] realizations = null;
	
	public BinomialModelSmartLog(double initialValue, double decreaseIfDown, double increaseIfUp,
            int numberOfTimes, double interestRate) {
		this.initialValue = initialValue;
		this.decreaseIfDown = decreaseIfDown;
		this.increaseIfUp = increaseIfUp;
		this.numberOfTimes = numberOfTimes;
		this.interestRate = interestRate;
		this.riskneutralProbability = (Math.log(decreaseIfDown)/ (-Math.log(increaseIfUp) + Math.log(decreaseIfDown)));
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
		
		for(int j = 0;j <numberOfTimes; j++) {
			for(int i =0;i <numberOfTimes; i++) {
				realizations[j][i] = Math.log(realizations[j][i]);
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
			return Math.log(initialValue);
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
			double[] zero = {0.0};
			return zero;
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
	
	public double[] evolutionOfProbabilitiesOfGain() {
		double[] pathEvolutionOfGain = new double[numberOfTimes];
		for(int i = 0;i<numberOfTimes;i++) {
			pathEvolutionOfGain[i] = probabilityOfGainAtTime(i);
		}
		return pathEvolutionOfGain;
	}
	public void printEvolutionProbabilitiesOfGain() {
		double[] gains = evolutionOfProbabilitiesOfGain();
		System.out.println("Evolution of gains:");
		for(int i = 0;i<numberOfTimes;i++) {
			System.out.println(gains[i]);
		}		
	}
	
	/**
	 * Plots the evolution of the probabilities of gain.
	 */
	public void plotEvolutionOfProbabilitesOfGain() {
		

		final List<Double> evolutionGain = new ArrayList<Double>();
		final List<Double> times = new ArrayList<Double>();
		double[] evolution = evolutionOfProbabilitiesOfGain();
		
		for(double i = 0; i<numberOfTimes; i++) {
			evolutionGain.add(evolution[(int) i]);
			times.add(i);
		}
		
 		Plots.createPlotScatter(times, evolutionGain,0,0, 2)
		.setTitle("Evolution of the probabilities of Gain smart log model")
		.setXAxisLabel("time")
		.setYAxisLabel("probability")
		.setYAxisNumberFormat(new DecimalFormat("0.00")).show();
		
	}

}
