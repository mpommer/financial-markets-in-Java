package BackwardAutomaticDifferentiation;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Class which performs the differentiation.
 * 
 * @author Marcel Pommer
 *
 */

public class RandomVariableDifferentiablelmplementation implements RandomVariableDifferentiable, GetRealizations {

	private enum Operator {
		SQUARED, SQRT, ADD, SUB, MULT, DIV, CHOOSE, EXP, VARIANCE, EXPECTATION, SAMPLEERROR, LOG
	}

	private static AtomicLong nextId = new AtomicLong();
	static double[] neutralAdd = { 0.0 };
	static double[] neutralMult = { 1.0 };
	private final static RandomVariable one = new RandomVariableDifferentiablelmplementation(neutralMult);
	private final static RandomVariable zero = new RandomVariableDifferentiablelmplementation(neutralAdd);


	private final double[] value;
	private final Operator operator;
	private final List<RandomVariableDifferentiablelmplementation> arguments;
	private final Long id;
	private boolean isDeterministic = false;
	

	private RandomVariableDifferentiablelmplementation(double[] RandomVariable, Operator operator,
			List<RandomVariableDifferentiablelmplementation> arguments) {
		super();
		boolean isDeterministic = true;
		for (int i = 0; i < RandomVariable.length; i++) {
			if (RandomVariable[0] != RandomVariable[i]) {
				isDeterministic = false;
			}
		}
		if (isDeterministic) {
			final double[] random = { RandomVariable[0] };
			this.value = random;
			this.isDeterministic = true;
		} else {
		this.value = RandomVariable;
		}


		this.operator = operator;
		this.arguments = arguments;
		this.id = nextId.getAndIncrement();
	}

	public RandomVariableDifferentiablelmplementation(double x) {
		this(new double[] { x }, null, null);

	}

	public RandomVariableDifferentiablelmplementation(double[] RandomVariable) {
		this(RandomVariable, null, null);
	}

	@Override
	public RandomVariableFactory getFactory() {
		final RandomVariableDifferentiableFactory factory = new RandomVariableDifferentiableFactoryImplementation();
		return factory;
	}

	@Override
	public RandomVariable expectation() {
		final RandomVariable ranNew1 = new RandomVariableImplementation(value);

		return new RandomVariableDifferentiablelmplementation(
				RandomVariableImplementation.getRealizations2(ranNew1.expectation()),
				Operator.EXPECTATION, List.of(this));
	}

	@Override
	public RandomVariable sampleError() {
		final RandomVariable ranNew1 = new RandomVariableImplementation(value);

		return new RandomVariableDifferentiablelmplementation(
				RandomVariableImplementation.getRealizations2(ranNew1.sampleError()), Operator.SAMPLEERROR, List.of(this));
	}

	@Override
	public RandomVariable variance() {
		final RandomVariable ranNew1 = new RandomVariableImplementation(value);

		return new RandomVariableDifferentiablelmplementation(
				RandomVariableImplementation.getRealizations2(ranNew1.variance()), Operator.VARIANCE, List.of(this));
	}

	@Override
	public RandomVariable squared() {
		final RandomVariable ranNew1 = new RandomVariableImplementation(value);

		return new RandomVariableDifferentiablelmplementation(RandomVariableImplementation.getRealizations2(ranNew1.squared()),
				Operator.SQUARED, List.of(this));
	}

	@Override
	public RandomVariable sqrt() {
		final RandomVariable ranNew1 = new RandomVariableImplementation(value);

		return new RandomVariableDifferentiablelmplementation(RandomVariableImplementation.getRealizations2(ranNew1.sqrt()),
				Operator.SQRT, List.of(this));
	}

	@Override
	public RandomVariable exp() {
		final RandomVariable ranNew1 = new RandomVariableImplementation(value);

		return new RandomVariableDifferentiablelmplementation(RandomVariableImplementation.getRealizations2(ranNew1.exp()),
				Operator.EXP, List.of(this));
	}

