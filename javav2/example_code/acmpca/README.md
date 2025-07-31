# AWS Private CA code examples for the SDK for Java 2.x

## Overview

Shows how to use the AWS SDK for Java 2.x to work with AWS Private Certificate Authority.

<!--custom.overview.start-->
<!--custom.overview.end-->

_AWS Private CA _

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
<!--custom.instructions.end-->



#### Create and activate a root CA programmatically

This example shows you how to create and activate a root CA using AWS Private CA API actions.


<!--custom.scenario_prereqs.acm-pca_RootCAActivation.start-->
<!--custom.scenario_prereqs.acm-pca_RootCAActivation.end-->


<!--custom.scenarios.acm-pca_RootCAActivation.start-->
<!--custom.scenarios.acm-pca_RootCAActivation.end-->

#### Create and activate a subordinate CA programmatically

This example shows you how to create and activate a subordinate CA using AWS Private CA API actions.


<!--custom.scenario_prereqs.acm-pca_SubordinateCAActivation.start-->
<!--custom.scenario_prereqs.acm-pca_SubordinateCAActivation.end-->


<!--custom.scenarios.acm-pca_SubordinateCAActivation.start-->
<!--custom.scenarios.acm-pca_SubordinateCAActivation.end-->

#### Using CreateCertificateAuthority to support Active Directory

This example shows you how to use the CreateCertificateAuthority operation to create a CA that can be installed in the Enterprise NTAuth store of Microsoft Active Directory (AD).

The operation creates a private root certificate authority (CA) using custom object identifiers (OIDs). See <ulink url="https://docs.aws.amazon.com/privateca/latest/userguide/create-CA.html#example_5">Create a CA for Active Directory login</ulink> for more information and an AWS CLI example of an equivalent operation.

If successful, this function returns the Amazon Resource Name (ARN) of the CA. 



<!--custom.scenario_prereqs.acm-pca_CreatePrivateCertificateAuthorityAD.start-->
<!--custom.scenario_prereqs.acm-pca_CreatePrivateCertificateAuthorityAD.end-->


<!--custom.scenarios.acm-pca_CreatePrivateCertificateAuthorityAD.start-->
<!--custom.scenarios.acm-pca_CreatePrivateCertificateAuthorityAD.end-->

### Tests

⚠ Running tests might result in charges to your AWS account.


To find instructions for running these tests, see the [README](../../README.md#Tests)
in the `javav2` folder.



<!--custom.tests.start-->
<!--custom.tests.end-->

## Additional resources

- [AWS Private CA User Guide](https://docs.aws.amazon.com/privateca/latest/userguide/PcaWelcome.html)
- [AWS Private CA API Reference](https://docs.aws.amazon.com/privateca/latest/APIReference/Welcome.html)
- [SDK for Java 2.x AWS Private CA reference](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/acm-pca/package-summary.html)

<!--custom.resources.start-->
<!--custom.resources.end-->

---

Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.

SPDX-License-Identifier: Apache-2.0
