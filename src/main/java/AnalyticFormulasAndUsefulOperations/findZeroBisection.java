package AnalyticFormulasAndUsefulOperations;

import java.util.function.Function;

/**
 * Class in order to calculate zero points. Might be useful in combination with the derivatives.
 * 
 * @author Marcel Pommer
 *
 */

public class findZeroBisection {
	  public static void main(String[] args) {
		  double xmin = -100.0;
		  double xmax = 100.0;
		  double epsilon = 0.0001;
		  final Function<Double, Double> function = x -> 2*x + x*x*x + x*x + 1;
		  
		  double zeroPoint = bisection(function, xmin, xmax, epsilon);
		  System.out.println("Zero point is: " + zeroPoint);
		  }

	/**
	 * Performs a gready search (numercially) of the zero point.
	 * Caution1: if the function has several zero point inside the interval [xmin,xmax], the one closets to xmin will
	 * be returned.
	 * Caution 2: If there is no zero point inside the interval [xmin,xmax] the methods calculates 100000 iterations, 
	 * converging to xmin and will return a double close to xmin.
	 * 
	 * 
	 * @param function created with java.util.function.Function
	 * @param xmin, defines the left border.
	 * @param xmax defines the right border
	 * @param epsilon, defines the accuracy
	 * @return zero point
	 */
	public static double bisection(Function<Double, Double> function, double xmin, double xmax,
			double epsilon) {
	    double error = 1.0;
	    int count = 0;
	    double xMiddle = (xmin + xmax)/2.0;
	    while( error > epsilon & count < 100000) {
	        xMiddle = (xmin + xmax)/2.0;
	        if(function.apply(xMiddle) * function.apply(xmin)>0) {
	            xmin = xMiddle;
	        } else {
	            xmax = xMiddle;
	        }
	        count += 1;
	        error = Math.abs(function.apply(xMiddle));
	    }
	    
	    return xMiddle;
	}
	
	/**
	 * Calculates the zero point of a function of the form f(x) = x^2 +p*x + q.
	 * If the function ha no real root, the method will throw an exception.
	 * if the two roots are identical, the method will return an array with two entries (same entries)
	 * 
	 * @param p
	 * @param q
	 * @return double array with both zero points.
	 */
	public static double[] pqFormel(double p, double q) {
		if ( 0.25*p*p-q < 0) {
			throw new IllegalArgumentException("The equation x^2 + x*" + p + " + " + q + " has no real root");		
		}
			if(p< 0) {
			double root1 = q/(-p/2 + Math.sqrt(Math.pow(p/2,2) -q));
			double root2 = q/(-p/2 - Math.sqrt(Math.pow(p/2,2) -q));
			double[] bothRoots = {root1,root2};
			return bothRoots;
			} else {
			double root1 = -p/2 - Math.sqrt(Math.pow(p/2,2) -q);
			double root2 = -p/2 + Math.sqrt(Math.pow(p/2,2) -q);
			double[] bothRoots = {root1,root2};

			return bothRoots;
			}
		}
	    
	    
	}


