# CryptographicMechanisms.Labs

## Getting Started

There are three conditions in which this application can be run:

* Get test execution results for predefined values:

```
java -jar labs-1.2-SNAPSHOT-jar-with-dependencies.jar
```
* Execute operations (+, -, *, **(Karatsuba multiplication), /, ^ (with mod) and GCD) for your own input parameters in hex interpretation:

```
java -jar labs-1.2-SNAPSHOT-jar-with-dependencies.jar <First input> <operation: + OR - OR * OR ** OR / OR GCD> <Second input>
java -jar labs-1.2-SNAPSHOT-jar-with-dependencies.jar <First input> ^ <Second input> mod <Third input>

```

* Generate comperison statistics for custom LongNumber and built-in BigInteger operations:

```
java -jar labs-1.2-SNAPSHOT-jar-with-dependencies.jar Statistics
```

### Prerequisites

You need to use installation steps from [here](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/) and download "labs-1.2-SNAPSHOT-jar-with-dependencies.jar" file from CryptographicMechanisms.Labs/target/ folder.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [JMH](http://openjdk.java.net/projects/code-tools/jmh/) - Harness for building, running, and analysing benchmarks
