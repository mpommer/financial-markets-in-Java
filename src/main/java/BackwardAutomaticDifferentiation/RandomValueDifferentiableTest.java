package BackwardAutomaticDifferentiation;


import java.util.function.Function;


class RandomValueDifferentiableTest {
	  public static void main(String[] args) {
		  test();

		  }
	  

	public static void test() {
		final RandomVariableDifferentiableFactory factory = new RandomVariableDifferentiableFactoryImplementation();
		final double a = 2.0;
		final double[] d = { -0.05, 1.0, 1.0 };
		final double[] b = { -0.5, 4, 3 };
		final double[] c = { 1, 2, 5 };
		final double[] b1 = { 2, 3, 2 };
		final double[] c1 = { 4, 6, 4 };
		
		final double[] e = { 1, 25, 4 };
		final RandomVariableDifferentiable zero = factory.zero();
		final RandomVariableDifferentiable one = factory.one();
		final RandomVariableDifferentiable constant = factory.fromConstant(a);
		final RandomVariableDifferentiable x = factory.fromArray(b);
		final RandomVariableDifferentiable y = factory.fromArray(c);
		final RandomVariableDifferentiable z = factory.fromArray(d);
		final RandomVariableDifferentiable sqrt = factory.fromArray(e);
		final RandomVariableDifferentiable y_tilde = (RandomVariableDifferentiable) x.mult(y);
		
		final RandomVariableDifferentiable x1 = factory.fromArray(b1);
		final RandomVariableDifferentiable y_ = factory.fromArray(c1);

		final Function<RandomVariable, RandomVariable> function = f -> f.expectation().add(y);
		final Function<RandomVariable, RandomVariable> function2 = f -> f.add(y.squared());
		final Function<RandomVariable, RandomVariable> function3 = f -> f.div(y);
		final Function<RandomVariable, RandomVariable> function4 = f -> f.sub(y);
		final Function<RandomVariable, RandomVariable> function5 = f -> f.mult(y);
		final Function<RandomVariable, RandomVariable> function6 = f -> f.sqrt();
		final Function<RandomVariable, RandomVariable> function7 = f -> f.log();
		final Function<RandomVariable, RandomVariable> function8 = f -> f.expectation();

		final RandomVariableDifferentiable y1 = (RandomVariableDifferentiable) function.apply(x);
		final RandomVariable derivative = y1.getDerivativeWithRespectTo(x);
		final double[] derivativeArray1 = RandomVariableImplementation.getRealizations2(derivative);

		System.out.println("Deriv expectation(x) + y, nach y:");
		for (int i = 0; i < derivativeArray1.length; i++) {
			System.out.println(derivativeArray1[i]);
		}

		final RandomVariableDifferentiable y2 = (RandomVariableDifferentiable) function.apply(x);
		final RandomVariable derivative2 = y2.getDerivativeWithRespectTo(y);
		final double[] derivativeArray2 = RandomVariableImplementation.getRealizations2(derivative2);

		System.out.println("Derive expectation(f) + y nach y:");
		for (int i = 0; i < derivativeArray2.length; i++) {
			System.out.println(derivativeArray2[i]);
		}

		final RandomVariableDifferentiable y3 = (RandomVariableDifferentiable) function2.apply(x);
		final RandomVariable derivative3 = y3.getDerivativeWithRespectTo(x);
		final double[] derivativeArray3 = RandomVariableImplementation.getRealizations2(derivative3);

		System.out.println("x +y^2 nach x:");
		for (int i = 0; i < derivativeArray3.length; i++) {
			System.out.println(derivativeArray3[i]);
		}


		final RandomVariableDifferentiable y4 = (RandomVariableDifferentiable) function2.apply(x);
		final RandomVariable derivative4 = y4.getDerivativeWithRespectTo(y);
		final double[] derivativeArray4 = RandomVariableImplementation.getRealizations2(derivative4);

		System.out.println("x +y^2 nach y:");
		for (int i = 0; i < derivativeArray4.length; i++) {
			System.out.println(derivativeArray4[i]);
		}

		final RandomVariableDifferentiable y5 = (RandomVariableDifferentiable) function3.apply(x);
		final RandomVariable derivative5 = y5.getDerivativeWithRespectTo(x);
		final double[] derivativeArray5 = RandomVariableImplementation.getRealizations2(derivative5);

		System.out.println("x/y nach x:");
		System.out.println("--> erwarte 1/y = {1,0.5,1/5}");

		for (int i = 0; i < derivativeArray5.length; i++) {
			System.out.println(derivativeArray5[i]);
		}

		final RandomVariableDifferentiable y6 = (RandomVariableDifferentiable) function3.apply(x);
		final RandomVariable derivative6 = y6.getDerivativeWithRespectTo(y);
		final double[] derivativeArray6 = RandomVariableImplementation.getRealizations2(derivative6);

		System.out.println("x/y nach y:");
		System.out.println("--> erwarte x/y^2 *-1 = {0.5,-1,-3/25}");
		for (int i = 0; i < derivativeArray6.length; i++) {
			System.out.println(derivativeArray6[i]);
		}

		final RandomVariableDifferentiable y7 = (RandomVariableDifferentiable) function4.apply(x);
		final RandomVariable derivative7 = y7.getDerivativeWithRespectTo(y);
		final double[] derivativeArray7 = RandomVariableImplementation.getRealizations2(derivative7);

		System.out.println("x-y nach y:");
		System.out.println("--> erwarte -y = {-1}");
		for (int i = 0; i < derivativeArray7.length; i++) {
			System.out.println(derivativeArray7[i]);
		}

		final RandomVariableDifferentiable y8 = (RandomVariableDifferentiable) function5.apply(x);
		final RandomVariable derivative8 = y8.getDerivativeWithRespectTo(y);
		final double[] derivativeArray8 = RandomVariableImplementation.getRealizations2(derivative8);

		System.out.println("x*y nach y:");
		System.out.println("--> erwarte x = { -0.5, 4, 3 }");
		for (int i = 0; i < derivativeArray8.length; i++) {
			System.out.println(derivativeArray8[i]);
		}

		final RandomVariableDifferentiable y9 = (RandomVariableDifferentiable) function6.apply(sqrt);
		final RandomVariable derivative9 = y9.getDerivativeWithRespectTo(sqrt);
		final double[] derivativeArray9 = RandomVariableImplementation.getRealizations2(derivative9);

		System.out.println("sqrt(x) nach x:");
		System.out.println("--> erwarte 0.5*(1/sqrt(x)) = { 0.5, 1/10, 1/4 }");
		for (int i = 0; i < derivativeArray9.length; i++) {
			System.out.println(derivativeArray9[i]);
		}

		final RandomVariableDifferentiable y10 = (RandomVariableDifferentiable) function7.apply(sqrt);
		final RandomVariable derivative10 = y10.getDerivativeWithRespectTo(sqrt);
		final double[] derivativeArray10 = RandomVariableImplementation.getRealizations2(derivative10);

		System.out.println("log(sqrt) nach sqrt:");
		System.out.println("--> erwarte 1/sqrt = { 1, 1/25, 1/4 }");
		for (int i = 0; i < derivativeArray10.length; i++) {
			System.out.println(derivativeArray10[i]);
		}

		final RandomVariableDifferentiable y11 = (RandomVariableDifferentiable) function8.apply(y_tilde);
		final RandomVariable derivative11 = y11.getDerivativeWithRespectTo(y_tilde);
		final double[] derivativeArray11 = RandomVariableImplementation.getRealizations2(derivative11);

		System.out.println("E(y_tilde)");
		System.out.println("--> erwarte 1/length = { 1/3}");
		for (int i = 0; i < derivativeArray11.length; i++) {
			System.out.println(derivativeArray11[i]);
		}
		
		
		RandomVariableDifferentiable y12 = (RandomVariableDifferentiable) x1.div(y_);
		final RandomVariable derivative12 = y12.getDerivativeWithRespectTo(x1);
		final double[] derivativeArray12 = RandomVariableImplementation.getRealizations2(derivative12);

		System.out.println("E(y_tilde)");
		System.out.println("--> erwarte 1/length = { 1/3}");
		for (int i = 0; i < derivativeArray12.length; i++) {
			System.out.println(derivativeArray12[i]);
		}
		


	}

}
