// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.CertificateAuthority;
import software.amazon.awssdk.services.acmpca.model.DescribeCertificateAuthorityRequest;
import software.amazon.awssdk.services.acmpca.model.DescribeCertificateAuthorityResponse;

// snippet-start:[acmpca.java2.DescribeCertificateAuthority.main]
/**
 * Before running this Java V2 code example, set up your development 
 * environment, including your credentials.
 * 
 * For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class DescribeCertificateAuthority {

  public static void main(String[] args) throws Exception {

    final String usage =
        """
            Usage: <region> <caArn>

            Where:
                region - the AWS region (e.g. us-east-1)
                caArn - the ARN of the certificate authority 
            """;

    if (args.length != 2) {
      System.out.println(usage);
      return;
    }

    String region = args[0];
    String caArn = args[1];

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(Region.of(region)).build();

    // Create a request object and set the certificate authority ARN.
    DescribeCertificateAuthorityRequest req =
        DescribeCertificateAuthorityRequest.builder()
           .certificateAuthorityArn(caArn)
           .build();

    try {
      DescribeCertificateAuthorityResponse result = client.describeCertificateAuthority(req);
      // Retrieve and display information about the CA.
      CertificateAuthority ca = result.certificateAuthority();
      System.out.println(ca.toString());
    } catch (AcmPcaException ex) {
      System.err.println(ex.awsErrorDetails().errorMessage());
    }
  }
}
// snippet-end:[acmpca.java2.DescribeCertificateAuthority.main]
