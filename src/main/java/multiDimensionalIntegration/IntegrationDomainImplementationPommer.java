package multiDimensionalIntegration;


public class IntegrationDomainImplementationPommer implements IntegrationDomain {
	private final double lowerBoundX;
	private final double upperBoundX;
	private final double lowerBoundY;
	private final double upperBoundY;
	
	public IntegrationDomainImplementationPommer(double lowerBoundX, double upperBoundX, double lowerBoundY, double upperBoundY) {
		this.lowerBoundX = 	lowerBoundX;
		this.upperBoundX = 	upperBoundX;
		this.lowerBoundY = 	lowerBoundY;
		this.upperBoundY = 	upperBoundY;
		}

	@Override
	public double[] fromUnitCube(double[] parametersOnUnitCube) {
		double[] dom = { this.lowerBoundX + (this.upperBoundX - this.lowerBoundX)*Math.abs(parametersOnUnitCube[0]),
				this.lowerBoundY + (this.upperBoundY - this.lowerBoundY)*Math.abs(parametersOnUnitCube[1]) };
		return dom;	
	}

	@Override
	public int getDimension() {		
		return 2;
	}

	@Override
	public double getDeterminantOfDifferential(double[] parametersOnUnitCurve) {
		double det = (this.upperBoundX - this.lowerBoundX) *( this.upperBoundY - this.lowerBoundY);	
		return det;
	}
	
}
