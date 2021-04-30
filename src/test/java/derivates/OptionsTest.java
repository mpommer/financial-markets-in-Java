package derivates;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import AnalyticFormulasAndUsefulOperations.AnalyticFormulas;
import BinomialModel.BinomialModelSmart;
import derivates.AmericanOption.analysisOption;


/**
 * Example of how to use the European/American Option with the Binomial model
 * @author Marcel Pommer
 *
 */
class OptionsTest {

	@Test
	void test() {
		double initialValue = 100.0;
		double decreaseIfDown = 0.8;
		double increaseIfUp = 2.0;
		int numberOfTimes = 5;
		int numberOfTimesLog = 30;
		int numberOfSimulations = 1000000;
		double interestRate = 0.0;
		double interestRatelog = 0.0;
		int maturity = numberOfTimes-1;
		final Function<Double, Double> functionCall = x -> Math.max(x - initialValue,0);
		final Function<Double, Double> functionPut = x -> Math.max(initialValue - x,0);

		
		BinomialModelSmart  bmSmart = new BinomialModelSmart(initialValue, decreaseIfDown, increaseIfUp, numberOfTimes, interestRate); 
		EuropeanOption european = new EuropeanOption(bmSmart);
		AnalyticFormulas analyticFormulas = new AnalyticFormulas();

		double disountedPayoff = european.evaluateDiscountedPayoff(maturity, functionCall);
		System.out.println("The discounted Payoff of a European Option is: " + disountedPayoff);
		
		
		AmericanOption american = new AmericanOption(bmSmart);
		System.out.println(american.getValuePortfolioBackward(maturity, functionPut));
		

		
	}

}
