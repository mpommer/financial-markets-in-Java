package multiDimensionalIntegration;

/**
 * Interface to calculate the actual integral
 * 
 * @author Marcel Pommer
 *
 */

public interface Integrator {
	/**
	 * Calculate the integral \int_A f(x) dx 
	 * 
	 * @param integrand The integrand f.
	 * @param integrationDomain The integration domain A.
	 * @return The integral  \int_A f(x) dx 
	 */
	double integrate(Integrand integrand, IntegrationDomain integrationDomain);

}
