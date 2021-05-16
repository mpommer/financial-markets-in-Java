package TimeDiscretization;

import java.util.ArrayList;




public interface TimeDiscretizationInterface {

	
	int numberOfTimes();



	int getNumberOfPeriods();



	double getTime(int timeIndex);


	double getTimeStep(int timeIndex);


/**
 * returns the nearest time index, if time is not on grid return (-(nearest index) -1). Example T = {0,1,2,3,4}, time = 2.7,
 * nearest index= 3, return = -(3)-1 = -4
 * @param time
 * @return
 */
	int getTimeIndex(double time);


	int getTimeIndexNearestLessOrEqual(double time);


	int getTimeIndexNearestGreaterOrEqual(double time);


	double[] getAsDoubleArray();


	ArrayList<Double> getAsArrayList();

}
