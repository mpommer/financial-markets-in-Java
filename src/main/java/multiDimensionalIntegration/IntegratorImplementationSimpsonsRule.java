package multiDimensionalIntegration;


public class IntegratorImplementationSimpsonsRule implements Integrator {
	private final int dimension;
	private final int numberOfPoints;
	private final double[][] boundaries;
	
	public IntegratorImplementationSimpsonsRule(int dimension,int numberOfPoints, double[][] boundaries) {
		this.dimension = dimension;
		this.numberOfPoints = numberOfPoints;
		this.boundaries = boundaries;
	}

	@Override
	public double integrate(Integrand integrand, IntegrationDomain integrationDomain) {
		double sum = 0.0;
		IntegrationDomainSimpsonsRule integrationDomanSimpson = new IntegrationDomainSimpsonsRule(dimension, numberOfPoints,
				boundaries);
		double[][] numberGridSimpson = integrationDomanSimpson.numberGrid();
		double[][] evaluationPoints = integrationDomanSimpson.evaluationPoints();
		
		double h = 1.0;
		for(int i =0;i<dimension;i++) {
			h *= (boundaries[i][1] - boundaries[i][0]);
		}
		h/=Math.pow(3, dimension)*Math.pow(numberOfPoints, dimension);
		
		
		for(int i = 0; i<numberOfPoints;i++) {
			for(int j = 0; j<numberOfPoints;j++) {
				double[] point = {evaluationPoints[0][i],evaluationPoints[1][j]};
				sum += numberGridSimpson[i][j] * integrand.value(point);
			}
		}

		return sum*h;
	}

}
