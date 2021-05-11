package multiDimensionalIntegration;

/**
 * implements the integration domain, although the methods are not needed,
 * since the points are equidistributed
 * @author marce
 *
 */
public class IntegrationDomainSimpsonsRule implements IntegrationDomain {
	private final int dimension;
	private final int numberOfPoints;
	private final double[][] boundaries;
	
	public IntegrationDomainSimpsonsRule(int dimension, int numberOfPoints, double[][] boundaries) {
		this.dimension = dimension;
		this.numberOfPoints = numberOfPoints;
		this.boundaries = boundaries;
		}

	
	public double[][] evaluationPoints(){
		double[][] evaluationPoints = new double[dimension][numberOfPoints];
		
		for(int dim = 0; dim<dimension;dim++) {
			for(int j = 0; j<numberOfPoints;j++) {
				evaluationPoints[dim][j] = boundaries[dim][0] + boundaries[dim][1]*(double)j/(double)numberOfPoints;
			}
		}		
		return evaluationPoints;
	}
	
	
	public double[][] numberGrid(){

		
		double[][] matrixMulti = new double[numberOfPoints][numberOfPoints];
		for(int j = 0; j<numberOfPoints;j++) {
			for(int i = 0; i<numberOfPoints;i++) {
				if((j==0 & i==0) |j==(numberOfPoints-1) & i==(numberOfPoints-1)) {
				matrixMulti[j][i] = 1;
				} else if(j==0  |j==(numberOfPoints-1)) {
					if(i % 2==0) {
						matrixMulti[j][i] = 2;
					} else {
						matrixMulti[j][i] = 4;
					}
				} else if(j!=0  & j!=(numberOfPoints-1) & (j%2==0)) {
					if(i % 2==0) {
						matrixMulti[j][i] = 4;
					} else {
						matrixMulti[j][i] = 8;
					}					
				} else if(j!=0  & j!=(numberOfPoints-1) & (j%2!=0)) {
					if(i % 2==0) {
						matrixMulti[j][i] = 8;
					} else {
						matrixMulti[j][i] = 16;
					}		
				}
			}
		}
		for(int j = 0; j<numberOfPoints; j++) {
			if(j==0) {
				matrixMulti[j][0] = 1;
				matrixMulti[j][numberOfPoints-1] = 1;
			} else if(j%2==0) {
			matrixMulti[j][0] = 2;
			matrixMulti[j][numberOfPoints-1] = 2;
			} else {
				matrixMulti[j][0] = 4;
				matrixMulti[j][numberOfPoints-1] = 4;
			}
		}
		
		return matrixMulti;
	}
	
	
	@Override
	public double[] fromUnitCube(double[] parametersOnUnitCube) {		
		return null;	
	}

	@Override
	public int getDimension() {		
		return dimension;
	}

	@Override
	public double getDeterminantOfDifferential(double[] parametersOnUnitCurve) {
		
		return 0;
	}

}
