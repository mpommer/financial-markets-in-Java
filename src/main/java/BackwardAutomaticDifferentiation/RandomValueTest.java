package BackwardAutomaticDifferentiation;



import BackwardAutomaticDifferentiation.RandomVariableFactory;

import BackwardAutomaticDifferentiation.RandomVariableFactoryImplementation;
import BackwardAutomaticDifferentiation.RandomVariableImplementation;

class RandomValueTest {

	
	void test() {
		final RandomVariableFactory factory = new RandomVariableFactoryImplementation();
		final double a = 2.0;
		final double[] d = { -0.05, 1.0, 1.0 };
		final double[] b = { -0.5, 2, 3 };
		final double[] c = { 0, 2, 5 };
		final RandomVariable zero = factory.zero();
		final RandomVariable one = factory.one();
		final RandomVariable constant = factory.fromConstant(a);
		final RandomVariable x = factory.fromArray(b);
		final RandomVariable y = factory.fromArray(c);
		final RandomVariable z = factory.fromArray(d);

		final double[] value = c;

		final double[] vector = RandomVariableImplementation.getRealizations2(x.sub(y));
				
		final double[] vector2 = RandomVariableImplementation.getRealizations2(y.add(constant).expectation());


		for (int i = 0; i < vector.length; i++) {
			System.out.println(vector[i]);
		}



		// Discount value
//		final RandomVariable value = payoff.mult(payoffUnit).expectation();
//		final double[] vector3 = RandomVariableImplementation.getRealizations2(value);
//		System.out.println(vector3[0]);

	}

}
