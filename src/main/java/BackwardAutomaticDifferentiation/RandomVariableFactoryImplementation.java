package BackwardAutomaticDifferentiation;

/**
 * Implementation of the factory of RandomVariable.
 * @author Marcel Pommer
 *
 */

public class RandomVariableFactoryImplementation implements RandomVariableFactory {

	@Override
	public RandomVariable zero() {
		final double[] zero = { 0.0 };
		final RandomVariable neutralElementAdd = new RandomVariableImplementation(zero);
		return neutralElementAdd;
	}

	@Override
	public RandomVariable one() {
		final double[] one = { 1.0 };
		final RandomVariable neutralElementMult = new RandomVariableImplementation(one);
		return neutralElementMult;
	}

	@Override
	public RandomVariable fromConstant(double constant) {
		final double[] constantrandomValue = { constant };
		final RandomVariable randomValueFromConstant = new RandomVariableImplementation(constantrandomValue);
		return randomValueFromConstant;
	}

	@Override
	public RandomVariable fromArray(double[] values) {
		final RandomVariable randomValuefromArray = new RandomVariableImplementation(values);
		return randomValuefromArray;
	}

}
