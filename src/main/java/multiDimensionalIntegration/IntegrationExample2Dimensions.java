package multiDimensionalIntegration;

import java.util.function.DoubleBinaryOperator;

import RandomNumbers.MersenneTwisterFromApache;
import RandomNumbers.RandomNumberGenerator1D;
import RandomNumbers.RandomNumberGeneratorLCG;
import RandomNumbers.RandomNumberGeneratorMultiDFromLCG;
import RandomNumbers.RandomNumberGeneratorND;
import RandomNumbers.VanDerCorputSequence;

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
		RandomNumberGenerator1D ranGenOneDim = new MersenneTwisterFromApache(707);
		int dim = 2;
		long numberOfMonteCarloSimulations = 10000000;
		RandomNumberGeneratorND ranGen = new RandomNumberGeneratorMultiDFromLCG(dim, ranGenOneDim);
		
		IntegratorImplementationPommer integralPommer = new IntegratorImplementationPommer(ranGen, numberOfMonteCarloSimulations);
		
		IntegrationDomain domain = new IntegrationDomainImplementationPommer(lowerBoundX, upperBoundX, lowerBoundY,upperBoundY);
		Integrand integrand = new IntegrandImplementationPommer(function);
		
		
		double value = integralPommer.integrate(integrand, domain);

		return value;
	}

}
