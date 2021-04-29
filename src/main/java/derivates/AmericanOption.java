package derivates;

import java.util.function.Function;

import BinomialModel.BinomialModelSmart;

public class AmericanOption {
	
	BinomialModelSmart binomialModel;
	double[][] valuesExercise; 
	double[][] valuesIfWait; 
	double[][] valuesOption;  
	double[][] exercise; 
	
	public AmericanOption(BinomialModelSmart binModel) {
		this.binomialModel = binModel;
	}
	
	public double getValuePortfolioBackward(int maturity, Function<Double, Double> function) {
		double[] processRealizations = binomialModel.getRealizationsAtTime(maturity);
		double[] payoffRealizations = new double[processRealizations.length];
		for(int i = 0; i<processRealizations.length;i++) {
			payoffRealizations[i] = function.apply(processRealizations[0]);			
		}
		
		for(int j = maturity-1;j>=0;j--) {
			double[] processRealizationsAtTimeJ = binomialModel.getRealizationsAtTime(j);
			double[] optionPart = new double[processRealizations.length];
			for(int i = 0; i<processRealizations.length;i++) {
				optionPart[i] = function.apply(processRealizationsAtTimeJ[0]);			
			}
			double[] valuationPart = new double[processRealizations.length-1];
		for(int i = 0; i<valuationPart.length; i++) {
			valuationPart[i] = (binomialModel.riskneutralProbability*payoffRealizations[j] + 
		(1-binomialModel.riskneutralProbability) * payoffRealizations[j+1])/(1+binomialModel.interestRate);
		}
		for(int i = 0; i<processRealizations.length;i++) {
			payoffRealizations[i] =  Math.max(optionPart[i], valuationPart[i]) ;			
		}
		}	
		
		return payoffRealizations[0];
	}
	
	public void generateAnalysisOption(int maturity, Function<Double, Double> function) {
		double[][] valuesExercise = new double[maturity+1][maturity+1]; 
		double[][] valuesIfWait= new double[maturity+1][maturity+1]; 
		double[][] valuesOption= new double[maturity+1][maturity+1];  
		boolean[][] exercise= new boolean[maturity+1][maturity+1]; 
		double[] processRealizations = binomialModel.getRealizationsAtTime(maturity);
		double[] payoffRealizations = new double[processRealizations.length];
		for(int i = 0; i<processRealizations.length;i++) {
			payoffRealizations[i] = function.apply(processRealizations[0]);	
			valuesExercise[maturity][i] = payoffRealizations[i];
			valuesIfWait[maturity][i] = payoffRealizations[i];
			valuesOption[maturity][i] = payoffRealizations[i];
			exercise[maturity][i] = true;
		}
		
		
		
	}
	

}
