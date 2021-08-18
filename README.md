# financial-markets-in-Java

Collections of useful operations for financial markets in Java.

1) Analytic formulas and useful operations on matrices and vectors
- useful operations on matrices and vector: Enables often used operation like append, cbind, rbind or min max on vectors and matrices. Furthermore the code enables
more complex operations like transpose and inverse.
- Analytic formulas in the context of financial markets: Enables for example the use of the black scholes and the bachelier formula for options. 
Furthermore I added a test class in order to demonstrate how to use the methods.
- Bisection: Finds the zero point of a function. Furthermore contains a method to calculate the pq formula in a numerically stable way.
- Standard normal distribution: Example and visualization of how to use the random number generator for standard normal distributed variables.

2) Automatic backward differentiation
- The class performs the Automatic backward differentiation algorithm, i.e. differentiate equations. The class RandomVariable enables the use of vectors. 
Furthermore I added a test class in order to demonstrate how to use the methods in the context of calculating derivatives and also by calcualting 
option deltas using the derivaive.

3) Binomial Model
- Normal model: risk neutral probabilitiy q = (1 + interestRate - decreaseIfDown)/ (increaseIfUp - decreaseIfDown). Two Implementations, the first performs a 
Monte Carlo calculation of the price, the second (smart) calculates all probabilities and calcualtes the price with probability times price.  
- log model: risk neutral probabilitiy q = (Math.log(decreaseIfDown)/ (-Math.log(increaseIfUp) + Math.log(decreaseIfDown))). Two Implementations, the first performs a 
Monte Carlo calculation of the price, the second (smart) calculates all probabilities and calculates the price with probability times price. 
- European Option: Uses the binomial model smart to calculate the value of an european option. An example is appended with a call option at the money 
(f(x) = max(x - initial value,0)).
- American Option: Uses the binomial model smart to calculate the value of an american option. An example is appended with a put option at the money 
(f(x) = max(initial value - x,0)).

4) Brownian Motion
- generates a sequence of standard normal random numbers as type of Randomvarible in order to perfom operations with them. This class can be used for term structure models (coming)

5) Random Numbers:
Project to create random numbers
- One dimensional: creates random numbers using the LCG or a van der corput sequence (low discrepancy sequence).
- Multi dimensional: creates multidimensional random numbers using LCG/mersenne twister or a halton sequence (low discrepancy sequence).
This class should be used for integration.

6) Integration:
- Project to perfom integration: support one and multidimensional integration.
