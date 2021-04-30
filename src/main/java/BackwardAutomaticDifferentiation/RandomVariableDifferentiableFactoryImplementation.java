package BackwardAutomaticDifferentiation;

/**
 * Implementation of the factory of RandomvariableDifferentiable.
 * 
 * @author Marcel Pommer
 *
 */

public class RandomVariableDifferentiableFactoryImplementation implements RandomVariableDifferentiableFactory {

	@Override
	public RandomVariableDifferentiable zero() {
		final double[] zero = { 0.0 };
		final RandomVariableDifferentiable neutralElementAdd = new RandomVariableDifferentiablelmplementation(zero);
		return neutralElementAdd;
	}
 
	@Override
	public RandomVariableDifferentiable one() {
		final double one = 1.0;
		final RandomVariableDifferentiable neutralElementMult = new RandomVariableDifferentiablelmplementation(one);
		return neutralElementMult;
	}

	@Override
	public RandomVariableDifferentiable fromConstant(double constant) {

		return new RandomVariableDifferentiablelmplementation(constant);

	}

	@Override
	public RandomVariableDifferentiable fromArray(double[] values) {

		return new RandomVariableDifferentiablelmplementation(values);
	}

}
