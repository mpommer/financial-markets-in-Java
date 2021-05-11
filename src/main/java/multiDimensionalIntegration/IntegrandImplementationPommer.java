package multiDimensionalIntegration;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of the Integrand Interface for a DoubleBinaryOperator function.
 * @author marce
 *
 */


public class IntegrandImplementationPommer implements Integrand {
	DoubleBinaryOperator function;
	
	public IntegrandImplementationPommer(DoubleBinaryOperator function) {
		this.function = function;
	}

	
	@Override
	public double value(double[] arguments) {
		double value = function.applyAsDouble(arguments[0], arguments[1]);
		return value;
	}
	
}
