// Gavin Dennis
// CS 3130 - Design and Analysis of Algorithms
// Dr. He
// Due 2024 December 15 23:59

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 

    Efficient Fibonacci Algorithm Analysis by Gavin Dennis

    Takes command-line arguments for input, then calculates the corresponding fibonacci numbers
    using two algorithms:
        * The standard recursive algorithm that is taught in 2000 level CS classes
        * The more efficient dynamic programming approach which removes redundancy when computing

    What to input for command-line arguments:
        * arg1: the nth fibonacci number to find for the first algorithm
        * arg2: the nth fibonacci number to find for the second algorithm

 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import java.math.BigInteger;  // used for very large number evaluations
import java.lang.Integer;  // for 'parseInt'

public class App {

    public static void cPrint(String message) {
        /* C-style print function for easier message output */
        System.out.println(message);
    }

    public static long standardAlgorithm(int n) {
        /* Uses standard recursive algorithm to compute fibonacci number
        prints out the number and computing time */

        long result;

        // make sure argument is a positive integer
        if (n < 0) {
            cPrint("ERROR: Argument must be a non-negative integer. Aborting program. \n");
            System.exit(1);
        }
        // fibo for n = 0
        else if (n == 0) {
            result = 0;
            return result;
        }
        // fibo for n = 1
        else if (n == 1) { 
            result = 1;
            return result; 
        }
        // fibo for n > 1
        result = standardAlgorithm(n-1) + standardAlgorithm(n-2); 
        return result;
    }

    public static BigInteger efficientAlgorithm(int n) {
        /* Uses dynamic programming algorithm to compute fibonacci number
        prints out the number and computing time */

        // instantiate the array with all elements set to -1 to represent unknown values
        BigInteger [] known = new BigInteger[n + 1];
        for (int i = 0; i < known.length; i++) {
            known[i] = BigInteger.valueOf(-1);
        }

        // use array to speed up computation time with computeEfficientAlgorithm
        return computeEfficientAlgorithm(n, known);

    }

    public static BigInteger computeEfficientAlgorithm(int n, BigInteger[] known) {
        /* used to compute the fibonacci numbers without redundant computations
        known values are returned while newly computed values are added to the array */

        // make sure argument is a positive integer
        if (n < 0) {
            cPrint("ERROR: Argument must be a non-negative integer. Aborting program. \n");
            System.exit(1);
        }
        // fibo for n = 0
        else if (n == 0) {
            known[0] = BigInteger.ZERO;
            return known[0];
        }
        // fibo for n = 1
        else if (n == 1) { 
            known[1] = BigInteger.ONE;
            return known[1];
        } 
        // if the value is known already, return it
        if (!known[n].equals(BigInteger.valueOf(-1))) {
            return known[n];
        }
        // otherwise, find it and add it to the array
        else {
            known[n] = computeEfficientAlgorithm(n-1, known).add(computeEfficientAlgorithm(n-2, known));
            return known[n];
        }
    }

    public static void evaluateAlgorithms(int arg1, int arg2) {
        /* uses command-line arguments to compute fibonacci numbers
        with the algorithms while concurrently measuring the computing duration */

        long start, end, duration;  // used to measure time duration of computations
        long standardResult;
        BigInteger efficientResult;

        // evaluate the standard algorithm
        cPrint("Evaluating algorithms: Standard Algorithm");
        start = System.currentTimeMillis();
        standardResult = standardAlgorithm(arg1);
        end = System.currentTimeMillis();
        duration = end-start;
        // print the results
        cPrint("Fibonacci number " + arg1 + ": " + standardResult);
        cPrint("Time duration of standard algorithm: " + duration + "ms\n");

        // evaluate the efficient algorithm
        cPrint("Evaluating algorithms: Efficient Algorithm");
        start = System.currentTimeMillis();
        efficientResult = efficientAlgorithm(arg2);
        end = System.currentTimeMillis();
        duration = end-start;
        // print the results
        cPrint("Fibonacci number " + arg2 + ": " + efficientResult);
        cPrint("Time duration of efficient algorithm: " + duration + "ms\n");
    }


    public static void main(String[] args) throws Exception {
        // print message to user explaining how to use the program
        cPrint(
            "Efficient Fibonacci Algorithm Analysis by Gavin Dennis." + "\n\n" +
            "Takes command-line arguments for input, then calculates the corresponding fibonacci numbers\n" +
            "using two algorithms: \n" + 
            "\t* The standard recursive algorithm that is taught in 2000 level CS classes \n" +
            "\t* The more efficient dynamic programming approach which removes redundancy when computing \n\n" + 
            "What to input for command-line arguments: \n" + 
            "\t* arg1: the nth fibonacci number to find for the first algorithm \n" +
            "\t* arg2: the nth fibonacci number to find for the second algorithm \n"
        );

        // declare variables for command-line arguments
        int arg1 = 0, arg2 = 0;  // used to find n'th fibonacci number for algorithms 1 and 2

        // get command-line arguments
        // if there's not enough commandline arguments set default args
        if (args.length < 2) {
            arg1 = 20;
            arg2 = 20;
        }
        // too many commandline arguments - abort the program
        else if (args.length > 2) {
            cPrint(
                "ERROR: Too many arguments. Two integers are needed (e.g. 15 67). \n" +
                "Aborting program. \n"
            );
            System.exit(1);
        }
        // the perfect amount - parse the strings into intgers
        else {
            try {
                arg1 = Integer.parseInt(args[0]);
                arg2 = Integer.parseInt(args[1]);
            }
            catch(Exception e) {
                cPrint("ERROR: Couldn't parse commandline arguments. \n" + "Aborting program. \n");
                System.exit(1);
            }
        }

        // evaluate the fibonacci algorithms
        evaluateAlgorithms(arg1, arg2);
    }
}