	@Override
	public RandomVariable log() {
		final RandomVariable ranNew1 = new RandomVariableImplementation(value);

		return new RandomVariableDifferentiablelmplementation(RandomVariableImplementation.getRealizations2(ranNew1.log()),
				Operator.LOG, List.of(this));
	}

	@Override
	public RandomVariable add(double x) {

		return add(new RandomVariableDifferentiablelmplementation(x));
	}

	@Override
	public RandomVariable add(RandomVariable x) {
		final RandomVariable ranNew1 = new RandomVariableImplementation(value);

		return new RandomVariableDifferentiablelmplementation(RandomVariableImplementation.getRealizations2(ranNew1.add(x)),
				Operator.ADD, List.of(this, (RandomVariableDifferentiablelmplementation) x));
	}

	@Override
	public RandomVariable sub(RandomVariable x) {
		final RandomVariable ranNew1 = new RandomVariableImplementation(value);

		return new RandomVariableDifferentiablelmplementation(RandomVariableImplementation.getRealizations2(ranNew1.sub(x)),
				Operator.SUB, List.of(this, (RandomVariableDifferentiablelmplementation) x));
	}

	@Override
	public RandomVariable mult(double x) {
		return mult(new RandomVariableDifferentiablelmplementation(x));
	}

	@Override
	public RandomVariable mult(RandomVariable x) {
		final RandomVariable ranNew1 = new RandomVariableImplementation(value);

		return new RandomVariableDifferentiablelmplementation(RandomVariableImplementation.getRealizations2(ranNew1.mult(x)),
				Operator.MULT, List.of(this, (RandomVariableDifferentiablelmplementation) x));
	}

	@Override
	public RandomVariable div(RandomVariable x) {
		final RandomVariable ranNew1 = new RandomVariableImplementation(value);

		return new RandomVariableDifferentiablelmplementation(RandomVariableImplementation.getRealizations2(ranNew1.div(x)),
				Operator.DIV, List.of(this, (RandomVariableDifferentiablelmplementation) x));
	}

	@Override
	public RandomVariable choose(RandomVariable valueIfNonNegative, RandomVariable valueIfNegative) {
		final RandomVariable ranNew1 = new RandomVariableImplementation(value);

		return new RandomVariableDifferentiablelmplementation(
				RandomVariableImplementation.getRealizations2(ranNew1.choose(valueIfNonNegative, valueIfNegative)),
				Operator.CHOOSE, List.of(this, (RandomVariableDifferentiablelmplementation) valueIfNonNegative,
						(RandomVariableDifferentiablelmplementation) valueIfNegative));
	}

	public Map<RandomVariableDifferentiable, RandomVariable> getDerivativeWithRespectTo() {
		// The map that will contain the derivatives x -> dy/dx // The map contains in
		// iteration m the values d F
		final Map<RandomVariableDifferentiable, RandomVariable> derivativesWithRespectTo = new HashMap<>();
		// Init with dy / dy = 1
		derivativesWithRespectTo.put(this, one);

		// This creates a set (queue) of objects, sorted ascending by their getID()
		// value (last = highest ID)
		final NavigableSet<RandomVariableDifferentiablelmplementation> nodesToProcess = new TreeSet<>(
				(o1, o2) -> o1.getID().compareTo(o2.getID()));

		// Add the root note
		nodesToProcess.add(this);

		// Walk down the tree, always removing the node with the highest id and adding
		// their arguments
		while (!nodesToProcess.isEmpty()) {

			// Get and remove the top most node.
			final RandomVariableDifferentiablelmplementation currentNode = nodesToProcess.pollLast();

			final List<RandomVariableDifferentiablelmplementation> currentNodeArguments = currentNode.getArguments();
			if (currentNodeArguments != null) {
				// Update the derivative as Di = Di + Dm * dxm / dxi (where Dm = dy/xm).
		
				propagateDerivativeToArguments(derivativesWithRespectTo, currentNode, currentNodeArguments);

				// Add all arguments to our queue of nodes we have to work on
				nodesToProcess.addAll(currentNode.getArguments());
			}
			
		}

	
		return derivativesWithRespectTo;
	}

