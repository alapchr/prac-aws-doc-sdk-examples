// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
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

  public static ByteBuffer stringToByteBuffer(final String string) {
    if (Objects.isNull(string)) {
      return null;
    }
    byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
    return ByteBuffer.wrap(bytes);
  }

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
      // Specify the certificate signing request (CSR) for the certificate to be signed and issued.
      String strCSR =
      "-----BEGIN CERTIFICATE REQUEST-----\n" + 
      "base64-encoded certificate\n" +
      "-----END CERTIFICATE REQUEST-----\n";

      SdkBytes csrSdkBytes = SdkBytes.fromUtf8String(strCSR);

      // Create a certificate request with all required parameters
      /*
       * Specify the template for the issued certificate
       * Replace 'EndEntityCertificate/V1' with your proper templateArn argument (e.g. arn:aws:acm-pca:::template/RootCACertificate/V1)
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

      // Issue the certificate.
      IssueCertificateResponse result = client.issueCertificate(request);
      // Retrieve and display the certificate ARN.
      String arn = result.certificateArn();
      System.out.println("Certificate ARN: " + arn);
    } catch (AcmPcaException ex) {
      System.err.println(ex.awsErrorDetails().errorMessage());
    }
  }
}
// snippet-end:[acmpca.java2.IssueCertificate.main]
