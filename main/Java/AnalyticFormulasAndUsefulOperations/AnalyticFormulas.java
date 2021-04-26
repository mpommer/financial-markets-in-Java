package AnalyticFormulasAndUsefulOperations;

import org.apache.commons.math3.distribution.NormalDistribution;

public class AnalyticFormulas {
	
	public static void main(String[] args) {
		final double forwardRate = 0.05;
		final double payoffUnit = 0.9;
		final double volatility = 0.3;
		final double maturity = 2.0;
		final double periodLength = 0.5;
		final double strike = 0.06;
		final double analyticDelta = blackScholesDigitalOptionDelta(forwardRate,0.0,volatility,maturity,strike)*payoffUnit*periodLength;
		final double analyticPrice = blackScholesDigitalOptionValue(forwardRate,0.0,volatility,maturity,strike)*payoffUnit*periodLength;
		System.out.println("test:" + analyticPrice);
		System.out.println("test:" + analyticDelta);

		
	}
	
	public static double blackScholesDigitalOptionDelta(
			final double initialValue,
			final double interestRate,
			final double volatility,
			final double optionMaturity,
			final double optionStrike)
	{
		if(optionStrike <= 0.0 || optionMaturity <= 0.0){return 0.0;		}
		else{
			final double dPlus = (Math.log(initialValue / optionStrike) + (interestRate + 0.5 * volatility * volatility) * optionMaturity) / (volatility * Math.sqrt(optionMaturity));
			final double dMinus = dPlus - volatility * Math.sqrt(optionMaturity);

			final double delta = Math.exp(-interestRate * optionMaturity) * Math.exp(-0.5*dMinus*dMinus) / (Math.sqrt(2.0 * Math.PI * optionMaturity) * initialValue * volatility);

			return delta;
		}
}
	
	public static double blackScholesDigitalOptionValue(
			final double initialValue,
			final double interestRate,
			final double volatility,
			final double optionMaturity,
			final double optionStrike)
	{
		if(optionStrike <= 0.0){
			// The Black-Scholes model does not consider it being an option
			return 1.0;
		}
		else
		{
			NormalDistribution normalDis = new NormalDistribution();
			// Calculate analytic value
			final double dPlus = (Math.log(initialValue / optionStrike) + (interestRate + 0.5 * volatility * volatility) * optionMaturity) / (volatility * Math.sqrt(optionMaturity));
			final double dMinus = dPlus - volatility * Math.sqrt(optionMaturity);

			final double valueAnalytic = Math.exp(- interestRate * optionMaturity) * normalDis.cumulativeProbability(dMinus);

			return valueAnalytic;
		}
	}
	
	
	public static double bachelierOptionValue(double forward, double optionStrike,double volatility,
            double optionMaturity, double periodLength, double discountFactor,double nominal) {
		double dPlus = 0.0;
        if(forward == optionStrike) {return volatility * Math.sqrt(optionMaturity / Math.PI / 2.0);}
        else {
        	if(optionMaturity > 0){
        		dPlus = (forward - optionStrike) / (volatility * Math.sqrt(optionMaturity));
        	}else {
                dPlus = Double.MAX_VALUE;
        	}
        	
    		NormalDistribution normalDis = new NormalDistribution();
   
            double valueAnalytic = periodLength * discountFactor* ((forward - optionStrike) * normalDis.cumulativeProbability(dPlus)+
            		volatility * Math.sqrt(optionMaturity) * normalDis.density(dPlus));

            return nominal * valueAnalytic;
	}
}
}