	private void propagateDerivativeToArguments(Map<RandomVariableDifferentiable, RandomVariable> derivatives,
			RandomVariableDifferentiablelmplementation node, List<RandomVariableDifferentiablelmplementation> arguments) {

		final double[] valuesArgument0 = RandomVariableImplementation.getRealizations2(arguments.get(0));

		switch (node.getOperator()) {
		case SQUARED:
			derivatives.put(arguments.get(0), derivatives.getOrDefault(arguments.get(0), zero)
					.add(derivatives.get(node).mult(2.0).mult(arguments.get(0))));
			break;
		case SQRT:
			derivatives.put(arguments.get(0), derivatives.getOrDefault(arguments.get(0), zero)
					.add(derivatives.get(node).mult(0.5).div(arguments.get(0).sqrt())));
			break;
		case EXP:
			derivatives.put(arguments.get(0), derivatives.getOrDefault(arguments.get(0), zero)
					.add(derivatives.get(node).mult(arguments.get(0).exp())));
			break;
		case LOG:
			derivatives.put(arguments.get(0),
					derivatives.getOrDefault(arguments.get(0), zero).add(derivatives.get(node).div(arguments.get(0))));

			break;
		case ADD:
			derivatives.put(arguments.get(0),
					derivatives.getOrDefault(arguments.get(0), zero).add(derivatives.get(node).mult(one)));
			derivatives.put(arguments.get(1),
					derivatives.getOrDefault(arguments.get(1), zero).add(derivatives.get(node).mult(one)));
			break;
		case SUB:
			derivatives.put(arguments.get(0),
					derivatives.getOrDefault(arguments.get(0), zero).add(derivatives.get(node).mult(one)));
			derivatives.put(arguments.get(1),
					derivatives.getOrDefault(arguments.get(1), zero).sub(derivatives.get(node).mult(one)));
			break;
		case MULT:
			derivatives.put(arguments.get(0),
					derivatives.getOrDefault(arguments.get(0), zero).add(derivatives.get(node).mult(arguments.get(1))));

			derivatives.put(arguments.get(1),
					derivatives.getOrDefault(arguments.get(1), zero).add(derivatives.get(node).mult(arguments.get(0))));

			break;
		case DIV: 
			derivatives.put(arguments.get(0),
					derivatives.getOrDefault(arguments.get(0), zero).add(derivatives.get(node).div(arguments.get(1))));
			derivatives.put(arguments.get(1),
					derivatives.getOrDefault(arguments.get(1), zero)
							.add(derivatives.get(node).mult(arguments.get(0).mult(-1.0))
									.div(arguments.get(1).squared())));
			break;
		case EXPECTATION:
			derivatives.put(arguments.get(0),
					derivatives.getOrDefault(arguments.get(0), zero).add(derivatives.get(node).expectation()));

	

			/*
			 *  alternative derivation of derivative expectation
			 */

//			derivatives.put(arguments.get(0), derivatives.getOrDefault(arguments.get(0), zero)
//					.add(derivatives.get(node).mult(1.0 / valuesArgument0.length)));


			break;
		case VARIANCE:
			derivatives.put(arguments.get(0),
					derivatives.getOrDefault(arguments.get(0), zero)
							.add(derivatives.get(node).mult(arguments.get(0).sub(arguments.get(0).expectation())
									.mult(2.0 / valuesArgument0.length))));

			/*
			 *  alternative derivation of derivative variance
			 */
			
//			derivatives.put(arguments.get(0),
//					derivatives.getOrDefault(arguments.get(0), zero)
//							.add(arguments.get(0)
//									.sub(arguments.get(0).expectation()
//											.mult((2.0 * valuesArgument0.length - 1.0) / valuesArgument0.length))
//									.mult(2.0 / valuesArgument0.length)));

			break;
		case SAMPLEERROR:
			derivatives.put(arguments.get(0), derivatives.getOrDefault(arguments.get(0), zero).add(derivatives.get(node)
					.mult(0.5 / Math.sqrt(valuesArgument0.length)).div(arguments.get(0).variance().sqrt())
					.mult(arguments.get(0).sub(arguments.get(0).expectation()).mult(2.0 / valuesArgument0.length))));
			
			/*
			 * 
			 * alternative derivation of derivative sample error
			 */


//			derivatives.put(arguments.get(0),
//					derivatives.getOrDefault(arguments.get(0), zero).add(arguments.get(0)
//							.sub(arguments.get(0).expectation()
//									.mult((2.0 * valuesArgument0.length - 1.0) / valuesArgument0.length))
//							.mult(2.0 / valuesArgument0.length).mult(0.5).div(arguments.get(0).variance().sqrt())));
			break;

		case CHOOSE:
			/*			  
			 * note: finite difference approach, the epsilon is 0.0001;
			 * 			  
			 */			
			
			final double eps = 0.0001;
			derivatives.put(arguments.get(0),
					derivatives.getOrDefault(arguments.get(0), zero)
							.add(derivatives.get(node).mult(arguments.get(0).squared().add(-eps * eps).choose(zero,
									arguments.get(1).sub(arguments.get(2)).mult(0.5 / eps)))));
			derivatives.put(arguments.get(1), derivatives.getOrDefault(arguments.get(1), zero)
					.add(derivatives.get(node).mult(arguments.get(0).choose(one, zero))));
			derivatives.put(arguments.get(2), derivatives.getOrDefault(arguments.get(2), zero)
					.add(derivatives.get(node).mult(arguments.get(0).choose(zero, one))));
			break;
		}
	}

