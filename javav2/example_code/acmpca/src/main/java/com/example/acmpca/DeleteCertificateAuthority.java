// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.DeleteCertificateAuthorityRequest;

// snippet-start:[acmpca.java2.DeleteCertificateAuthority.main]
/**
 * Before running this Java V2 code example, set up your development environment, including your
 * credentials.
 *
 * <p>For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class DeleteCertificateAuthority {
  public static void main(String[] args) throws Exception {

    final String usage =
        """

               Usage: <region> <certArn>

               Where:
                  region - the AWS region (e.g. us-east-1)
                  certArn - the ARN of the certificate authority to delete
               """;

    if (args.length < 2 || args.length > 3) {
      System.out.println(usage);
      return;
    }

    String regionName = args[0];
    String certArn = args[1];

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(Region.of(regionName)).build();

    // Create a request object and set the ARN of the private CA to delete.
    DeleteCertificateAuthorityRequest req =
        DeleteCertificateAuthorityRequest.builder()
            .certificateAuthorityArn(certArn) // Set the certificate authority ARN
            .permanentDeletionTimeInDays(12) // Set the recovery period
            .build();

    // Delete the CA.
    try {
      client.deleteCertificateAuthority(req);
      System.out.println("Successfully deleted Certificate Authority!");
    } catch (AcmPcaException ex) {
      throw ex;
    }
  }
}
// snippet-end:[acmpca.java2.DeleteCertificateAuthority.main]
