// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.ImportCertificateAuthorityCertificateRequest;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

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

    try {
      /*
       * Read the signed certificate.
       * This assumes that a file named cert.pem is stored somewhere and contains the signed certificate
       */
      String signedCert = Files.readString(Paths.get("cert.pem"), StandardCharsets.UTF_8);
      SdkBytes certSdkBytes = SdkBytes.fromUtf8String(signedCert);

      /*
       * Read the certificate chain.
       * This assumes that a file named certChain.pem is stored somewhere and contains the certificate chain 
       */
      String certChain = Files.readString(Paths.get("certChain.pem"), StandardCharsets.UTF_8);
      SdkBytes chainSdkBytes = SdkBytes.fromUtf8String(certChain);

      ImportCertificateAuthorityCertificateRequest req =
          ImportCertificateAuthorityCertificateRequest.builder()
              .certificateAuthorityArn(caArn)
              .certificate(certSdkBytes)
              .certificateChain(chainSdkBytes)
              .build();
              
      client.importCertificateAuthorityCertificate(req);
      
    } catch (IOException ex) {
      System.err.println("Error reading certificate file: " + ex.getMessage());
    } catch (AcmPcaException ex) {
      System.err.println(ex.awsErrorDetails().errorMessage());
    }
  }
}
// snippet-end:[acmpca.java2.ImportCertificateAuthorityCertificate.main]
