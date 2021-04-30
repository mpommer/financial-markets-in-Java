package BackwardAutomaticDifferentiation;



public interface RandomVariableDifferentiable extends RandomVariable {
	
	RandomVariable getDerivativeWithRespectTo(RandomVariableDifferentiable x);

}
