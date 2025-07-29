// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.ImportCertificateAuthorityCertificateRequest;

// snippet-start:[acmpca.java2.ImportCertificateAuthorityCertificate.main]
/**
 * Before running this Java V2 code example, set up your development 
 * environment, including your credentials.
 * 
 * For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class ImportCertificateAuthorityCertificate {

  public static void main(String[] args) throws Exception {

    final String usage =
        """
            Usage: <region> <caArn>

            Where:
                region - The AWS region (e.g. us-east-1)
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

    // Set the signed certificate.
    String certificateStr =
          "-----BEGIN CERTIFICATE-----\n" +
          "base64-encoded certificate\n" +
          "-----END CERTIFICATE-----\n";
    SdkBytes certSdkBytes = SdkBytes.fromUtf8String(certificateStr);

    // Set the certificate chain.
    String certificateStrChain =
          "-----BEGIN CERTIFICATE-----\n" +
          "base64-encoded certificate\n" +
          "-----END CERTIFICATE-----\n";
    SdkBytes chainSdkBytes = SdkBytes.fromUtf8String(certificateStrChain);

    // Create a request object with required parameters.
    ImportCertificateAuthorityCertificateRequest req =
        ImportCertificateAuthorityCertificateRequest.builder()
            .certificateAuthorityArn(caArn)
            .certificate(certSdkBytes)
            .certificateChain(chainSdkBytes)
            .build();
            
    try {
      client.importCertificateAuthorityCertificate(req);
    } catch (AcmPcaException ex) {
      System.err.println(ex.awsErrorDetails().errorMessage());
    }
  }
}
// snippet-end:[acmpca.java2.ImportCertificateAuthorityCertificate.main]
