package multiDimensionalIntegration;

import RandomNumbers.RandomNumberGeneratorND;

public class IntegratorImplementationPommer implements Integrator {
	private final RandomNumberGeneratorND ranNumber;
	private final long numberOfMonteCarloSImulations;
	
	public IntegratorImplementationPommer(RandomNumberGeneratorND ranNumber, long numberOfMonteCarloSImulations) {
		this.ranNumber = ranNumber;
		this.numberOfMonteCarloSImulations = numberOfMonteCarloSImulations;
	}
	

	@Override
	public double integrate(Integrand integrand, IntegrationDomain integrationDomain) {
		double sum = 0.0;
		
		for(int i = 0; i<this.numberOfMonteCarloSImulations;i++) {
			double[] x = this.ranNumber.getNextDouble();			
			double[] intervall = integrationDomain.fromUnitCube(x);
			double det = integrationDomain.getDeterminantOfDifferential(intervall);
			sum += det* integrand.value(intervall);			
		}

		return sum /this.numberOfMonteCarloSImulations;
	}

}
