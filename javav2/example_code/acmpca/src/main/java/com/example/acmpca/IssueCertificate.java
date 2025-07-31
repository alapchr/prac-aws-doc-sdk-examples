// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.IssueCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.IssueCertificateResponse;
import software.amazon.awssdk.services.acmpca.model.SigningAlgorithm;
import software.amazon.awssdk.services.acmpca.model.Validity;

// snippet-start:[acmpca.java2.IssueCertificate.main]
/**
 * Before running this Java V2 code example, set up your development 
 * environment, including your credentials.
 * 
 * For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class IssueCertificate {

  public static void main(String[] args) throws Exception {

    final String usage =
        """
            Usage: <region> <caArn>

            Where:
                region - the AWS region (e.g. us-east-1)
                caArn - The ARN of the certificate authority
            """;

    if (args.length != 2) {
      System.out.println(usage);
      return;
    }

    String region = args[0];
    String caArn = args[1];

    // Create a client that you can use to make requests.
     AcmPcaClient client = AcmPcaClient.builder().region(Region.of(region)).build();

    try {
      /*
       * Read the certificate signing request (CSR) from ca.csr file
       * This assumes that a CSR was added to a file named ca.csr that is stored somewhere
       */
      String strCSR = Files.readString(Paths.get("ca.csr"), StandardCharsets.UTF_8);
      SdkBytes csrSdkBytes = SdkBytes.fromUtf8String(strCSR);

      /*
       * Create a certificate request with all required parameters.
       * Replace 'EndEntityCertificate/V1' with your proper templateArn argument 
       * (e.g. arn:aws:acm-pca:::template/RootCACertificate/V1)
       */
      IssueCertificateRequest request =
          IssueCertificateRequest.builder()
              .certificateAuthorityArn(caArn)
              .templateArn("arn:aws:acm-pca:::template/EndEntityCertificate/V1")
              .signingAlgorithm(SigningAlgorithm.SHA256_WITHRSA)
              .validity(Validity.builder().value(365L).type("DAYS").build())
              .idempotencyToken("1234")
              .csr(csrSdkBytes)
              .build();

      IssueCertificateResponse result = client.issueCertificate(request);
      // Retrieve and display the certificate ARN.
      String arn = result.certificateArn();
      System.out.println("Certificate ARN: " + arn);
    } catch (AcmPcaException ex) {
      System.err.println(ex.awsErrorDetails().errorMessage());
    } catch (IOException ex) {
      System.err.println("Error reading ca.csr file: " + ex.getMessage());
    }
  }
}
// snippet-end:[acmpca.java2.IssueCertificate.main]
