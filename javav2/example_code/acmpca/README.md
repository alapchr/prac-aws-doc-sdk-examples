# AWS Private CA code examples for the SDK for Java 2.x

## Overview

Shows how to use the AWS SDK for Java 2.x to work with AWS Private Certificate Authority.

<!--custom.overview.start-->
<!--custom.overview.end-->

## ⚠ Important

* Running this code might result in charges to your AWS account. For more details, see [AWS Pricing](https://aws.amazon.com/pricing/) and [Free Tier](https://aws.amazon.com/free/).
* Running the tests might result in charges to your AWS account.
* We recommend that you grant your code least privilege. At most, grant only the minimum permissions required to perform the task. For more information, see [Grant least privilege](https://docs.aws.amazon.com/IAM/latest/UserGuide/best-practices.html#grant-least-privilege).
* This code is not tested in every AWS Region. For more information, see [AWS Regional Services](https://aws.amazon.com/about-aws/global-infrastructure/regional-product-services).

<!--custom.important.start-->
<!--custom.important.end-->

## Code examples

### Prerequisites

For prerequisites, see the [README](../../README.md#Prerequisites) in the `javav2` folder.


<!--custom.prerequisites.start-->
<!--custom.prerequisites.end-->

### Single actions

Code excerpts that show you how to call individual service functions.

- [CreateCertificateAuthority](src/main/java/com/example/acmpca/CreateCertificateAuthority.java#L22)
- [CreateCertificateAuthorityAuditReport](src/main/java/com/example/acmpca/CreateCertificateAuthorityAuditReport.java#L12)
- [CreatePermission](src/main/java/com/example/acmpca/CreatePermission.java#L14)
- [DeleteCertificateAuthority](src/main/java/com/example/acmpca/DeleteCertificateAuthority.java#L11)
- [DeletePermission](src/main/java/com/example/acmpca/DeletePermission.java#L11)
- [DeletePolicy](src/main/java/com/example/acmpca/DeletePolicy.java#L11)
- [DescribeCertificateAuthority](src/main/java/com/example/acmpca/DescribeCertificateAuthority.java#L13)
- [DescribeCertificateAuthorityAuditReport](src/main/java/com/example/acmpca/DescribeCertificateAuthorityAuditReport.java#L14)
- [GetCertificate](src/main/java/com/example/acmpca/GetCertificate.java#L13)
- [GetCertificateAuthorityCertificate](src/main/java/com/example/acmpca/GetCertificateAuthorityCertificate.java#L12)
- [GetCertificateAuthorityCsr](src/main/java/com/example/acmpca/GetCertificateAuthorityCsr.java#L13)
- [GetPolicy](src/main/java/com/example/acmpca/GetPolicy.java#L12)
- [ImportCertificateAuthorityCertificate](src/main/java/com/example/acmpca/ImportCertificateAuthorityCertificate.java#L16)
- [IssueCertificate](src/main/java/com/example/acmpca/IssueCertificate.java#L19)
- [ListCertificateAuthorities](src/main/java/com/example/acmpca/ListCertificateAuthorities.java#L13)
- [ListPermissions](src/main/java/com/example/acmpca/ListPermissions.java#L13)
- [ListTags](src/main/java/com/example/acmpca/ListTags.java#L12)
- [PutPolicy](src/main/java/com/example/acmpca/PutPolicy.java#L13)
- [RestoreCertificateAuthority](src/main/java/com/example/acmpca/RestoreCertificateAuthority.java#L11)
- [RevokeCertificate](src/main/java/com/example/acmpca/RevokeCertificate.java#L13)
- [TagCertificateAuthorities](src/main/java/com/example/acmpca/TagCertificateAuthorities.java#L14)
- [UntagCertificateAuthority](src/main/java/com/example/acmpca/UntagCertificateAuthority.java#L14)
- [UpdateCertificateAuthority](src/main/java/com/example/acmpca/UpdateCertificateAuthority.java#L14)

### Scenarios

Code examples that show you how to accomplish a specific task by calling multiple
functions within the same service.

- [Create and activate a root CA programmatically](src/main/java/com/example/acmpca/scenarios/RootCAActivation.java)
- [Create and activate a subordinate CA programmatically](src/main/java/com/example/acmpca/scenarios/SubordinateCAActivation.java)
- [Using CreateCertificateAuthority to support Active Directory](src/main/java/com/example/acmpca/scenarios/CreatePrivateCertificateAuthorityAD.java)


<!--custom.examples.start-->
<!--custom.examples.end-->

## Run the examples

### Instructions


<!--custom.instructions.start-->
To run the code examples, see the usage instructions in the comments of the example file. 

For Example:

This example requires parameters for the AWS region and an S3 bucket name for CRL revocation to run the CreateCertificateAuthority code sample.

**mvn exec:java -Dexec.mainClass="com.example.acmpca.CreateCertificateAuthority" -Dexec.args="us-east-1 my-crl-bucket"**
<!--custom.instructions.end-->


### Tests

⚠ Running tests might result in charges to your AWS account.

<!--custom.tests.start-->
You can test the Java code examples for AWS Private Certificate Authority by running the test file named **PCATests**.
<!--custom.tests.end-->

To find instructions for running these tests, see the [README](../../README.md#Tests)
in the `javav2` folder.


## Additional resources

- [AWS Private CA User Guide](https://docs.aws.amazon.com/privateca/latest/userguide/PcaWelcome.html)
- [AWS Private CA API Reference](https://docs.aws.amazon.com/privateca/latest/APIReference/Welcome.html)
- [SDK for Java 2.x AWS Private CA reference](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/acm-pca/package-summary.html)

<!--custom.resources.start-->
<!--custom.resources.end-->

---

Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.

SPDX-License-Identifier: Apache-2.0
