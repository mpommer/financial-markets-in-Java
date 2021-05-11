package multiDimensionalIntegrationtest;

import java.text.DecimalFormat;

import java.util.function.DoubleBinaryOperator;

import org.junit.Assert;
import org.junit.Test;

import multiDimensionalIntegration.Integrand;
import multiDimensionalIntegration.IntegrandImplementationPommer;
import multiDimensionalIntegration.IntegrationDomain;
import multiDimensionalIntegration.IntegrationDomainSimpsonsRule;
import multiDimensionalIntegration.IntegrationExample2Dimensions;
import multiDimensionalIntegration.IntegratorImplementationSimpsonsRule;

public class IntegrationTestPommer {

	@Test
	public void testMonteCarloIntegrator() {
		DecimalFormat df = new DecimalFormat("#.###");
		DoubleBinaryOperator integral1 = (x,y) -> x + y;
		DoubleBinaryOperator integral2 = (x,y) -> x*x + y*y;
		double integralAnalytic1 = 8.0;
		double integralAnalytic2 = 10.666666;
		double lowx = 0.0;
		double highx = 2.0;
		
		double lowy = 0.0;
		double highy = 2.0;	
		
		int dim = 2;
		int numberOfPoints = 10000;
		double[][] boundaries = {{lowx, highx},{lowy, highy}};
		
		
		IntegratorImplementationSimpsonsRule solutionSimpson = new IntegratorImplementationSimpsonsRule(dim,
				numberOfPoints, boundaries);
		
		double monteCarloValue1 = IntegrationExample2Dimensions.getIntegral(integral1, lowx, highy, lowy, highx);
		double monteCarloValue2 = IntegrationExample2Dimensions.getIntegral(integral2, lowx, highy, lowy, highx);
		IntegrationDomain domain = new IntegrationDomainSimpsonsRule(dim,
				numberOfPoints, boundaries);
		Integrand integrand1 = new IntegrandImplementationPommer(integral1);
		Integrand integrand2 = new IntegrandImplementationPommer(integral2);
		double valueSimpsons1 = solutionSimpson.integrate(integrand1, domain);
		double valueSimpsons2 = solutionSimpson.integrate(integrand2, domain);

		System.out.println("Comparison Function analytic value:");
		System.out.println("Analytic value: " + df.format(integralAnalytic1) + "\t\t " + df.format(monteCarloValue1));
		System.out.println("Analytic value: " + df.format(integralAnalytic2) + "\t\t " + df.format(monteCarloValue2));

		System.out.println("-".repeat(70));
		System.out.println("Computation with simpsons rule");
		System.out.println("Analytic value: " + df.format(integralAnalytic1) + "\t\t " + df.format(valueSimpsons1));
		System.out.println("Analytic value: " + df.format(integralAnalytic2) + "\t\t " + df.format(valueSimpsons2));

		double tolerance = 0.01;
		double toleranceSimpsons = 0.1;
		Assert.assertEquals("Integral", monteCarloValue1, integralAnalytic1, tolerance);
		Assert.assertEquals("Integral", monteCarloValue2, integralAnalytic2, tolerance);
		Assert.assertEquals("Integral", valueSimpsons1, integralAnalytic1, toleranceSimpsons);
		Assert.assertEquals("Integral", valueSimpsons2, integralAnalytic2, toleranceSimpsons);


	}

}
