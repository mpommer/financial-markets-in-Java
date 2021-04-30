package BackwardAutomaticDifferentiation;

/**
 * Interface for the factory for RandomvariableDifferentiable.
 * 
 * @author Marcel Pommer
 *
 */

public interface RandomVariableDifferentiableFactory extends RandomVariableFactory{
	
	RandomVariableDifferentiable zero();
	
	RandomVariableDifferentiable one();
	
	RandomVariableDifferentiable fromConstant(double constant);
	
	RandomVariableDifferentiable fromArray(double[] values);

}
