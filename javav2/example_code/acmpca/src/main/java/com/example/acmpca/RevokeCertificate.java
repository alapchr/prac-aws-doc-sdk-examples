// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

// import software.amazon.awssdk.awscore.client.builder.AwsSyncClientBuilder;
// import software.amazon.awssdk.awscore.client.builder.AwsSyncClientBuilder.EndpointConfiguration;

// import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.regions.Region;
// import software.amazon.awssdk.auth.credentials.AwsCredentials;
// import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
// import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.InvalidArnException;
import software.amazon.awssdk.services.acmpca.model.InvalidStateException;
import software.amazon.awssdk.services.acmpca.model.RequestAlreadyProcessedException;
import software.amazon.awssdk.services.acmpca.model.RequestFailedException;
import software.amazon.awssdk.services.acmpca.model.RequestInProgressException;
import software.amazon.awssdk.services.acmpca.model.ResourceNotFoundException;
import software.amazon.awssdk.services.acmpca.model.RevocationReason;
import software.amazon.awssdk.services.acmpca.model.RevokeCertificateRequest;

// snippet-start:[acmpca.java2.RevokeCertificate.main]
/**
 * Before running this Java V2 code example, set up your development environment, including your
 * credentials.
 *
 * <p>For more information, see the following documentation topic:
 *
 * <p>https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class RevokeCertificate {

  public static void main(String[] args) throws Exception {

    final String usage =
        """

                Usage: <regionName> <caArn> <serial>

                Where:
                 regionName - The AWS region (e.g. us-east-1)
                 caArn - The ARN of the certificate authority
                 serial - The Serial number for the Arn
                """;
    if (args.length != 3) {
      System.out.println(usage);
      return;
    }

    String regionName = args[0];
    String caArn = args[1];
    String serial = args[2];

    // Define the region for your sample.
    Region region = Region.of(regionName);

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(region).build();

    // Create a request object.
    RevokeCertificateRequest req =
        RevokeCertificateRequest.builder()
            .certificateAuthorityArn(caArn) // Set certificate authority ARN.
            .certificateSerial(serial) // Set the certificate serial number.
            .revocationReason(RevocationReason.KEY_COMPROMISE) // Set the Revocation reason.
            .build();

    // Revoke the certificate.
    try {
      client.revokeCertificate(req);
    } catch (InvalidArnException ex) {
      throw ex;
    } catch (InvalidStateException ex) {
      throw ex;
    } catch (ResourceNotFoundException ex) {
      throw ex;
    } catch (RequestAlreadyProcessedException ex) {
      throw ex;
    } catch (RequestInProgressException ex) {
      throw ex;
    } catch (RequestFailedException ex) {
      throw ex;
    }
  }
}
// snippet-end:[acmpca.java2.RevokeCertificate.main]
