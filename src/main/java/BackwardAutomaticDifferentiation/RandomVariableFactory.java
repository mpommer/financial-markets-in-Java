package BackwardAutomaticDifferentiation;

/**
 * Interface for the factory for RandomVariable.
 * 
 * @author Marcel Pommer
 *
 */

public interface RandomVariableFactory {
	
	RandomVariable zero();
	
	RandomVariable one();
	
	RandomVariable fromConstant(double constant);

	RandomVariable fromArray(double[] values);

}
