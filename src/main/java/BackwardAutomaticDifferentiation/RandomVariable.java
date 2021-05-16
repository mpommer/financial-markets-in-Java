package BackwardAutomaticDifferentiation;

/**
 * This Interface summarizes all methods which can be performed on a randomvariable. A randomvariable 
 * will be represented by a vector of doubles.
 * 
 * @author Marcel Pommer
 *
 */


public interface RandomVariable {
	/**
	 * Returns a random variable factory that allows to generate new object of the same type as the one implementing this interface.
	 *
	 * @return An object implementing RandomValueFactory
	 */
	RandomVariableFactory getFactory();

	/**
	 * Returns the random variable representing the expectation of this random variable.
	 *
	 * @return New object representing the result.
	 */
	RandomVariable expectation();

	/**
	 * Returns the random variable representing the variance of this random variable.
	 *
	 *Note, that the returned variance is the biased one (n instead of n-1).
	 *
	 *
	 * @return New object representing the result.
	 */
	RandomVariable variance();

	/**
	 * Returns the random variable representing the constant 1/sqrt(n) sigma with sigma = x.sub(x.expectation()).squared().expectation().sqrt()
	 *
	 * @return New object representing the result.
	 */
	RandomVariable sampleError();

	/**
	 * Applies x*x to this object x.
	 *
	 * @return New object representing the result.
	 */
	RandomVariable squared();

	/**
	 * Applies sqrt(x) to this object x.
	 *
	 * @return New object representing the result.
	 */
	RandomVariable sqrt();

	/**
	 * Applies exp(x) to this object x.
	 *
	 * @return New object representing the result.
	 */
	RandomVariable exp();

	/**
	 * Applies log(x) to this object x.
	 *
	 * @return New object representing the result.
	 */
	RandomVariable log();

	/**
	 * Applies a+x to this object a.
	 *
	 * @param x The value x - the value to add.
	 * @return New object representing the result.
	 */
	RandomVariable add(double x);

	/**
	 * Applies a+x to this object a.
	 *
	 * @param x The value x - the value to add.
	 * @return New object representing the result.
	 */
	RandomVariable add(RandomVariable x);

	/**
	 * Applies a-x to this object a.
	 *
	 * @param x The value x - the value to substract.
	 * @return New object representing the result.
	 */
	RandomVariable sub(RandomVariable x);

	/**
	 * Applies a*x to this object a.
	 *
	 * @param x The value x - the factor of this multiplication.
	 * @return New object representing the result.
	 */
	RandomVariable mult(double x);

	/**
	 * Applies a*x to this object a.
	 *
	 * @param x The value x - the factor of this multiplication.
	 * @return New object representing the result.
	 */
	RandomVariable mult(RandomVariable x);

	/**
	 * Applies a/x to this object a.
	 *
	 * @param x The value x - the denominator of the division.
	 * @return New object representing the result.
	 */
	RandomVariable div(RandomVariable x);

	/**
	 * Applies trigger >= 0 ? valueIfNonNegative : valueIfNegative to this object, where trigger = this object.
	 * 
	 * @param valueIfNonNegative The value if this value is positive or zero.
	 * @param valueIfNegative The value is this value is negative.
	 * @return New object representing the result.
	 */
	RandomVariable choose(RandomVariable valueIfNonNegative, RandomVariable valueIfNegative);
	
	double[] returnDoubleArray();

}