	@Override
	public RandomVariable getDerivativeWithRespectTo(RandomVariableDifferentiable x) {

		return new RandomVariableImplementation(
				RandomVariableImplementation.getRealizations2(getDerivativeWithRespectTo().getOrDefault(x, zero)));
	}

	@Override
	public Double asFloatingPoint() {
		/**
		 *note, in case the method is called, but the object is non deterministic, a warning is created, BUT the returned
		 *value is the first value of the vector (random variable)
		 *throwing an exception leaded to some complications with the test!
		 *alternatively one could return the average (see comment below)
		 * 
		 */

		if (!isDeterministic) {
			System.out.println("Warning: Constant created from random variable, which is not constant");
//			return new RandomVariableDifferentiablelmplementation(value).expectation();
		}

		return value[0];
	}

	Long getID() {
		return id.longValue();
	}

	public List<RandomVariableDifferentiablelmplementation> getArguments() {
		return arguments;
	}

	public Operator getOperator() {
		return operator;
	}

	public static boolean isNonZero(double[] RandomVariable) {
		for (int i = 0; i < RandomVariable.length; i++) {
			if (RandomVariable[i] == 0) {
				return false;
			}
		}
		return true;
	}
/**
 * Static method to return the array of realizations. This is only an alternative to the implemented
 * method getRealizations (not static).
 * 
 * @param x
 * @return Array of realizations.
 */
	public static double[] getRealizations2(RandomVariable x) {
		if (x instanceof RandomVariableDifferentiablelmplementation) {
			return ((RandomVariableDifferentiablelmplementation) x).value;
		}
		return null;
	}
	
	/**
	 * check wether a RandomVariable is deterministic (boolean, returns true if true)
	 * 
	 * @param Random variable
	 * @return boolean
	 */

	public boolean isDeterministic(RandomVariable value) {
		final double[] values = getRealizations2(value);

		for (int i = 0; i < values.length; i++) {
			if (values[0] != values[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public double[] getRealizations() {
		return value;
	}

}
