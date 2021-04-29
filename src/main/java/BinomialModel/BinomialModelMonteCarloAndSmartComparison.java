package BinomialModel;

import AnalyticFormulasAndUsefulOperations.UsefullOperationsVectorsMatrixes;

public class BinomialModelMonteCarloAndSmartComparison {

	public static void main(String[] args) {
		double initialValue = 100.0;
		double decreaseIfDown = 0.5;
		double increaseIfUp = 1.5;
		int numberOfTimes = 50;
		int numberOfSimulations = 100000;
		double interestRate = 0.0;
		
//		double[] vec = {1,2,3};		
//		double av = UsefullOperationsVectorsMatrixes.vectorAverage(vec);
//		System.out.println(Math.pow(1,-1));

		
		BinomialModelMonteCarlo  bmMonteCarlo = new BinomialModelMonteCarlo(initialValue, decreaseIfDown, increaseIfUp, numberOfTimes, numberOfSimulations, interestRate); 
		BinomialModelMonteCarlo  bmSmart = new BinomialModelMonteCarlo(initialValue, decreaseIfDown, increaseIfUp, numberOfTimes, numberOfSimulations, interestRate); 
		BinomialModelSmartLog	bmSmartLog = new BinomialModelSmartLog(initialValue, decreaseIfDown, increaseIfUp, numberOfTimes, interestRate);
		BinomialModelMonteCarloLog bmMonteCarloLog = new BinomialModelMonteCarloLog(initialValue, decreaseIfDown, increaseIfUp, numberOfTimes, numberOfSimulations, interestRate); 
		
//		System.out.println(bmMonteCarloLog.getDiscountedAverageValueAtTime(5));
		
//		double valueMC = bmMonteCarlo.getDiscountedAverageValueAtTime(100);
//		double valueSmart = bmSmart.getDiscountedAverageValueAtTime(100);
//		System.out.println(valueMC);
//
//		System.out.println(valueSmart);
//		
//		
//		System.out.println(bmMonteCarlo.getMaximumAtTime(4));

//		bmMonteCarlo.plotEvolutionOfMaximum();
//		bmMonteCarlo.plotEvolutionProbabilitiesOfGain();
//		bmMonteCarloLog.plotEvolutionProbabilitiesOfGain();
//		bmSmart.plotEvolutionProbabilitiesOfGain();
		bmSmartLog.plotEvolutionOfProbabilitesOfGain();
		
	}

}
