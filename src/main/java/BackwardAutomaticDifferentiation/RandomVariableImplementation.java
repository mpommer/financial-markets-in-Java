package BackwardAutomaticDifferentiation;

/**
 * Implements the Interface RandomVariable. Enables simple operations on random variables (arrays).
 * 
 * @author Marcel Pommer
 *
 */

public class RandomVariableImplementation implements GetRealizations, RandomVariable {

	private double[] randomvalue;

	private boolean isDeterministic = false;

	public RandomVariableImplementation(double[] RandomVariable) {
		super();
		boolean isDeterministic = true;
		for (int i = 0; i < RandomVariable.length; i++) {
			if (RandomVariable[0] != RandomVariable[i]) {
				isDeterministic = false;
			}
		}
		if (isDeterministic) {
			final double[] random = { RandomVariable[0] };
			this.randomvalue = random;
			this.isDeterministic = true;
		} else {
		this.randomvalue = RandomVariable;
		}

	}
	
	
	@Override
	public RandomVariableFactory getFactory() {
		final RandomVariableFactory factory = new RandomVariableFactoryImplementation();
		return factory;
	}

	@Override
	public RandomVariable expectation() {

		double sum1 = 0.0;
		for (int i = 0; i < randomvalue.length; i++) {
			sum1 += randomvalue[i] / randomvalue.length;
		}


		final double[] ranNew = { sum1 };

		return new RandomVariableImplementation(ranNew);
	}

	@Override
	public RandomVariable variance() {
		final RandomVariable ranNew1 = new RandomVariableImplementation(randomvalue);
		
		return ranNew1.sub(ranNew1.expectation()).squared().expectation();
	}
	
	@Override
	public RandomVariable sampleError() {
		final RandomVariable ranNew1 = new RandomVariableImplementation(randomvalue);

		// get the std error as sqrt(variance)*sqrt(n), whre n is the length of the
		// random variable vector
		return ranNew1.variance().sqrt().mult(1 / Math.sqrt(randomvalue.length));
	}

	@Override
	public RandomVariable squared() {
		final double[] ranNew = new double[randomvalue.length];
		for (int i = 0; i < randomvalue.length; i++) {
			ranNew[i] = randomvalue[i] * randomvalue[i];
		}

		return new RandomVariableImplementation(ranNew);
	}

	@Override
	public RandomVariable sqrt() {
		final double[] ranNew = new double[randomvalue.length];
		for (int i = 0; i < randomvalue.length; i++) {
			ranNew[i] = Math.sqrt(randomvalue[i]);
		}

		return new RandomVariableImplementation(ranNew);
	}

	@Override
	public RandomVariable exp() {
		final double[] ranNew = new double[randomvalue.length];
		for (int i = 0; i < randomvalue.length; i++) {
			ranNew[i] = Math.exp(randomvalue[i]);
		}

		return new RandomVariableImplementation(ranNew);
	}

	@Override
	public RandomVariable log() {
		final double[] ranNew = new double[randomvalue.length];
		for (int i = 0; i < randomvalue.length; i++) {
			ranNew[i] = Math.log(randomvalue[i]);
		}

		return new RandomVariableImplementation(ranNew);
	}

	@Override
	public RandomVariable add(double x) {
		final double[] ranNew = new double[randomvalue.length];
		for (int i = 0; i < randomvalue.length; i++) {
			ranNew[i] = randomvalue[i] + x;
		}

		return new RandomVariableImplementation(ranNew);
	}

	@Override
	public RandomVariable add(RandomVariable x) {
		if (isDeterministic(x)) {
			return this.add(getRealizations2(x)[0]);
		} else if (isDeterministic(this)) {
			return x.add(getRealizations2(this)[0]);
		} else {
			if (randomvalue.length != getRealizations2(x).length) {
				throw new IllegalArgumentException("The random value need to have a compatible size");
			}
		final double[] ranNew = new double[randomvalue.length];
		for (int i = 0; i < randomvalue.length; i++) {
			ranNew[i] = randomvalue[i] + getRealizations2(x)[i];
		}

		return new RandomVariableImplementation(ranNew);
	}
	}

	/*
	 *  sub for double missing, instead one need to use add.mult(-1.0)
	 */

	@Override
	public RandomVariable sub(RandomVariable x) {
		if (isDeterministic(x)) {
			return this.add(getRealizations2(x)[0] * (-1.0));
		} else if (isDeterministic(this)) {
			return x.add(getRealizations2(this)[0] * (-1.0));
		} else {
			if (randomvalue.length != getRealizations2(x).length) {
				throw new IllegalArgumentException("The random value need to have a compatible size");
			}
		final double[] ranNew = new double[randomvalue.length];
		for (int i = 0; i < randomvalue.length; i++) {
			ranNew[i] = randomvalue[i] - getRealizations2(x)[i];
		}

		return new RandomVariableImplementation(ranNew);
	}
	}

	@Override
	public RandomVariable mult(double x) {
		final double[] ranNew = new double[randomvalue.length];
		for (int i = 0; i < randomvalue.length; i++) {
			ranNew[i] = randomvalue[i] * x;
		}

		return new RandomVariableImplementation(ranNew);
	}

