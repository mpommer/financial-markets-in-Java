package derivates;

import java.util.function.Function;

import AnalyticFormulasAndUsefulOperations.UsefullOperationsVectorsMatrixes;
import BinomialModel.BinomialModelSmart;

public class AmericanOption {
	
	BinomialModelSmart binomialModel;
	private double[][] valuesExercise; 
	private double[][] valuesIfWait; 
	private double[][] valuesOption;  
	private boolean[][] exercise; 
	
	public enum analysisOption {
		VALUESEXERCISE, VALUESIFWAIT, VALUESOPTION, EXERCISE
	}
	
	public AmericanOption(BinomialModelSmart binModel) {
		this.binomialModel = binModel;
	}
	
	public double getValuePortfolioBackward(int maturity, Function<Double, Double> function) {
		double[] processRealizations = binomialModel.getRealizationsAtTime(maturity);
		double[] payoffRealizations = new double[processRealizations.length];
		for(int i = 0; i<processRealizations.length;i++) {
			payoffRealizations[i] = function.apply(processRealizations[i]);			
		}
		
		for(int timeIndexBackward = maturity-1;timeIndexBackward>=0;timeIndexBackward--) {
			double[] processRealizationsAtTimeJ = binomialModel.getRealizationsAtTime(timeIndexBackward);
			double[] optionPart = new double[processRealizationsAtTimeJ.length];
			for(int i = 0; i<=timeIndexBackward;i++) {
				optionPart[i] = function.apply(processRealizationsAtTimeJ[i]);			
			}
			double[] valuationPart = new double[processRealizationsAtTimeJ.length];
		for(int i = 0; i<=timeIndexBackward; i++) {
			valuationPart[i] = (binomialModel.riskneutralProbability*payoffRealizations[i] + 
		(1-binomialModel.riskneutralProbability) * payoffRealizations[i+1])/(1+binomialModel.interestRate);
		}
		for(int i = 0; i<=timeIndexBackward;i++) {
			payoffRealizations[i] =  Math.max(optionPart[i], valuationPart[i]) ;			
		}
		}	
		
		return payoffRealizations[0];
	}
	
	
	
	private void generateAnalysisOption(int maturity, Function<Double, Double> function) {
		double[][] valuesExercise = new double[maturity+1][maturity+1]; 
		double[][] valuesIfWait= new double[maturity+1][maturity+1]; 
		double[][] valuesOption= new double[maturity+1][maturity+1];  
		boolean[][] exercise= new boolean[maturity+1][maturity+1]; 
		double[] processRealizations = binomialModel.getRealizationsAtTime(maturity);
		double[] payoffRealizations = new double[processRealizations.length];
		for(int i = 0; i<processRealizations.length;i++) {
			payoffRealizations[i] = function.apply(processRealizations[i]);	
			valuesExercise[maturity][i] = payoffRealizations[i];
			valuesIfWait[maturity][i] = payoffRealizations[i];
			valuesOption[maturity][i] = payoffRealizations[i];
			exercise[maturity][i] = true;
		}
		
		for(int j = maturity-1;j>=0;j--) {
			double[] processRealizationsAtTimeJ = binomialModel.getRealizationsAtTime(j);
			double[] optionPart = new double[j+1];
			for(int i = 0; i<=j;i++) {
				optionPart[i] = function.apply(processRealizationsAtTimeJ[i]);			
			}
			double[] valuationPart = new double[j+1];
			for(int i = 0; i<=j; i++) {
				valuationPart[i] = binomialModel.riskneutralProbability/(1+binomialModel.interestRate)*
				valuesOption[j+1][i] + (1- binomialModel.riskneutralProbability) / (1+binomialModel.interestRate) * 	valuesOption[j+1][i+1];	
			}
			
			for(int i = 0; i<j+1; i++) {
				valuesOption[j][i] = Math.max(optionPart[i], valuationPart[i]); 
				valuesExercise[j][i] = optionPart[i];
				valuesIfWait[j][i] = valuationPart[i];
				exercise[j][i] = optionPart[i]>valuationPart[i]? true:false;
			}			
		}
		this.valuesExercise = valuesExercise;
		this.valuesIfWait = valuesIfWait;
		this.valuesOption = valuesOption;
		this.exercise = exercise;
	}
		
		
		public double[][] getAnalysisOption(int maturity, Function<Double, Double> function, analysisOption returnType){
			if(valuesExercise==null) {
				generateAnalysisOption(maturity,function);
			}		
			switch (returnType) {
			case VALUESEXERCISE:
				return this.valuesExercise;
			case VALUESIFWAIT:
				return this.valuesIfWait;
			case VALUESOPTION:
				return this.valuesOption;	
			case EXERCISE:
				double[][] ex = UsefullOperationsVectorsMatrixes.changeBooleanToDouble(this.exercise);
				return ex;
			}
			return null;
		}
	
	

}
