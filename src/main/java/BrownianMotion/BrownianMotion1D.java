package BrownianMotion;

import BackwardAutomaticDifferentiation.RandomVariable;

import TimeDiscretization.TimeDiscretizationInterface;

public interface BrownianMotion1D {
	

	RandomVariable getBrownianIncrement(int timeIndex);



	default RandomVariable getBrownianIncrement(final double time) {
		return getBrownianIncrement(getTimeDiscretization().getTimeIndex(time));
	}


	
	TimeDiscretizationInterface getTimeDiscretization();


	
	int getNumberOfPaths();


	
	RandomVariable getRandomVariableForConstant(double value);


	
	BrownianMotion1D getCloneWithModifiedSeed(int seed);


	
	BrownianMotion1D getCloneWithModifiedTimeDiscretization(TimeDiscretizationInterface newTimeDiscretization);

}
