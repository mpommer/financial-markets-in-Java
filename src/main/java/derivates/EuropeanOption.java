package derivates;

import java.util.function.Function;

import AnalyticFormulasAndUsefulOperations.UsefullOperationsVectorsMatrixes;
import BinomialModel.BinomialModelSmart;


/**
 * European option (exercice of the option is ony at the end possible). 
 * 
 * @author Marcel Pommer
 *
 */

public class EuropeanOption {
	BinomialModelSmart binomialModel;
	double[][] amountRiskyAssets;
	double[][] amountRiskFreeAssets;
	
	public EuropeanOption(BinomialModelSmart binModel) {
		this.binomialModel = binModel;
	}
	
	/**
	 * Evaluates the payoff.
	 * 
	 * @param maturity
	 * @param function
	 * @return
	 */
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

	/**
	 * discounted payoff
	 * 
	 * @param maturity
	 * @param function
	 * @return
	 */
	public double evaluateDiscountedPayoff(int maturity, Function<Double, Double> function) {
		double discountedAverage =  evaluatePayoff(maturity, function)*Math.pow(1+binomialModel.interestRate, -maturity);				
		return discountedAverage;
	}
	
	/**
	 * calculates the portfolio value backward.
	 * 
	 * @param maturity
	 * @param function
	 * @return
	 */
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
	
	/**
	 * returns the portfolio value at a given time.
	 * 
	 * @param currentTime
	 * @param maturity
	 * @param function
	 * @return
	 */
	public double[] portfolioValueBackwardAtGivenTime(int currentTime, int maturity,  Function<Double, Double> function) {
		double[][] portfolioValuesBackward = getValuePortfolioBackward(maturity, function);
		double[] valuesAtTime = new double[currentTime+1];
		for(int i = 0; i<currentTime+1;i++) {
			valuesAtTime[i] = portfolioValuesBackward[currentTime][i];
		}
		return valuesAtTime;
	}
	
	/**
	 * returns the discounted portfolio value at a given time.
	 * 
	 * @param currentTime
	 * @param maturity
	 * @param function
	 * @return
	 */	
	public double[] portfolioValueDscountedAtGivenTime(int currentTime, int maturity,  Function<Double, Double> function) {
		double[] valuesAtTime = portfolioValueBackwardAtGivenTime(currentTime, maturity, function );
		double mulitplier = Math.pow(1+ binomialModel.interestRate, -(maturity - currentTime));
		double[] valuesAtTimeDiscounted = UsefullOperationsVectorsMatrixes.vectorMultiplicationWithDouble(valuesAtTime, mulitplier);
		return valuesAtTimeDiscounted;
	}
	
	/**
	 * returns initial value of the portfolio.
	 * 
	 * @param maturity
	 * @param function
	 * @return
	 */
	public double getInitialPortfolioValue(int maturity,  Function<Double, Double> function) {
		double[][] portfolioValues = getValuePortfolioBackward(maturity, function);
		return portfolioValues[0][0];
	}
	
	/**
	 * returns discounted initial value of the portfolio.
	 * 
	 * @param maturity
	 * @param function
	 * @return
	 */
	public double getDiscountedInitialPortfolioValue(int maturity,  Function<Double, Double> function) {
		double initialValue = getInitialPortfolioValue(maturity, function);
		return Math.pow(1+ binomialModel.interestRate, -maturity)*initialValue;
	}
	
	/**
	 * calculates the strategy for the amount of the risky asset.
	 * 
	 * @param maturity
	 * @param function
	 * @return
	 */
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
	
	/**
	 * calculates the strategy for the amount of the risk free asset.
	 * 
	 * @param maturity
	 * @param function
	 * @return
	 */
	public double[][] getStrategyAmountRiskFreeAsset(int maturity,  Function<Double, Double> function) {
	if(this.amountRiskFreeAssets != null) {
		return this.amountRiskFreeAssets;
	} else {
		double[][] r = getStrategyAmountRiskyAsset(maturity, function);
	}
	return this.amountRiskFreeAssets;
	}
	
	
	/**
	 * calculates the strategy for the amount of the risk free and the risky asset at given time. The first row is the strategy 
	 * of the risk free asset, the second the strategy of the risky asset.
	 * 
	 * @param maturity
	 * @param function
	 * @return
	 */
	public double[][] getStrategyAtGivenTime(int currentTime, int maturity,Function<Double, Double> function){
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





