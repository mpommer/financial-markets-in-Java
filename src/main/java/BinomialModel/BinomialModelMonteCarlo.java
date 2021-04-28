package BinomialModel;

import java.util.Random;

import AnalyticFormulasAndUsefulOperations.UsefullOperationsVectorsMatrixes;

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
	

	
	@Override
	public double[][] generateRealizations() {
		if(this.realizations != null) {return this.realizations;}
		
		double[][] realizations = new double[numberOfTimes][numberOfSimulations];
		double[][] upsDowns = getUpsDowns();
//		in any state start with initial value
		for(int i =0;i <numberOfSimulations; i++) {realizations[0][i] = initialValue;}
		
		for(int j = 0;j <numberOfTimes; j++) {
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
	
	public double[] getRealizationsPath(int timeIndex) {
		double[][] realizations = generateRealizations();
		double[] pathAtTime = UsefullOperationsVectorsMatrixes.matrixGetColumn(realizations, timeIndex);
		return pathAtTime;
	}

}
