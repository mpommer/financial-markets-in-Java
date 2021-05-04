package TimeDiscretization;

import java.util.ArrayList;




public interface TimeDiscretizationInterface {

	
	int numberOfTimes();



	int getNumberOfPeriods();



	double getTime(int timeIndex);


	double getTimeStep(int timeIndex);



	int getTimeIndex(double time);


	int getTimeIndexNearestLessOrEqual(double time);


	int getTimeIndexNearestGreaterOrEqual(double time);


	double[] getAsDoubleArray();


	ArrayList<Double> getAsArrayList();

}
