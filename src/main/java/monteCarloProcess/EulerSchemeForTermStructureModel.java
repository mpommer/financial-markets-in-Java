package monteCarloProcess;

import BackwardAutomaticDifferentiation.RandomVariable;




import BrownianMotion.BrownianMotionND;
import TimeDiscretization.TimeDiscretizationInterface;
import monteCarloProcessModel.ProcessModel;

public class EulerSchemeForTermStructureModel implements process {
	
	final BrownianMotionND BM;
	
	final ProcessModel model;
	
	private RandomVariable[][] discreteProcess = null;
	
	final TimeDiscretizationInterface timedis;

	public EulerSchemeForTermStructureModel(final ProcessModel model, final BrownianMotionND BM, final TimeDiscretizationInterface timedis) {
		this.BM = BM;
		this.model=model;
		this.timedis = timedis;
	}

	@Override
	public RandomVariable getProcessValue(int timeIndex, int componentIndex) {
		calculateProcess();		
		return discreteProcess[timeIndex][componentIndex];
	}

	@Override
	public TimeDiscretizationInterface getTimeDiscretization() {
		return timedis;
	}

	@Override
	public double getTime(int timeIndex) {
		return timedis.getTime(timeIndex);
	}

	@Override
	public int getTimeIndex(double time) {
		return timedis.getTimeIndex(time);
	}

	@Override
	public int getNumberOfPaths() {
		return BM.getNumberOfPaths();
	}

	@Override
	public BrownianMotionND getBrownianMotion() {
		return BM;
	}
	
	private void calculateProcess() {
		if (discreteProcess != null && discreteProcess.length != 0) {
			return;
		}
				
		discreteProcess = new RandomVariable[getTimeDiscretization().getNumberOfPeriods() + 1][getNumberOfComponents()];
		
		
		final RandomVariable[] initialState = model.getInitialState();
		final RandomVariable[] currentState = new RandomVariable[getNumberOfComponents()];
		for (int componentIndex = 0; componentIndex < getNumberOfComponents(); componentIndex++) {
			currentState[componentIndex] = initialState[componentIndex];
			discreteProcess[0][componentIndex] = model.applyStateSpaceTransform(currentState[componentIndex]);
		}
		
		for (int timeIndex2 = 1; timeIndex2 < getTimeDiscretization().getNumberOfPeriods()+1; timeIndex2++) {
			
			final RandomVariable[] drift;
			drift = model.getDrift(this, timeIndex2 - 1, discreteProcess[timeIndex2 - 1]);
			
			for (int componentIndex = 0; componentIndex < getNumberOfComponents(); componentIndex++) {
			// Generate process from timeIndex-1 to timeIndex
			final double deltaT = getTime(timeIndex2) - getTime(timeIndex2 - 1);
			final RandomVariable[]	factorLoadings		= model.getFactorLoading(this, timeIndex2 - 1, componentIndex, discreteProcess[timeIndex2 - 1]);

						
			final RandomVariable[] brownianIncrement	= BM.getBrownianIncrement(timeIndex2 - 1);
			
			
			final RandomVariable		zero	= model.getRandomVariableForConstant(0.0);
			RandomVariable brownianPart = zero;
			for(int i = 0; i< model.getNumberOfFactors(); i++) {
				brownianPart = brownianPart.add(brownianIncrement[i].mult(factorLoadings[i]));
			}
			
			RandomVariable driftPart = zero;
			if(drift[componentIndex] !=null) {
				driftPart = drift[componentIndex].mult(deltaT);
			} else {
				driftPart = zero;
			}
			RandomVariable periodMinusOnePart = currentState[componentIndex];
			
			
			RandomVariable nextEulerStep = periodMinusOnePart.add(driftPart).add(brownianPart);
			
			currentState[componentIndex] = nextEulerStep;
					
			discreteProcess[timeIndex2][componentIndex] = model.applyStateSpaceTransform(currentState[componentIndex]);
			
		}
		}
	}


	private int getNumberOfComponents() {
		return model.getNumberOfComponents();
	}

	@Override
	public int getnumberOfFactors() {
		return model.getNumberOfComponents();
	}

	@Override
	public ProcessModel getModel() {
		return model;
	}

}
