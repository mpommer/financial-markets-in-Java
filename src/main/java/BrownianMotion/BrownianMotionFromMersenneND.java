package BrownianMotion;

import org.apache.commons.math3.distribution.NormalDistribution;


import BackwardAutomaticDifferentiation.RandomVariable;
import BackwardAutomaticDifferentiation.RandomVariableFactory;
import BackwardAutomaticDifferentiation.RandomVariableFactoryImplementation;
import RandomNumbers.MersenneTwisterFromApache;
import TimeDiscretization.TimeDiscretizationInterface;

public class BrownianMotionFromMersenneND implements BrownianMotionND {
	
	RandomVariable[][] brownianMotion;
	TimeDiscretizationInterface timeDiscretization;
	int seed;
	int numberOfPathes;
	int numberOfComponents;
		
	public BrownianMotionFromMersenneND(TimeDiscretizationInterface timeDiscretization, int seed, int numberOfPathes,
			int numberOfComponents) {
		this.timeDiscretization = timeDiscretization;
		this.seed = seed;
		this.numberOfPathes = numberOfPathes;
		this.numberOfComponents = numberOfComponents;
	}


	@Override
	public RandomVariable[] getBrownianIncrement(int timeIndex) {
		if(this.brownianMotion == null) {
			generateBrownianMotion();
		}
		return brownianMotion[timeIndex];
	}

	private void generateBrownianMotion() {
		MersenneTwisterFromApache rangenerator = new MersenneTwisterFromApache(seed);
		NormalDistribution normalDis = new NormalDistribution();
		RandomVariableFactory randomVariableFactory = new RandomVariableFactoryImplementation();
		
		
				final double[][][] brownianIncrementsArray = 
				new double[timeDiscretization.getNumberOfPeriods()][getNumberOfComponents()][numberOfPathes];

				// Pre-calculate square roots of deltaT
				final double[] sqrtOfTimeStep = new double[timeDiscretization.getNumberOfPeriods()];
				for(int timeIndex=0; timeIndex<sqrtOfTimeStep.length; timeIndex++) {
					sqrtOfTimeStep[timeIndex] = Math.sqrt(timeDiscretization.getTimeStep(timeIndex));
				}


				/**
				 * brownian motion as sqrt(t_{i+1}- t_i) * standardNormal
				 */
				
				for(int path=0; path<numberOfPathes; path++) {
					for(int timeIndex=0; timeIndex<timeDiscretization.getNumberOfPeriods(); timeIndex++) {
						final double sqrtDeltaT = sqrtOfTimeStep[timeIndex];
						// Generate uncorrelated Brownian increment
						for(int component=0; component<getNumberOfComponents(); component++) {
						double uniformNumber = rangenerator.getNextDouble();
						brownianIncrementsArray[timeIndex][component][path] = normalDis.inverseCumulativeProbability(uniformNumber)* sqrtDeltaT;
						}
					}
				}

				// Allocate memory for RandomVariableFromDoubleArray wrapper objects.
				RandomVariable[][] brownianVariable = new RandomVariable[timeDiscretization.getNumberOfPeriods()][getNumberOfComponents()];

				// Wrap the values in RandomVariableFromDoubleArray objects
				for(int timeIndex=0; timeIndex<timeDiscretization.getNumberOfPeriods(); timeIndex++) {
					final double time = timeDiscretization.getTime(timeIndex+1);
					for(int component=0; component<getNumberOfComponents(); component++) {
					brownianVariable[timeIndex][component] = randomVariableFactory.fromArray(brownianIncrementsArray[timeIndex][component]);					
					}
					}
				
				this.brownianMotion = brownianVariable;		
	}


	@Override
	public RandomVariable getBrownianIncrement(int timeIndex, int component) {
		if(brownianMotion == null) {
			generateBrownianMotion();
		}
		return brownianMotion[timeIndex][component];
	}

	@Override
	public TimeDiscretizationInterface getTimeDiscretization() {
		return this.timeDiscretization;
	}

	@Override
	public int getNumberOfPaths() {
		return this.numberOfPathes;
	}

	@Override
	public RandomVariable getRandomVariableForConstant(double value) {
		RandomVariableFactory randomVariableFactory = new RandomVariableFactoryImplementation();
		return randomVariableFactory.fromConstant(value);
	}

	@Override
	public BrownianMotionND getCloneWithModifiedSeed(int seed) {
		// TODO Auto-generated method stub
		return new BrownianMotionFromMersenneND(this.timeDiscretization, seed, this.numberOfPathes, this.numberOfComponents);
	}

	@Override
	public BrownianMotionND getCloneWithModifiedTimeDiscretization(TimeDiscretizationInterface newTimeDiscretization) {
		return new BrownianMotionFromMersenneND(newTimeDiscretization, seed, this.numberOfPathes, this.numberOfComponents);
	}


	@Override
	public int getNumberOfComponents() {
		return this.numberOfComponents;
	}
	

}
