package TimeDiscretization;

import java.util.ArrayList;

public class TimeDiscretizationImplementation implements TimeDiscretizationInterface {
	double[] timeDiscretization;
	int beginning;
	int end;
	double periodLength;
	int numberOfPeriods;
	
	public TimeDiscretizationImplementation(int beginning, int end, int numberOfPeriods) {
		this.beginning = beginning;
		this.end = end;
		this.numberOfPeriods = numberOfPeriods;
		double[] times = new double[numberOfPeriods+1];
		this.periodLength = (double)(end-beginning)/(double)numberOfPeriods;
		for(int i = 0; i<numberOfPeriods+1; i++) {
			times[i] = beginning + i*this.periodLength;
		}
		this.timeDiscretization = times;
	}
	
	public TimeDiscretizationImplementation(int beginning, int end, double periodLength) {
		this.beginning = beginning;
		this.end = end;
		this.periodLength = periodLength;
		if(((end-beginning)/periodLength)%1==0){
			throw new IllegalArgumentException("Time cannot be split up an even period with period length " + periodLength);	
		}
		this.numberOfPeriods = (int) ((end-beginning)/periodLength);
		double[] times = new double[numberOfPeriods+1];

		for(int i = 0; i<numberOfPeriods+1; i++) {
			times[i] = beginning + i*this.periodLength;
		}
		this.timeDiscretization = times;
	}
	
	public TimeDiscretizationImplementation(int beginning, int end, double[] discretizationArray) {
		this.beginning = beginning;
		this.end = end;
		this.numberOfPeriods = discretizationArray.length;
		this.timeDiscretization = discretizationArray;
	}
	
	@Override
	public int numberOfTimes() {
		// TODO Auto-generated method stub
		return numberOfPeriods+1;
	}

	@Override
	public int getNumberOfPeriods() {
		// TODO Auto-generated method stub
		return numberOfPeriods;
	}

	@Override
	public double getTime(int timeIndex) {
		
		return timeDiscretization[timeIndex];
	}

	@Override
	public double getTimeStep(int timeIndex) {
		double timeStep = timeDiscretization[timeIndex+1] -timeDiscretization[timeIndex];
		return timeStep;
	}

	@Override
	public int getTimeIndex(double time) {

		for(int i = 0; i< timeDiscretization.length; i++) {
			if(timeDiscretization[i] == time) {
				return i;
			}
		}
		throw new IllegalArgumentException("No time Index found for time " + time + ". Use get nearest instead.");	
		
	}

	@Override
	public int getTimeIndexNearestLessOrEqual(double time) {
		for(int i = 0; i< timeDiscretization.length; i++) {
			if(timeDiscretization[i] > time) {
				return i-1;
			}
		}
		
		return timeDiscretization.length;
	}

	@Override
	public int getTimeIndexNearestGreaterOrEqual(double time) {
		for(int i = 0; i< timeDiscretization.length; i++) {
			if(timeDiscretization[i] > time) {
				return i;
			}
		}
		
		throw new IllegalArgumentException("Index to high");	
	}

	@Override
	public double[] getAsDoubleArray() {
		return timeDiscretization;
	}

	@Override
	public ArrayList<Double> getAsArrayList() {
		ArrayList<Double> timeDis = new ArrayList<Double>();
		for(int i = 0; i< timeDiscretization.length; i++) {
			timeDis.add(timeDiscretization[i]);
		}
		return timeDis;
	}

}
