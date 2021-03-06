package BrownianMotion;

import BackwardAutomaticDifferentiation.RandomVariable;
import BackwardAutomaticDifferentiation.RandomVariableFactory;
import BackwardAutomaticDifferentiation.RandomVariableFactoryImplementation;
import RandomNumbers.RandomNumberGeneratorLCG;
import TimeDiscretization.TimeDiscretizationInterface;

public class BrownianMotionGeneration {
	int numberOfBrownianMotions;
	double maturity;
	double[] brownianMotion;
	RandomVariable brownianMotionRandomVariable;
	
	public BrownianMotionGeneration(int numberOfBrownianMotions, double maturity) {
		this.numberOfBrownianMotions = numberOfBrownianMotions;
		this.maturity = maturity;
	}
	
	
	private void generateBrownianMotions(){
		double[] normalRandomNumbers = new double[numberOfBrownianMotions];
		RandomNumberGeneratorLCG ran = new RandomNumberGeneratorLCG(numberOfBrownianMotions, 69);
		for(int i = 0; i<numberOfBrownianMotions; i++) {
			normalRandomNumbers[i] = Math.sqrt(ran.getNextStandardNormal());
		}
		this.brownianMotion = normalRandomNumbers;
		RandomVariableFactory ranFactory = new RandomVariableFactoryImplementation();
		brownianMotionRandomVariable = ranFactory.fromArray(normalRandomNumbers);
	}
	
	public double[] getBrownianMotion() {
		if(brownianMotion == null) {
			generateBrownianMotions();
			return brownianMotion;
		}
		return brownianMotion;
	}
	
	public RandomVariable getBrownianMotionRandomVariable() {
		if(brownianMotionRandomVariable == null) {
			generateBrownianMotions();
			return brownianMotionRandomVariable;
		}
		return brownianMotionRandomVariable;
	}

}
