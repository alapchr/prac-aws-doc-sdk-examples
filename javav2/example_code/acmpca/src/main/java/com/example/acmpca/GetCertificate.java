// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.GetCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.GetCertificateResponse;
import software.amazon.awssdk.services.acmpca.waiters.AcmPcaWaiter;

// snippet-start:[acmpca.java2.GetCertificate.main]
/**
 * Before running this Java V2 code example, set up your development environment, including your
 * credentials.
 *
 * <p>For more information, see the following documentation topic:
 *
 * <p>https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class GetCertificate {

  public static void main(String[] args) throws Exception {

    final String usage =
        """

         Usage: <region> <certArn> <caArn>

         Where:
            region - The AWS region (e.g., us-east-1)
            certArn - The ARN of the certificate to retrieve
            caArn - The ARN of the certificate authority
         """;

    if (args.length != 3) {
      System.out.println(usage);
      return;
    }

    String regionName = args[0];
    String certArn = args[1];
    String caArn = args[2];

    // Define the region for your sample.
    Region region = Region.of(regionName);

    // Create a client.
    AcmPcaClient client = AcmPcaClient.builder().region(region).build();

    // Create a request object.
    GetCertificateRequest req =
        GetCertificateRequest.builder()
            .certificateArn(certArn)
            .certificateAuthorityArn(caArn)
            .build();

    // Create waiter to wait on successful creation of the certificate file.
    try (AcmPcaWaiter waiter = client.waiter()) {
      waiter.waitUntilCertificateIssued(
          b ->
              b.certificateArn(req.certificateArn())
                  .certificateAuthorityArn(req.certificateAuthorityArn()));
    } catch (AcmPcaException e) {
      throw e;
    }

    try {
      // Retrieve the certificate and certificate chain.
      GetCertificateResponse result = client.getCertificate(req);
      // Get the certificate and display the result.
      String strCert = result.certificate();
      System.out.println(strCert);
    } catch (AcmPcaException ex) {
      throw ex;
    }
  }
}
// snippet-end:[acmpca.java2.GetCertificate.main]
