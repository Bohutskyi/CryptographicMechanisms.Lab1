# CryptographicMechanisms.Labs

## Getting Started

There are three conditions in which this application can be run:

* Get test execution results for predefined values:

```
java -jar labs-1.3-SNAPSHOT-jar-with-dependencies.jar
```
* Execute multiplication operation for your own input parameters in hex interpretation:

```
java -jar labs-1.3-SNAPSHOT-jar-with-dependencies.jar <First input>  * <Second input>
```

* Generate comparison statistics for developed LongNumber and built-in BigInteger operations using randomly generated inputs (size: 768 and 1024 bits):

```
java -jar labs-1.3-SNAPSHOT-jar-with-dependencies.jar Statistics
```

### Prerequisites

You need to use installation steps from [here](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/) and download "labs-1.3-SNAPSHOT-jar-with-dependencies.jar" file from CryptographicMechanisms.Labs/target/ folder.

### Files with main functionality
#### (Lab #1)

1. master/src/main/java/lab1/LongNumber.java - custom implementation for long arithmetic
2. master/src/main/java/lab1/test/AppLab1.java  & BenchmarksLab1.java - LongNumber algorithms performance tests and results comparison to (Java built-in) BigInteger ones 
3. master/src/main/java/lab1/test/StatResults - performance results
4. master/src/main/java/lab1/test/BigIntegerMulAlgorithms - BigInteger algorithms (package java.math)
5. master/src/main/java/lab1/test/BigDecimalMulAlgorithm - BigDecimal algorithms (package java.math)
6. master/src/main/java/lab1/test/LargeIntegerMulAlgorithm - LargeNumber algorithms (package org.jscience.mathematics.number)

#### (Lab #2)

1. master/src/main/java/lab2/Maurer.java - generates probable prime number with specified number of bits using Maurer algorithm
2. master/src/main/java/lab2/MillerRabinTest.java - Miller-Rabin primality test: an algorithm which determines whether a given number is prime
3. master/src/main/java/lab2/test/cwe/*.java - examples from list of common software security weaknesses for SecurityRundom Java built-in class (which is used in testing)
4. main/java/lab2/test/nist/NistTests.java - set of Statistical Test Suites for the Validation of Random Number Generators and Pseudo Random Number Generators for Cryptographic applied on customly developed Maurer generator

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [JMH](http://openjdk.java.net/projects/code-tools/jmh/) - Harness for building, running, and analysing benchmarks
* 
