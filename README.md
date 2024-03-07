# Apache Beam Java Demo

## Overview

The Apache Beam Java Demo is a collection of Java-based examples demonstrating the use of the Apache Beam SDK to process and transform data streams and batch data. This project includes examples for basic operations such as reading from and writing to various sources, applying simple transformations, and more complex use cases like word count, database interactions, and custom row processing.

## License

This project is licensed under the Apache License, Version 2.0 (LICENSE-APACHE) and the MIT license (LICENSE-MIT), at your option.

## Prerequisites

Before you begin, ensure you have met the following requirements:
- Java JDK 11 or later
- Maven 3.6.0 or later
- Apache Beam SDK 2.x for Java
- Access to a MySQL database for some of the examples

## Project Structure

This project consists of the following main classes, each demonstrating different aspects of the Apache Beam SDK:

- `App1`: Demonstrates a simple Beam pipeline that creates a PCollection of strings, applies a map transformation to print each element, and runs the pipeline.

- `App2`: Shows how to read text from a file, apply transformations like flatMap, filter, and count to perform a word count operation, and then print the results.

- `App3`: Utilizes JdbcIO to read from a MySQL database, converts the result set to JSON, and prints each record.

- `App4`: Demonstrates the use of R2DBC for reactive database access within a Beam pipeline, including converting database rows to custom objects and printing them.

## Getting Started

To run these examples, clone the repository and navigate to the project directory:


### Compiling the Project

Compile the project using Maven:


### Running the Examples

To run an example, use the `mvn exec:java` command with the `-Dexec.mainClass` option set to the class you want to execute. For instance, to run `App1`, you would use:

mvn exec:java -Dexec.mainClass=com.example.App1



Replace `App1` with `App2`, `App3`, or `App4` to run the respective examples.

## Contributing

Contributions to this project are welcome. Please follow the standard fork and pull request workflow.

## Contact

If you have any questions or feedback, please open an issue in the project repository.
