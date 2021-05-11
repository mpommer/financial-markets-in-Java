package multiDimensionalIntegration;

/**
 * Interface to calculate and return the corresponding values 
 * (from [0,1] -> [lowerBound,upperBoudn]) and the corrsponding determinant.
 * 
 * @author marce
 *
 */

public interface IntegrationDomain {
	
	/**
	 * function to transform from unit cube to interval from [0,1] -> [lowerBound,upperBoudn]
	 * @param parametersOnUnitCube
	 * @return
	 */
	public double[] fromUnitCube(double[] parametersOnUnitCube);

	/**
	 * returns the dimension
	 * @return
	 */
	public int getDimension();
	
	
	/**
	 * For this transformation f : [0,1]^{n} \mapsto R^{n} this methods returns the value
	 * \[
	 *   det( df/dx (x) )
	 * \]
	 * 
	 * This is required to transform the integral of a function h to the unit cube. It is
	 * \[
	 *   \int_A h(z) dz = \int_[0,1]^{n} h(f(x)) det(df/dx) dx
	 * \]
	 * 
	 * @param parametersOnUnitCurve
	 * @return The determinant of df/dx - the scaleing of applied to an infinitesimal volume.
	 */
	public double getDeterminantOfDifferential(double[] parametersOnUnitCurve);
}
