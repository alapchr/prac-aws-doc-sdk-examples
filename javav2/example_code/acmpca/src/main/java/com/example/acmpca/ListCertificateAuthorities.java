// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.CertificateAuthority;
import software.amazon.awssdk.services.acmpca.model.ListCertificateAuthoritiesRequest;
import software.amazon.awssdk.services.acmpca.model.ListCertificateAuthoritiesResponse;

// snippet-start:[acmpca.java2.ListCertificateAuthorities.main]
/**
 * Before running this Java V2 code example, set up your development 
 * environment, including your credentials.
 * <p>
 * For more information, see the following documentation topic:
 * <p>
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class ListCertificateAuthorities {

  public static void main(String[] args) throws Exception {

    final String usage =
        """
            Usage: <region>

            Where:
                region - The AWS region (e.g., us-east-1)
            """;

    if (args.length != 1) {
      System.out.println(usage);
      return;
    }

    String region = args[0];

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(Region.of(region)).build();

    // Create the request object.
    ListCertificateAuthoritiesRequest req =
        ListCertificateAuthoritiesRequest.builder()
           .maxResults(10)
           .build();

    try {
      ListCertificateAuthoritiesResponse result = client.listCertificateAuthorities(req);
      // Display the CA list.
      for (CertificateAuthority ca : result.certificateAuthorities()) {
        System.out.println(ca + "\n");
      }
    } catch (AcmPcaException ex) {
      System.err.println(ex.awsErrorDetails().errorMessage());
    }
  }
}
// snippet-end:[acmpca.java2.ListCertificateAuthorities.main]
