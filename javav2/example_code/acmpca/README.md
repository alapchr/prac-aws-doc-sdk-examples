# AWS Private Certificate Authority Java code examples

This README discusses how to run the Java code examples for AWS Private Certificate Authority (PCA).

## Running the AWS Private Certificate Authority Java files

**IMPORTANT**

The Java code examples perform AWS operations for the account and AWS Region for which you've specified credentials, and you may incur AWS service charges by running them. See the [AWS Pricing page](https://aws.amazon.com/pricing/) for details about the charges you can expect for a given service and operation.

Some of these examples perform *destructive* operations on AWS resources, such as deleting a certificate authority. **Be very careful** when running an operation that deletes or modifies AWS resources in your account. It's best to create separate test-only resources when experimenting with these examples.

## Prerequisites

You must have an AWS account, and have your default credentials and AWS Region configured as described in the [AWS Tools and SDKs Shared Configuration and Credentials Reference Guide](https://docs.aws.amazon.com/credref/latest/refdoc/creds-config-files.html).

## Hello World

The **HelloWorld** example shows you how to get started using AWS Private Certificate Authority.

### Purpose

Demonstrates a basic hello world example for AWS Private Certificate Authority.

### Prerequisites

For general prerequisites, see the [README](../../README.md#Prerequisites) in the `javav2` folder.

### Run the example

```
mvn exec:java -Dexec.mainClass="com.example.pca.HelloWorld"
```