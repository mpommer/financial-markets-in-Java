package BinomialModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import AnalyticFormulasAndUsefulOperations.UsefullOperationsVectorsMatrixes;
import net.finmath.plots.Plots;

/**
 * Implementation of the Binomial model interface using monte carlo.
 * 
 * @author Marcel Pommer
 *
 */

public class BinomialModelMonteCarlo implements BinomialModel {
	double initialValue;
	double decreaseIfDown;
	double increaseIfUp;
	int numberOfTimes;
	int numberOfSimulations;
	double interestRate;
	double riskneutralProbability;
	double[][] realizations = null;
	
	public BinomialModelMonteCarlo(double initialValue, double decreaseIfDown, double increaseIfUp,
            int numberOfTimes, int numberOfSimulations,double interestRate) {
		this.initialValue = initialValue;
		this.decreaseIfDown = decreaseIfDown;
		this.increaseIfUp = increaseIfUp;
		this.numberOfTimes = numberOfTimes;
		this.numberOfSimulations = numberOfSimulations;
		this.interestRate = interestRate;
		this.riskneutralProbability = (1 + interestRate - decreaseIfDown)/ (increaseIfUp - decreaseIfDown);
	}
	
	/**
	 * creates the matrix with all possible up/down combinations.
	 * @return
	 */
	public double[][] getUpsDowns(){
		double[][] upsDowns = new double[numberOfTimes][numberOfSimulations];
				
		for(int i = 0; i<numberOfTimes;i++) {
			for(int j=0;j<numberOfSimulations;j++) {
				double randomNumber = Math.random();
					upsDowns[i][j] = randomNumber <this.riskneutralProbability ? increaseIfUp:decreaseIfDown;			
			}
		}
		
		return upsDowns;
	}
	

	/**
	 * generates the realizations.
	 */
	@Override
	public double[][] generateRealizations() {
		if(this.realizations != null) {return this.realizations;}
		
		double[][] realizations = new double[numberOfTimes][numberOfSimulations];
		double[][] upsDowns = getUpsDowns();
//		in any state start with initial value
		for(int i =0;i <numberOfSimulations; i++) {realizations[0][i] = initialValue;}
		
		for(int j = 1;j <numberOfTimes; j++) {
			for(int i =0;i <numberOfSimulations; i++) {
				realizations[j][i] = realizations[j-1][i] * upsDowns[j-1][i];
			}
			
		}		
		this.realizations = realizations;
		return realizations;
	}


 
	@Override
	public double getDiscountedAverageValueAtTime(int timeIndex) {
		double[] realizationsAtTimeIndex = getRealizationsAtTime(timeIndex);
		double average = UsefullOperationsVectorsMatrixes.vectorAverage(realizationsAtTimeIndex);
		double discountedAverage = Math.pow(1 +interestRate, -timeIndex) * average;
		return discountedAverage;
	}

	@Override
	public double probabilityOfGainAtTime(int timeIndex) {
		double[] realizationsAtTimeIndex = getRealizationsAtTime(timeIndex);
		double[] probOfGain = new double[realizationsAtTimeIndex.length];
		for(int i = 0;i<realizationsAtTimeIndex.length;i++) {
			probOfGain[i] = initialValue *Math.pow(1 +interestRate, timeIndex) <= realizationsAtTimeIndex[i] ? 1:0;
		}
		return UsefullOperationsVectorsMatrixes.vectorAverage(probOfGain);
	}


	@Override
	public double[] getRealizationsAtTime(int timeIndex) {
		double[][] realizations = generateRealizations();
		double[] realizationsAtTime = UsefullOperationsVectorsMatrixes.matrixGetRow(realizations, timeIndex);
		return realizationsAtTime;
	}
	
	/**
	 * Returns the path at time timeindex.
	 * @param timeIndex
	 * @return realizations path
	 */
	public double[] getRealizationsPath(int timeIndex) {
		double[][] realizations = generateRealizations();
		double[] pathAtTime = UsefullOperationsVectorsMatrixes.matrixGetColumn(realizations, timeIndex);
		return pathAtTime;
	}
	
	/**
	 * Calcualtes the max at time timeIndex.
	 * @param timeIndex
	 * @return max at timeIndex
	 */
	public double getMaximumAtTime(int timeIndex) {
		double[] realizationsAtTime = getRealizationsAtTime(timeIndex);
		double max = UsefullOperationsVectorsMatrixes.vectorMax(realizationsAtTime);				
		return max;
	}
	
	/**
	 * gets the evolution array of the max.
	 * @return array of wvolution of max
	 */
	public double[] getEvolutionMax() {
		double[] evolutionOfMax = new double[numberOfTimes];
		for(int i = 1; i<numberOfTimes;i++) {
			evolutionOfMax[i] = getMaximumAtTime(i);
		}
		return evolutionOfMax;
	}
	
	/**
	 * Prints the evolution of the max.
	 */
	public void printEvolutionMaximum() {
		double[] evolutionOfMax = getEvolutionMax();
		
        System.out.println("The path of the maximum evolution is the following:");
        for(int i = 0; i<evolutionOfMax.length;i++) {
        System.out.println(evolutionOfMax[i]);
        }        
	}
	
	/**
	 * Plots the evolution of the max.
	 */
	public void plotEvolutionOfMaximum() {
		final List<Double> evolutionMaximum = new ArrayList<Double>();
		final List<Double> times = new ArrayList<Double>();
		double[] evolutionMax = getEvolutionMax();

		for(double i = 0; i<numberOfTimes; i++) {
			evolutionMaximum.add(evolutionMax[(int) i]);
			times.add(i);
		}
 		Plots.createPlotScatter(times, evolutionMaximum,0,0, 2)
		.setTitle("Evolution of the maximum")
		.setXAxisLabel("time")
		.setYAxisLabel("max")
		.setYAxisNumberFormat(new DecimalFormat("0.00")).show();
		
	}
}
