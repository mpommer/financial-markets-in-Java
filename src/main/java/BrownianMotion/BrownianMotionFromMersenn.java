package BrownianMotion;

import org.apache.commons.math3.distribution.NormalDistribution;

import BackwardAutomaticDifferentiation.RandomVariable;
import BackwardAutomaticDifferentiation.RandomVariableFactory;
import BackwardAutomaticDifferentiation.RandomVariableFactoryImplementation;
import RandomNumbers.MersenneTwisterFromApache;
import TimeDiscretization.TimeDiscretizationInterface;

public class BrownianMotionFromMersenn implements BrownianMotion1D {
	RandomVariable[] brownianMotion;
	TimeDiscretizationInterface timeDiscretization;
	int seed;
	int numberOfPathes;
		
	public BrownianMotionFromMersenn(TimeDiscretizationInterface timeDiscretization, int seed, int numberOfPathes) {
		this.timeDiscretization = timeDiscretization;
		this.seed = seed;
		this.numberOfPathes = numberOfPathes;
	}

	@Override
	public RandomVariable getBrownianIncrement(int timeIndex) {
		if(this.brownianMotion == null) {
			generateBrownianMotion();
		}
		return brownianMotion[timeIndex];
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
	public BrownianMotion1D getCloneWithModifiedSeed(int seed) {		
		return new BrownianMotionFromMersenn(this.timeDiscretization, seed, this.numberOfPathes);
	}

	@Override
	public BrownianMotion1D getCloneWithModifiedTimeDiscretization(TimeDiscretizationInterface newTimeDiscretization) {		
		return new BrownianMotionFromMersenn(newTimeDiscretization, this.seed, this.numberOfPathes);
	}
	
	private void generateBrownianMotion() {
		MersenneTwisterFromApache rangenerator = new MersenneTwisterFromApache(seed);
		NormalDistribution normalDis = new NormalDistribution();
		RandomVariableFactory randomVariableFactory = new RandomVariableFactoryImplementation();
		
		
				final double[][] brownianIncrementsArray = new double[timeDiscretization.getNumberOfPeriods()][(int) numberOfPathes];

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
						double uniformNumber = rangenerator.getNextDouble();
						brownianIncrementsArray[timeIndex][path] = normalDis.inverseCumulativeProbability(uniformNumber)* sqrtDeltaT;
					}
				}

				// Allocate memory for RandomVariableFromDoubleArray wrapper objects.
				RandomVariable[] brownianVariable = new RandomVariable[timeDiscretization.getNumberOfPeriods()];

				// Wrap the values in RandomVariableFromDoubleArray objects
				for(int timeIndex=0; timeIndex<timeDiscretization.getNumberOfPeriods(); timeIndex++) {
					final double time = timeDiscretization.getTime(timeIndex+1);					
					brownianVariable[timeIndex] =	randomVariableFactory.fromArray(brownianIncrementsArray[timeIndex]);					
				}
				
				this.brownianMotion = brownianVariable;
			}		
	}

