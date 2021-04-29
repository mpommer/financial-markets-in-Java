package BinomialModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import AnalyticFormulasAndUsefulOperations.UsefullOperationsVectorsMatrixes;
import net.finmath.plots.Plots;

public class BinomialModelMonteCarloLog implements BinomialModel {

	double initialValue;
	double decreaseIfDown;
	double increaseIfUp;
	int numberOfTimes;
	int numberOfSimulations;
	double interestRate;
	double riskneutralProbability;
	double[][] realizations = null;
	
	public BinomialModelMonteCarloLog(double initialValue, double decreaseIfDown, double increaseIfUp,
            int numberOfTimes, int numberOfSimulations,double interestRate) {
		this.initialValue = initialValue;
		this.decreaseIfDown = decreaseIfDown;
		this.increaseIfUp = increaseIfUp;
		this.numberOfTimes = numberOfTimes;
		this.numberOfSimulations = numberOfSimulations;
		this.interestRate = interestRate;
		this.riskneutralProbability = (Math.log(decreaseIfDown)/ (-Math.log(increaseIfUp) + Math.log(decreaseIfDown)));
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
		
		for(int j = 0;j <numberOfTimes; j++) {
			for(int i =0;i <numberOfSimulations; i++) {
				realizations[j][i] = Math.log(realizations[j][i]);
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
			probOfGain[i] = Math.log(initialValue) *Math.pow(1 +interestRate, timeIndex) <= realizationsAtTimeIndex[i] ? 1:0;
		}
		return UsefullOperationsVectorsMatrixes.vectorAverage(probOfGain)*100;
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
		.setTitle("Evolution of the Discounted Average Monte Carlo Log")
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
		.setTitle("Evolution of Probabilities of Gain Monte Carlo log")
		.setXAxisLabel("time")
		.setYAxisLabel("Discounted Average")
		.setYAxisNumberFormat(new DecimalFormat("0.00")).show();	}

}
