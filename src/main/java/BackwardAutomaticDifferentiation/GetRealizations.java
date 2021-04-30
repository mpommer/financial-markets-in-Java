package BackwardAutomaticDifferentiation;

/**
 * Interface for the methods in order to return a double vector/ single float.
 * @author Marcel Pommer
 *
 */

public interface GetRealizations {
	
	/**
	 * Returns the realizations as vector of floating point numbers.
	 *
	 * @return vector of floating point numbers (realizations)
	 */
	double[] getRealizations();
	
	/**
	 * If the randomvariable is deterministic the method returns the floating point value of this object.
	 *
	 * @return Floating point value represented by this object
	 */
	Double asFloatingPoint();

}
