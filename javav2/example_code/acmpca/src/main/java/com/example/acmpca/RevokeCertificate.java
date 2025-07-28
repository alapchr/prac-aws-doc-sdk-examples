// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;

import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.RevocationReason;
import software.amazon.awssdk.services.acmpca.model.RevokeCertificateRequest;

// snippet-start:[acmpca.java2.RevokeCertificate.main]
/**
 * Before running this Java V2 code example, set up your development 
 * environment, including your credentials.
 * 
 * For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class RevokeCertificate {

  public static void main(String[] args) throws Exception {

    final String usage =
        """
           Usage: <regionName> <caArn> <serial>

           Where:
                regionName - The AWS region (e.g. us-east-1)
                caArn - The ARN of the certificate authority
                serial - The Serial number for the Arn (ex. "79:3f:0d:5b:6a:04:12:5e:2c:9c:fb:52:37:35:98:fe")
           """;

    if (args.length != 3) {
      System.out.println(usage);
      return;
    }

    String region = args[0];
    String caArn = args[1];
    String serial = args[2];

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(Region.of(region)).build();

    // Create a request object and set the revocation reason.
    RevokeCertificateRequest req =
        RevokeCertificateRequest.builder()
            .certificateAuthorityArn(caArn) 
            .certificateSerial(serial) 
            .revocationReason(RevocationReason.KEY_COMPROMISE) 
            .build();
            
    try {
      client.revokeCertificate(req);
    } catch (AcmPcaException ex) {
       System.err.println(ex.awsErrorDetails().errorMessage());
    } 
  }
}
// snippet-end:[acmpca.java2.RevokeCertificate.main]
