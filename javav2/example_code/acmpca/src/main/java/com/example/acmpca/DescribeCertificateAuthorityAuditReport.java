// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import java.time.Instant;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.DescribeCertificateAuthorityAuditReportRequest;
import software.amazon.awssdk.services.acmpca.model.DescribeCertificateAuthorityAuditReportResponse;
import software.amazon.awssdk.services.acmpca.waiters.AcmPcaWaiter;

// snippet-start:[acmpca.java2.DescribeCertificateAuthorityAuditReport.main]
/**
 * Before running this Java V2 code example, set up your development 
 * environment, including your credentials.
 * 
 * For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class DescribeCertificateAuthorityAuditReport {

   public static void main(String[] args) throws Exception {

    final String usage =
        """
            Usage: <region> <caArn> <reportId>

            Where:
                region - The AWS region (e.g. us-east-1)
                caArn - The ARN of the certificate authority
                reportId - The ID of the audit report (e.g. "11111111-2222-3333-4444-555555555555")
            """;

    if (args.length != 3) {
      System.out.println(usage);
      return;
    }

    String region = args[0];
    String caArn = args[1];
    String reportId = args[2];

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(Region.of(region)).build();

    // Create a request object.
    DescribeCertificateAuthorityAuditReportRequest req =
        DescribeCertificateAuthorityAuditReportRequest.builder()
            .certificateAuthorityArn(caArn)
            .auditReportId(reportId)
            .build();

    // Create waiter to wait on successful creation of the audit report file.
    try (AcmPcaWaiter waiter = client.waiter()){
      waiter.waitUntilAuditReportCreated(
         b -> b.certificateAuthorityArn(caArn).auditReportId(reportId).build());
    } catch (AcmPcaException ex) {
      System.err.println(ex.awsErrorDetails().errorMessage());
    }

    try {
      DescribeCertificateAuthorityAuditReportResponse result = 
      client.describeCertificateAuthorityAuditReport(req);

      String status = result.auditReportStatusAsString();
      String s3Bucket = result.s3BucketName();
      String s3Key = result.s3Key();
      Instant createdAt = result.createdAt(); 

      System.out.println(status);
      System.out.println(s3Bucket);
      System.out.println(s3Key);
      System.out.println(createdAt);

    } catch (AcmPcaException ex){
      System.err.println(ex.awsErrorDetails().errorMessage());
    }
  }
}
// snippet-end:[acmpca.java2.DescribeCertificateAuthorityAuditReport.main]
