package BrownianMotion;

import BackwardAutomaticDifferentiation.RandomVariable;

import TimeDiscretization.TimeDiscretizationInterface;

public interface BrownianMotionND {
	
	RandomVariable[] getBrownianIncrement(int timeIndex);

	RandomVariable getBrownianIncrement(int timeIndex, int component);



	default RandomVariable getBrownianIncrement(final double time, int component) {
		return getBrownianIncrement(getTimeDiscretization().getTimeIndex(time), component);
	}


	
	TimeDiscretizationInterface getTimeDiscretization();


	
	int getNumberOfPaths();


	
	RandomVariable getRandomVariableForConstant(double value);

	int getNumberOfComponents();
	
	BrownianMotionND getCloneWithModifiedSeed(int seed);


	
	BrownianMotionND getCloneWithModifiedTimeDiscretization(TimeDiscretizationInterface newTimeDiscretization);

}

