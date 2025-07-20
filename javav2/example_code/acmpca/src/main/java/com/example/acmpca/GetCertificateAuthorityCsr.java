// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.waiters.AcmPcaWaiter;

import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCsrRequest;
import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCsrResponse;

import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.InvalidArnException;
import software.amazon.awssdk.services.acmpca.model.RequestFailedException;
import software.amazon.awssdk.services.acmpca.model.RequestInProgressException;
import software.amazon.awssdk.services.acmpca.model.ResourceNotFoundException;

// snippet-start:[acmpca.java2.GetCertificateAuthorityCsr.main]
/**
 * Before running this Java V2 code example, set up your development environment, including your
 * credentials.
 *
 * <p>For more information, see the following documentation topic:
 *
 * <p>https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class GetCertificateAuthorityCsr {

  public static void main(String[] args) throws Exception {

    final String usage =
        """
               Usage: <region> <caArn>

               Where:
                  region - the AWS region (e.g. us-east-1)
                  caArn - The ARN of the certificate authority

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

    // Create the request object and set the CA ARN.
    GetCertificateAuthorityCsrRequest req =
        GetCertificateAuthorityCsrRequest.builder().certificateAuthorityArn(caArn).build();

    // Create waiter to wait on successful creation of the CSR file.
    try (AcmPcaWaiter waiter = client.waiter()) {
      waiter.waitUntilCertificateAuthorityCSRCreated(
          b -> b.certificateAuthorityArn(req.certificateAuthorityArn()).build());
    } catch (AcmPcaException e) {
      System.out.println("Error: " + e.getMessage());
    }

    // Retrieve the CSR.
    GetCertificateAuthorityCsrResponse result = null;
    try {
      result = client.getCertificateAuthorityCsr(req);
      // Retrieve and display the CSR;
      String Csr = result.csr();
      System.out.println(Csr);

    } catch (RequestInProgressException
        | ResourceNotFoundException
        | InvalidArnException
        | RequestFailedException e) {
      throw e;
    } catch (AcmPcaException e) {
      throw e;
    }
  }
}
// snippet-end:[acmpca.java2.GetCertificateAuthorityCsr.main]
