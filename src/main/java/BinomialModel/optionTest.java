package BinomialModel;

import java.util.function.Function;

public class optionTest {

	public static void main(String[] args) {
		double initialValue = 100.0;
		double decreaseIfDown = 0.5;
		double increaseIfUp = 2.0;
		int numberOfTimes = 5;
		int numberOfTimesLog = 30;
		int numberOfSimulations = 1000000;
		double interestRate = 0.1;
		double interestRatelog = 0.0;
		int maturity = numberOfTimes-1;
		final Function<Double, Double> function = x -> Math.max(x- initialValue,0);
		
		
		BinomialModelSmart  bmSmart = new BinomialModelSmart(initialValue, decreaseIfDown, increaseIfUp, numberOfTimes, interestRate); 
		EuropeanOption european = new EuropeanOption(bmSmart);
		double disountedPayoff = european.evaluateDiscountedPayoff(maturity, function);
		System.out.println(disountedPayoff);
//		System.out.println(european.portfolioValueBackwardAtGivenTime(5, 10, function)[4]);

	}

}
