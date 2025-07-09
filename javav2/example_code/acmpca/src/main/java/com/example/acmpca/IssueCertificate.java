// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

// import software.amazon.awssdk.awscore.client.builder.AwsSyncClientBuilder;
// import software.amazon.awssdk.awscore.client.builder.AwsSyncClientBuilder.EndpointConfiguration;
// import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
// import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.InvalidArgsException;
import software.amazon.awssdk.services.acmpca.model.InvalidArnException;
import software.amazon.awssdk.services.acmpca.model.InvalidStateException;
import software.amazon.awssdk.services.acmpca.model.IssueCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.IssueCertificateResponse;
import software.amazon.awssdk.services.acmpca.model.LimitExceededException;
import software.amazon.awssdk.services.acmpca.model.MalformedCsrException;
import software.amazon.awssdk.services.acmpca.model.ResourceNotFoundException;
import software.amazon.awssdk.services.acmpca.model.SigningAlgorithm;
import software.amazon.awssdk.services.acmpca.model.Validity;

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
                  region - the AWS region (e.g. us-east-1).
                  caArn - The ARN of the certificate authority.
               """;

    if (args.length < 2) {
      System.out.println(usage);
      return;
    }

    String regionName = args[0];
    String caArn = args[1];

    // Define the region for your sample.
    Region region = Region.of(regionName);

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(region).build();

    // Create a certificate request:
    IssueCertificateRequest req =
        IssueCertificateRequest.builder().certificateAuthorityArn(caArn).build();

    // Specify the certificate signing request (CSR) for the certificate to be signed and issued.
    String strCSR =
        "<replaceable>-----BEGIN CERTIFICATE REQUEST-----\n</replaceable>"
            + "<replaceable>base64-encoded certificate\n</replaceable>"
            + "<replaceable>-----END CERTIFICATE REQUEST-----\n</replaceable>";
    ByteBuffer csrByteBuffer = stringToByteBuffer(strCSR);
    req = req.toBuilder().csr(csrByteBuffer).build();

    // Specify the template for the issued certificate.
    req.templateArn(
        "arn:aws:acm-pca:::template/<replaceable>EndEntityCertificate/V1</replaceable>");

    // Set the signing algorithm.
    req.signingAlgorithm(SigningAlgorithm.SHA256_WITHRSA);

    // Set the validity period for the certificate to be issued.
    Validity validity = Validity.builder().value(3650L).type("DAYS").build();

    req = req.toBuilder().validity(validity).build();

    // Set the idempotency token.
    req = req.toBuilder().idempotencyToken("1234").build();

    // Issue the certificate.
    IssueCertificateResponse result = null;
    try {
      result = client.issueCertificate(req);
    } catch (LimitExceededException ex) {
      throw ex;
    } catch (ResourceNotFoundException ex) {
      throw ex;
    } catch (InvalidStateException ex) {
      throw ex;
    } catch (InvalidArnException ex) {
      throw ex;
    } catch (InvalidArgsException ex) {
      throw ex;
    } catch (MalformedCsrException ex) {
      throw ex;
    }

    // Retrieve and display the certificate ARN.
    String arn = result.certificateArn();
    System.out.println(arn);
  }
}
