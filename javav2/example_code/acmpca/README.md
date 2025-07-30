# AWS Private Certificate Authority code examples for the SDK Java 2.x

## Available examples

**Single Actions**

Code samples that show you how to call individual service functions.

* **CreateCertificateAuthority** 
* **CreateCertificateAuthorityAuditReport** 
* **CreatePermissions** 
* **DeleteCertificateAuthority** 
* **DeletePermission** 
* **DeletePolicy** 
* **DescribeCertificateAuthority** 
* **DescribeCertificateAuthorityAuditReport** 
* **GetCertificate** 
* **GetCertificateAuthorityCertificate** 
* **GetCertificateAuthorityCsr** 
* **GetPolicy** 
* **ImportCertificateAuthorityCertificate** 
* **IssueCertificate** 
* **ListCertificateAuthorites** 
* **ListPermissions** 
* **ListTags** 
* **PutPolicy** 
* **RestoreCertificateAuthority** 
* **TagCertificateAuthority** 
* **UntagCertificateAuthority** 

**Sceanrios**

Code samples that demonstrate a specific task.

* **Create and Activate a root CA programmatically** 
* **Create and activate a subordinate CA programmatically** 
* **Using CertificateAuthority to support Active Directory** 

## Running the AWS Private Certificate Authority Java files

**IMPORTANT**

The Java code examples perform AWS operations for the account and AWS Region for which you've specified credentials, and you may incur AWS service charges by running them. See the [AWS Pricing page](https://aws.amazon.com/pricing/) for details about the charges you can expect for a given service and operation.

Some of these examples perform *destructive* operations on AWS resources, such as creating a certificate authority. **Be very careful** when running an operation that creates, modifies, or deletes AWS resources in your account. It's best to create separate test-only resources when experimenting with these examples.

## Prerequisites

You must have an AWS account, and have your default credentials and AWS Region configured as described in the [AWS Tools and SDKs Shared Configuration and Credentials Reference Guide](https://docs.aws.amazon.com/credref/latest/refdoc/creds-config-files.html).

For general prerequisites, see the [README](../../README.md#Prerequisites) in the `javav2` folder.

## Running the example

To run the code examples, see the usage instructions in the comments of the example file. 

The example requires parameters for the AWS region and an S3 bucket name for CRL revocation to run the CreateCertificateAuthority code sample.

For example:

mvn exec:java -Dexec.mainClass="com.example.acmpca.CreateCertificateAuthority" -Dexec.args="us-east-1 my-crl-bucket"

## Tests

You can test the Java code examples for AWS Private Certificate Authority by running the test file named **PCATests**.

To find instructions for running these tests, see the [README](../../README.md#Tests)
in the `javav2` folder.

## Additional resources

* [AWS Private Certificate Authority Developer Guide](https://docs.aws.amazon.com/privateca/latest/userguide/PcaWelcome.html)
* [AWS SDK for Java v2 Developer Guide](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/home.html)

---
Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
SPDX-License-Identifier: Apache-2.0