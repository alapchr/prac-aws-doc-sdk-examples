// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.CertificateAuthorityStatus;
import software.amazon.awssdk.services.acmpca.model.CrlConfiguration;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.RevocationConfiguration;
import software.amazon.awssdk.services.acmpca.model.UpdateCertificateAuthorityRequest;

// snippet-start:[acmpca.java2.UpdateCertificateAuthority.main]
/**
 * Before running this Java V2 code example, set up your development 
 * environment, including your credentials.
 * 
 * For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class UpdateCertificateAuthority {

  public static void main(String[] args) throws Exception {

    final String usage =
        """
            Usage: <region> <caArn>

            Where:
                region - The AWS region (e.g., us-east-1)
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

    /*
     * Define the certificate revocation list configuration. 
     * If you do not want to update the CRL configuration, leave the CrlConfiguration structure alone and
     * do not set it on your UpdateCertificateAuthorityRequest object.
     */
    CrlConfiguration crlConfigure =
        CrlConfiguration.builder()
            .enabled(true)
            .expirationInDays(365) 
            .customCname("your-custom-cname")
            .s3BucketName("your-bucket-name")
            .build();
    /*
     * Set the CRL configuration onto your UpdateCertificateAuthorityRequest object.
     * If you do not want to change your CRL configuration, do not use the
     * crlConfiguration method.
     */
    RevocationConfiguration revokeConfig =
        RevocationConfiguration.builder()
            .crlConfiguration(crlConfigure)
            .build(); 

     // Create the request object.
    UpdateCertificateAuthorityRequest req =
        UpdateCertificateAuthorityRequest.builder()
            .certificateAuthorityArn(caArn)
            .status(CertificateAuthorityStatus.ACTIVE)
            .revocationConfiguration(revokeConfig)
            .build();
      
    try {
      client.updateCertificateAuthority(req);
      System.out.println("Sucessfully updated CA!");
    } catch (AcmPcaException ex) {
      System.err.println(ex.awsErrorDetails().errorMessage());
    } 
  }
}
// snippet-end:[acmpca.java2.UpdateCertificateAuthority.main]
