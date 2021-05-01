package AnalyticFormulasAndUsefulOperations;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.finmath.plots.Plots;

public class StandardNormalDistributionVisulization {

	public static void main(String[] args) {
		long seed = 281516L; 
		int numberOfPseudoRandomNumbers = 1000000;
		RandomNumberGenerator ran = new RandomNumberGenerator(numberOfPseudoRandomNumbers, seed);
		long[] longs = ran.getRandomNumberSequenceLong();
		double[] doubles = ran.getRandomNumberSequenceDouble();
		double[] standardNormal = ran.getRandomNumberSequenceStandardNormal();
		
		double[][] hist = UsefullOperationsVectorsMatrixes.histogramm(standardNormal, -4, 4, 150);

		
		final List<Double> frequency = new ArrayList<Double>();
		final List<Double> x = new ArrayList<Double>();
		
		
		for(int i = 0; i<hist[0].length-1; i++) {
			frequency.add(hist[1][i]);
			x.add((hist[0][i]+ hist[0][i+1])/2.0);
		}
		
 		Plots.createPlotScatter(x, frequency,-3,3, 5)
		.setTitle("Distribution according to random number generator")
		.setXAxisLabel("x")
		.setYAxisLabel("frequency")
		.setYAxisNumberFormat(null).show();
		
	}

}