	@Override
	public RandomVariable mult(RandomVariable x) {
		if (isDeterministic(x)) {
			return this.mult(getRealizations2(x)[0]);
		} else if (isDeterministic(this)) {
			return x.mult(getRealizations2(this)[0]);
		} else {
			if (randomvalue.length != getRealizations2(x).length) {
				throw new IllegalArgumentException("The random value need to have a compatible size");
			}
		final double[] ranNew = new double[randomvalue.length];
		for (int i = 0; i < randomvalue.length; i++) {
			ranNew[i] = randomvalue[i] * getRealizations2(x)[i];
		}

		return new RandomVariableImplementation(ranNew);
	}
	}

	// dividing for double missing, instead use mult(1.0/x)

	@Override
	public RandomVariable div(RandomVariable x) {
		if (!isNonZero(getRealizations2(x))) {
			throw new IllegalArgumentException("RandomValue nedds to be non Zero");
		}
		if (isDeterministic(x)) {
			return this.mult(1.0 / getRealizations2(x)[0]);
		} else if (isDeterministic) {
			final double[] ranNew = new double[getRealizations2(x).length];
			for (int i = 0; i < getRealizations2(x).length; i++) {
				ranNew[i] = randomvalue[0] / getRealizations2(x)[i];
			}
			return new RandomVariableImplementation(ranNew);
		} else {
			if (randomvalue.length != getRealizations2(x).length) {
				throw new IllegalArgumentException("The random value need to have a compatible size");
			}
		final double[] ranNew = new double[randomvalue.length];
		for (int i = 0; i < randomvalue.length; i++) {
			ranNew[i] = randomvalue[i] / getRealizations2(x)[i];
		}

		return new RandomVariableImplementation(ranNew);
	}
	}

	@Override
	public RandomVariable choose(RandomVariable valueIfNonNegative, RandomVariable valueIfNegative) {
		if (isDeterministic) {
			if (randomvalue[0] >= 0) {
				return valueIfNonNegative;
			} else {
				return valueIfNegative;
			}
		}
		double[] valueIfNegativedouble = new double[randomvalue.length];
		double[] valueIfNonNegativedouble = new double[randomvalue.length];

		if (isDeterministic(valueIfNonNegative)) {
			final double[] valueNonNegative = new double[randomvalue.length];
			for (int i = 0; i < randomvalue.length; i++) {
				valueNonNegative[i] = getRealizations2(valueIfNonNegative)[0];
				valueIfNonNegativedouble = valueNonNegative;
			}
		} else {
			final double[] valueNonNegative = getRealizations2(valueIfNonNegative);
			valueIfNonNegativedouble = valueNonNegative;
		}


		if (isDeterministic(valueIfNegative)) {
			final double[] valueNegative = new double[randomvalue.length];
			for (int i = 0; i < randomvalue.length; i++) {
				valueNegative[i] = getRealizations2(valueIfNegative)[0];
				valueIfNegativedouble = valueNegative;
			}
		} else {
			final double[] valueNegative = getRealizations2(valueIfNegative);
			valueIfNegativedouble = valueNegative;
		}


		if (randomvalue.length != valueIfNegativedouble.length
				| randomvalue.length != valueIfNonNegativedouble.length) {
			throw new IllegalArgumentException("The random value need to have a compatible size");
		}

		final double[] ranNew = new double[randomvalue.length];
		for (int i = 0; i < randomvalue.length; i++) {
			if (randomvalue[i] >= 0) {
				ranNew[i] = valueIfNonNegativedouble[i];
			} else {
				ranNew[i] = valueIfNegativedouble[i];
			}
		}

		return new RandomVariableImplementation(ranNew);
	}

	@Override
	public Double asFloatingPoint() {

		// note, in case the method is called, but the object is non deterministic, a warning is created, BUT the returned
		// value is the first value of the vector (random variable)
		// throwing an exception leaded to some complications with the test!
		// alternatively one could return the avergage (see comment below)
		if (!isDeterministic) {
			System.out.println("Warning: Constant created from random variable, which is not constant");

			return randomvalue[0];
//			return new RandomvalueImplementation(randomvalue).getAverage();
		}

		return randomvalue[0];
	}


/**
 * Calculates the average (same as expectation)
 * 
 * @return double
 */
	public double getAverage() {
		final RandomVariable ranNew1 = new RandomVariableImplementation(randomvalue);
		final double average = getRealizations2(ranNew1.expectation())[0];

		return average;

	}

	
	/*
	 *  check wether a randomValue is deterministic (boolean, returns true if randomvalue is deterministic, otherwise false)
	 */
	
	
	public boolean isDeterministic(RandomVariable value) {
		final double[] values = getRealizations2(value);

		for (int i = 0; i < values.length; i++) {
			if (values[0] != values[i]) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * returns a double array with all entries of the randomvariable.
	 * returns null if random variable is not instance of RandomvalueImplementation or RandomValueDifferentialImplementation2
	 * 
	 * @param x
	 * @return array of realizations.
	 */

	public static double[] getRealizations2(RandomVariable x) {
		if (x instanceof RandomVariableImplementation) {
			return ((RandomVariableImplementation) x).randomvalue;
		}
		if (x instanceof RandomVariableDifferentiable) {
			return RandomVariableDifferentiablelmplementation.getRealizations2(x);
		}

		return null;
	}
	
	/**
	 * check if a array contains a zero, return false if array contains 0!
	 * @param randomValue
	 * @return boolean.
	 */
	public static boolean isNonZero(double[] randomValue) {
		for (int i = 0; i < randomValue.length; i++) {
			if (randomValue[i] == 0) {
				return false;
			}
		}
		return true;
	}


	@Override
	public double[] getRealizations() {		
		return randomvalue;
	}



}
