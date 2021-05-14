package BrownianMotion;

import BackwardAutomaticDifferentiation.RandomVariable;
import TimeDiscretization.TimeDiscretizationInterface;

public interface BrownianMotion {
	

	RandomVariable getBrownianIncrement(int timeIndex);



	default RandomVariable getBrownianIncrement(final double time) {
		return getBrownianIncrement(getTimeDiscretization().getTimeIndex(time));
	}


	
	TimeDiscretizationInterface getTimeDiscretization();


	
	int getNumberOfPaths();


	
	RandomVariable getRandomVariableForConstant(double value);


	
	BrownianMotion getCloneWithModifiedSeed(int seed);


	
	BrownianMotion getCloneWithModifiedTimeDiscretization(TimeDiscretizationInterface newTimeDiscretization);

}
