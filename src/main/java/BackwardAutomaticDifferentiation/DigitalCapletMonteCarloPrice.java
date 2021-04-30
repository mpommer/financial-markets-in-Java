package BackwardAutomaticDifferentiation;

import java.util.Random;

import AnalyticFormulasAndUsefulOperations.AnalyticFormulas;
import AnalyticFormulasAndUsefulOperations.UsefullOperationsVectorsMatrixes;

public class DigitalCapletMonteCarloPrice {

	public static void main(String[] args) {
		final double forwardRate = 0.05;
		final double payoffUnit = 0.9;
		final double volatility = 0.3;
		final double maturity = 2.0;
		final double periodLength = 0.5;
		final double strike = 0.06;
		final int numberOfPaths = 1000000;
		
		Random random = new Random(3287);

		double[] samplesBM = new double[numberOfPaths];
		for(int pathIndex=0; pathIndex<numberOfPaths; pathIndex++) {
			samplesBM[pathIndex] = random.nextGaussian();
		}
		double[] BMUponMaturity = UsefullOperationsVectorsMatrixes.vectorMultiplicationWithDouble(samplesBM, Math.sqrt(maturity));
		
//		calculate the analytic value
		final double analyticDelta = AnalyticFormulas.blackScholesDigitalOptionDelta(forwardRate,0.0,volatility,maturity,strike)*payoffUnit*periodLength;
		final double analyticPrice = AnalyticFormulas.blackScholesDigitalOptionValue(forwardRate,0.0,volatility,maturity,strike)*payoffUnit*periodLength;
		
		System.out.println("Price of digital caplet analytic: " + analyticPrice);
		digitalCaplet(forwardRate, payoffUnit, volatility, BMUponMaturity, maturity, periodLength, strike);
		
		System.out.println("");
		System.out.println("Price of digital caplet analytic: " + analyticDelta);
		digitalCapletDelta(forwardRate, payoffUnit, volatility, BMUponMaturity, maturity, periodLength, strike);
		


	}
	
	private static void digitalCaplet(double forwardRate, double payoffUnit, double volatility,
			double[] brownianMotionUponMaturity, double maturity, double periodLength, double strike) {

		
		final RandomVariable brownianIncrement = new RandomVariableImplementation(brownianMotionUponMaturity);
		final RandomVariableFactory randomValueFactory = brownianIncrement.getFactory();
		
		final RandomVariable forward1 = randomValueFactory.fromConstant(forwardRate);
		final RandomVariable payoffUnit1 = randomValueFactory.fromConstant(payoffUnit);
		final RandomVariable volatility1 = randomValueFactory.fromConstant(volatility);
		final RandomVariable strike1 = randomValueFactory.fromConstant(strike);
		final RandomVariable maturity1 = randomValueFactory.fromConstant(maturity);
		final RandomVariable periodLength1 = randomValueFactory.fromConstant(periodLength);

		
		
		
		final RandomVariable L_T = forward1.mult(
				brownianIncrement.mult(volatility1).add(volatility1.squared().mult(-0.5).mult(maturity1)).exp());
		final RandomVariable exerciceValue = L_T.sub(strike1);

		// disocunted expected value of the caplet
		final RandomVariable payoff = exerciceValue.choose(randomValueFactory.one(), randomValueFactory.zero())
				.mult(periodLength1);

//		 get value, by discounting 
		final RandomVariable value = payoff.mult(payoffUnit1).expectation();

		System.out.println("The Price of the digital caplet is: " + ((GetRealizations)value).asFloatingPoint());
	}

	private static void digitalCapletDelta(double forwardRate, double payoffUnit, double volatility,
			double[] brownianMotionUponMaturity, double maturity, double periodLength, double strike) {
		
		final RandomVariableDifferentiable forwardRate1 = new RandomVariableDifferentiablelmplementation(forwardRate);
		final RandomVariableDifferentiable payoffUnit1 = new RandomVariableDifferentiablelmplementation(payoffUnit);
		final RandomVariableDifferentiable volatility1 = new RandomVariableDifferentiablelmplementation(volatility);
		final RandomVariableDifferentiable brownianMotionUponMaturity1 = new RandomVariableDifferentiablelmplementation(brownianMotionUponMaturity);
		final RandomVariableDifferentiable strike1 = new RandomVariableDifferentiablelmplementation(strike);
		final RandomVariableDifferentiable maturity1 = new RandomVariableDifferentiablelmplementation(maturity);
		final RandomVariableDifferentiable periodLength1 = new RandomVariableDifferentiablelmplementation(periodLength);

		final RandomVariable L_T = forwardRate1.mult(
				brownianMotionUponMaturity1.mult(volatility1).add(volatility1.squared().mult(-0.5).mult(maturity1)).exp());
		final RandomVariable exerciceValue = L_T.sub(strike1);

		// disocunted expected value of the caplet
		final RandomVariable payoff = exerciceValue.choose(new RandomVariableDifferentiablelmplementation(1.0)
				, new RandomVariableDifferentiablelmplementation(0.0)).mult(periodLength1);

//		 get value, by discounting 
		final RandomVariable value = payoff.mult(payoffUnit1).expectation();

		final RandomVariable valueDerivative = ((RandomVariableDifferentiable) value)
				.getDerivativeWithRespectTo(forwardRate1);

		final RandomVariable delta =  valueDerivative.expectation();
		
		System.out.println("The delta of the digital option is: " + ((GetRealizations)delta).asFloatingPoint());
	}
}
