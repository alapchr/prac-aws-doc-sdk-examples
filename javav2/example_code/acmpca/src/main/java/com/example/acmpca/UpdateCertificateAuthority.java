// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.CertificateAuthorityStatus;
import software.amazon.awssdk.services.acmpca.model.ConcurrentModificationException;
import software.amazon.awssdk.services.acmpca.model.CrlConfiguration;
import software.amazon.awssdk.services.acmpca.model.InvalidArgsException;
import software.amazon.awssdk.services.acmpca.model.InvalidArnException;
import software.amazon.awssdk.services.acmpca.model.InvalidPolicyException;
import software.amazon.awssdk.services.acmpca.model.InvalidStateException;
import software.amazon.awssdk.services.acmpca.model.ResourceNotFoundException;
import software.amazon.awssdk.services.acmpca.model.RevocationConfiguration;
import software.amazon.awssdk.services.acmpca.model.UpdateCertificateAuthorityRequest;

// snippet-start:[acmpca.java2.UpdateCertificateAuthority.main]
public class UpdateCertificateAuthority {

  public static void main(String[] args) throws Exception {

    final String usage =
        """

             Usage: <region> <s3BucketName> <caArn>

             Where:
                region - The AWS region (e.g., us-east-1)
                s3Bucket - The name of your bucket for CRL revocation
                caArn - The ARN of the certificate authority
            """;

    if (args.length != 3) {
      System.out.println(usage);
      return;
    }

    String regionName = args[0];
    String s3BucketName = args[1];
    String caArn = args[2];

    // Define the region for your sample.
    Region region = Region.of(regionName);

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(region).build();

    // Create the request object.
    UpdateCertificateAuthorityRequest req =
        UpdateCertificateAuthorityRequest.builder().certificateAuthorityArn(caArn).build();

    // Define the certificate revocation list configuration. If you do not want to
    // update the CRL configuration, leave the CrlConfiguration structure alone and
    // do not set it on your UpdateCertificateAuthorityRequest object.
    CrlConfiguration crlConfigure =
        CrlConfiguration.builder()
            .enabled(true)
            .expirationInDays(365)
            .customCname("your-custom-name")
            .s3BucketName(s3BucketName)
            .build();

    // Set the CRL configuration onto your UpdateCertificateAuthorityRequest object.
    // If you do not want to change your CRL configuration, do not use the
    // setCrlConfiguration method.
    RevocationConfiguration revokeConfig =
        RevocationConfiguration.builder().crlConfiguration(crlConfigure).build();

    req = req.toBuilder().revocationConfiguration(revokeConfig).build();

    //   revokeConfig = revokeConfig.toBuilder().build();

    //   // Set the status.
    req.status(CertificateAuthorityStatus.ACTIVE);

    // Create the result object.
    try {
      client.updateCertificateAuthority(req);
    } catch (ConcurrentModificationException ex) {
      throw ex;
    } catch (ResourceNotFoundException ex) {
      throw ex;
    } catch (InvalidArgsException ex) {
      throw ex;
    } catch (InvalidArnException ex) {
      throw ex;
    } catch (InvalidStateException ex) {
      throw ex;
    } catch (InvalidPolicyException ex) {
      throw ex;
    }
  }
}
// snippet-end:[acmpca.java2.UpdateCertificateAuthority.main]
