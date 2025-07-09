// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.InvalidNextTokenException;
import software.amazon.awssdk.services.acmpca.model.ListCertificateAuthoritiesRequest;
import software.amazon.awssdk.services.acmpca.model.ListCertificateAuthoritiesResponse;

// snippet-start:[acmpca.java2.ListCertificateAuthorities.main]
/**
 * Before running this Java V2 code example, set up your development environment, including your
 * credentials.
 *
 * <p>For more information, see the following documentation topic:
 *
 * <p>https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
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

    String regionName = args[0];

    // Define the region for your sample.
    Region region = Region.of(regionName);

    AcmPcaClient client = AcmPcaClient.builder().region(region).build();

    // Create the request object.
    ListCertificateAuthoritiesRequest req =
        ListCertificateAuthoritiesRequest.builder().maxResults(10).build();

    // Retrieve a list of your CAs.
    try {
      ListCertificateAuthoritiesResponse result = client.listCertificateAuthorities(req);
      // Display the CA list.
      for (var ca : result.certificateAuthorities()) {
        System.out.println(ca);
        System.out.println();
      }
    } catch (InvalidNextTokenException e) {
      System.out.println("Invalid next token: " + e.getMessage());
    } catch (AcmPcaException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
// snippet-end:[acmpca.java2.ListCertificateAuthorities.main]
