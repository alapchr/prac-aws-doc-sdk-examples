// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.CreateCertificateAuthorityAuditReportRequest;
import software.amazon.awssdk.services.acmpca.model.CreateCertificateAuthorityAuditReportResponse;

// snippet-start:[acmpca.java2.CreateCertificateAuthorityAuditReport.main]
/**
 * Before running this Java V2 code example, set up your development 
 * environment, including your credentials.
 * 
 * For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class CreateCertificateAuthorityAuditReport {

   public static void main(String[] args) throws Exception {

    final String usage =
        """
            Usage: <region> <s3Bucket> <caArn>

            Where:
                region - The AWS region (e.g., us-east-1)
                s3Bucket - The S3 bucket name for your report
                caArn - The ARN of the certificate authority
            """;

    if (args.length != 3) {
      System.out.println(usage);
      return;
    }

    String region = args[0];
    String s3Bucket = args[1];
    String caArn = args[2];

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(Region.of(region)).build();

    // Create a request object and specify the audit response format.
    CreateCertificateAuthorityAuditReportRequest req =
        CreateCertificateAuthorityAuditReportRequest.builder()
            .certificateAuthorityArn(caArn)
            .s3BucketName(s3Bucket)
            .auditReportResponseFormat("JSON")
            .build();

    try {
      CreateCertificateAuthorityAuditReportResponse result = 
      client.createCertificateAuthorityAuditReport(req);

      String ID = result.auditReportId();
      String S3Key = result.s3Key();

      System.out.println(ID);
      System.out.println(S3Key);
    } catch (AcmPcaException ex) {
      System.err.println(ex.awsErrorDetails().errorMessage());
    } 
   }
}
// snippet-end:[acmpca.java2.CreateCertificateAuthorityAuditReport.main]
