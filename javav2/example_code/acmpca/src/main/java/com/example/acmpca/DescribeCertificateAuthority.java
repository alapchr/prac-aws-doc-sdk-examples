// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.CertificateAuthority;
import software.amazon.awssdk.services.acmpca.model.DescribeCertificateAuthorityRequest;
import software.amazon.awssdk.services.acmpca.model.DescribeCertificateAuthorityResponse;
import software.amazon.awssdk.services.acmpca.model.InvalidArnException;
import software.amazon.awssdk.services.acmpca.model.ResourceNotFoundException;

// snippet-start:[acmpca.java2.DescribeCertificateAuthority.main]
/**
 * Before running this Java V2 code example, set up your development environment, including your
 * credentials.
 *
 * <p>For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class DescribeCertificateAuthority {

  public static void main(String[] args) throws Exception {

    final String usage =
        """
               Usage: <region> <certArn>

               Where:
                  region - the AWS region (e.g. us-east-1)
                  certArn - the ARN of the certificate authority
               """;

    if (args.length < 2) {
      System.out.println(usage);
      return;
    }

    String regionName = args[0];
    String certArn = args[1];

    // Create a client that you can use to make requests. 
    AcmPcaClient client = AcmPcaClient.builder().region(Region.of(regionName)).build();

    // Create a request object and set the certificate authority ARN.
    DescribeCertificateAuthorityRequest req =
        DescribeCertificateAuthorityRequest.builder()
            .certificateAuthorityArn(certArn) // set the certificate authority ARN
            .build();

    try {

      DescribeCertificateAuthorityResponse result = client.describeCertificateAuthority(req);

      // Retrieve and display information about the CA.
      CertificateAuthority PCA = result.certificateAuthority();
      String strPCA = PCA.toString();
      System.out.println(strPCA);

    } catch (InvalidArnException | ResourceNotFoundException e) {
      throw e;
    } catch (AcmPcaException e) {
      System.out.println(e.getMessage());
    }
  }
}
// snippet-end:[acmpca.java2.DescribeCertificateAuthority.main]
