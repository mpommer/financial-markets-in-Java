package BinomialModel;

import java.util.function.Function;

import AnalyticFormulasAndUsefulOperations.UsefullOperationsVectorsMatrixes;




public class EuropeanOption {
	BinomialModelSmart binomialModel;
	double[][] amountRiskyAssets;
	double[][] amountRiskFreeAssets;
	
	public EuropeanOption(BinomialModelSmart binModel) {
		this.binomialModel = binModel;
	}
	
	public double evaluatePayoff(int maturity, Function<Double, Double> function) {
		double[] processRealizations = binomialModel.getRealizationsAtTime(maturity);
		double[] payoffRealizations = new double[processRealizations.length];
		for(int i = 0; i<processRealizations.length;i++) {
			payoffRealizations[i] = function.apply(processRealizations[i]);			
		}
		
		double[] processProbabilities = binomialModel.getProbabilitiesOfRealizationsAtGivenTime(maturity);
		double sum = 0.0;
		for(int i = 0; i<processProbabilities.length;i++) {
			sum += 	processProbabilities[i] * payoffRealizations[i];
		}
				
		return sum;
	}

	public double evaluateDiscountedPayoff(int maturity, Function<Double, Double> function) {
		double discountedAverage =  evaluatePayoff(maturity, function)*Math.pow(1+binomialModel.interestRate, -maturity);				
		return discountedAverage;
	}
	
	public double[][] getValuePortfolioBackward(int maturity, Function<Double, Double> function) {
		double[][] valuesPortfolio = new double[maturity + 1][maturity + 1];
		double[] processRealizations = binomialModel.getRealizationsAtTime(maturity);
		double[] payoffRealizations = new double[processRealizations.length];
		for(int i = 0; i<processRealizations.length;i++) {
			payoffRealizations[i] = function.apply(processRealizations[0]);			
		}
		for(int i = 0; i<=maturity; i++) {
			valuesPortfolio[maturity][i] = payoffRealizations[i];
		}
		for(int j = maturity-1;j>=0;j--) {
		for(int i = 0; i<j; i++) {
			valuesPortfolio[j][i] = binomialModel.riskneutralProbability*valuesPortfolio[j+1][i] + (1-binomialModel.riskneutralProbability) * valuesPortfolio[j+1][i+1];
		}
		}	
		
		return valuesPortfolio;
	}
	
	public double[] portfolioValueBackwardAtGivenTime(int currentTime, int maturity,  Function<Double, Double> function) {
		double[][] portfolioValuesBackward = getValuePortfolioBackward(maturity, function);
		double[] valuesAtTime = new double[currentTime+1];
		for(int i = 0; i<currentTime+1;i++) {
			valuesAtTime[i] = portfolioValuesBackward[currentTime][i];
		}
		return valuesAtTime;
	}
	
	public double[] portfolioValueDscountedAtGivenTime(int currentTime, int maturity,  Function<Double, Double> function) {
		double[] valuesAtTime = portfolioValueBackwardAtGivenTime(currentTime, maturity, function );
		double mulitplier = Math.pow(1+ binomialModel.interestRate, -(maturity - currentTime));
		double[] valuesAtTimeDiscounted = UsefullOperationsVectorsMatrixes.vectorMultiplicationWithDouble(valuesAtTime, mulitplier);
		return valuesAtTimeDiscounted;
	}
	
	public double getInitialPortfolioValue(int maturity,  Function<Double, Double> function) {
		double[][] portfolioValues = getValuePortfolioBackward(maturity, function);
		return portfolioValues[0][0];
	}
	
	public double getDiscountedInitialPortfolioValue(int maturity,  Function<Double, Double> function) {
		double initialValue = getInitialPortfolioValue(maturity, function);
		return Math.pow(1+ binomialModel.interestRate, -maturity)*initialValue;
	}
	
	public double[][] getStrategyAmountRiskyAsset(int maturity,  Function<Double, Double> function) {
		double u = binomialModel.increaseIfUp;
		double d = binomialModel.decreaseIfDown;
		double rho = binomialModel.interestRate;
		double[][] amountRiskyAssets = new double[maturity][maturity];
		double[][] amountRiskFreeAssets = new double[maturity][maturity];
		for(int i = maturity-1; i>=0;i--) {
			double[] processAtNextTime = binomialModel.getRealizationsAtTime(i+1);
			double[] PortfolioNextTime = portfolioValueDscountedAtGivenTime(i+1, maturity, function);
			
			for(int j = 0; j<i+1;j++) {
				amountRiskyAssets[i][j] = (PortfolioNextTime[j] - PortfolioNextTime[j+1])/(processAtNextTime[j] - processAtNextTime[j+1]);
				amountRiskFreeAssets[i][j] = (u*PortfolioNextTime[j+1] - d * PortfolioNextTime[j])/(u-d)*Math.pow(1+rho, i);
			}		
		}
		this.amountRiskFreeAssets = amountRiskyAssets;
		this.amountRiskyAssets = amountRiskyAssets;
		return amountRiskyAssets;
	}
	
	public double[][] getStrategyAmountRiskFreeAsset(int maturity,  Function<Double, Double> function) {
	if(this.amountRiskFreeAssets != null) {
		return this.amountRiskFreeAssets;
	} else {
		double[][] r = getStrategyAmountRiskyAsset(maturity, function);
	}
	return this.amountRiskFreeAssets;
	}
	
	public double[][] getStrategyAtGivenTime(int currentTime, int maturity,Function<Double, Double> function){
		if(this.amountRiskFreeAssets != null) {
			return this.amountRiskFreeAssets;
		} else {
			double[][] r = getStrategyAmountRiskyAsset(maturity, function);
		}
		double[] amountRiskFreeAssetAtCurrentTime = UsefullOperationsVectorsMatrixes.matrixGetRow(this.amountRiskFreeAssets, currentTime);
		double[] amountRiskyAssetAtCurrentTime = UsefullOperationsVectorsMatrixes.matrixGetRow(this.amountRiskyAssets, currentTime);		
		double[][] amountRiskyAndRiskFreeAssetsAtCurrentTime = new double[2][amountRiskyAssetAtCurrentTime.length];
		for(int i = 0; i< amountRiskyAssetAtCurrentTime.length; i++) {
			amountRiskyAndRiskFreeAssetsAtCurrentTime[0][i] = amountRiskFreeAssetAtCurrentTime[i];
			amountRiskyAndRiskFreeAssetsAtCurrentTime[1][i] = amountRiskyAssetAtCurrentTime[i];

		}
		return amountRiskyAndRiskFreeAssetsAtCurrentTime;
	}
	
}





