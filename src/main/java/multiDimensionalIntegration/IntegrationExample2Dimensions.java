package multiDimensionalIntegration;

import java.util.function.DoubleBinaryOperator;

import RandomNumbers.RandomNumberGenerator1D;
import RandomNumbers.RandomNumberGeneratorLCG;
import RandomNumbers.RandomNumberGeneratorMultiDFromLCG;
import RandomNumbers.RandomNumberGeneratorND;

public class IntegrationExample2Dimensions {
	public static void main(String[] args) {
		DoubleBinaryOperator function = (x,y) -> Math.sin(x)*Math.sin(y);
		double lowerBoundX = 0.0;
		double upperBoundX = Math.PI;
		double lowerBoundY = 0.0;
		double upperBoundY = Math.PI;

		double value = getIntegral(function, lowerBoundX,upperBoundX, lowerBoundY, upperBoundY);
		System.out.println(value);
	}

	public static double getIntegral(DoubleBinaryOperator function, double lowerBoundX,
			double upperBoundX, double lowerBoundY, double upperBoundY) {
		long seed = 707;
		int numberOfRandomNumbers = 50000000;
		RandomNumberGenerator1D ranGenOneDim = new RandomNumberGeneratorLCG(numberOfRandomNumbers, seed);
		int dim = 2;
		long numberOfMonteCarloSimulations = 1000;
		RandomNumberGeneratorND ranGen = new RandomNumberGeneratorMultiDFromLCG(10000,ranGenOneDim, dim);
		
		IntegratorImplementationPommer integralPommer = new IntegratorImplementationPommer(ranGen, numberOfMonteCarloSimulations);
		
		IntegrationDomain domain = new IntegrationDomainImplementationPommer(lowerBoundX, upperBoundX, lowerBoundY,upperBoundY);
		Integrand integrand = new IntegrandImplementationPommer(function);
		
		
		double value = integralPommer.integrate(integrand, domain);

		return value;
	}

}
