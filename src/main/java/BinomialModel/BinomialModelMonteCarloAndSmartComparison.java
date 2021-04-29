package BinomialModel;

import AnalyticFormulasAndUsefulOperations.UsefullOperationsVectorsMatrixes;

public class BinomialModelMonteCarloAndSmartComparison {

	public static void main(String[] args) {
		double initialValue = 100.0;
		double decreaseIfDown = 00.5;
		double increaseIfUp = 1.5;
		int numberOfTimes = 35;
		int numberOfTimesLog = 30;
		int numberOfSimulations = 1000000;
		double interestRate = 0.03;
		double interestRatelog = 0.0;


		
		BinomialModelMonteCarlo  bmMonteCarlo = new BinomialModelMonteCarlo(initialValue, decreaseIfDown, increaseIfUp, numberOfTimes, numberOfSimulations, interestRate); 
		BinomialModelSmart  bmSmart = new BinomialModelSmart(initialValue, decreaseIfDown, increaseIfUp, numberOfTimes, interestRate); 
		
		double valueMontecarlo = bmMonteCarlo.getDiscountedAverageValueAtTime(19);
		double valueSmart = bmSmart.getDiscountedAverageValueAtTime(19);
		System.out.println("Value Monte Carlo.......: " + valueMontecarlo);
		System.out.println("Value Smart.............: " + valueSmart);

		bmMonteCarlo.plotEvolutionDiscountedAverageValue();
		bmSmart.plotEvolutionDiscountedAverageValue();

		
		BinomialModelSmartLog	bmSmartLog = new BinomialModelSmartLog(initialValue, decreaseIfDown, increaseIfUp, numberOfTimesLog, interestRatelog);
		BinomialModelMonteCarloLog bmMonteCarloLog = new BinomialModelMonteCarloLog(initialValue, decreaseIfDown, increaseIfUp, numberOfTimesLog, numberOfSimulations,interestRatelog); 
	
		bmSmartLog.plotEvolutionOfProbabilitesOfGain();
		bmMonteCarloLog.plotEvolutionDiscountedAverageValue();
		
		
	}

}
