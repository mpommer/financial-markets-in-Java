package AnalyticFormulasAndUsefulOperations;

import java.util.function.Function;


public class findZeroBisection {

	public findZeroBisection() {	
	}
	
	public static double bisection(Function<Double, Double> fct, double xmin, double xmax,
			double epsilon) {
	  /*  double error = 1.0;
	    int count = 0;
	    double xMiddle = (xmin + xmax)/2.0;
	    while( error > epsilon & count < 100000) {
	        xMiddle = (xmin + xmax)/2.0;
	        if(fct(xMiddle) * fct(xmin)>0) {
	            xmin = xMiddle;
	        } else {
	            xmax = xMiddle;
	        }
	        count += 1;
	        error = Math.abs(fct(xMiddle));
	    }*/
	    return 0;
	}

}
