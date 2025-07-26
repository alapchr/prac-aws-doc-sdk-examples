// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.DeleteCertificateAuthorityRequest;

// snippet-start:[acmpca.java2.DeleteCertificateAuthority.main]
/**
 * Before running this Java V2 code example, set up your development 
 * environment, including your credentials.
 * 
 * For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class DeleteCertificateAuthority {
  
  public static void main(String[] args) throws Exception {

    final String usage =
        """
            Usage: <region> <certArn> <restorePeriod>

            Where:
               region - the AWS region (e.g. us-east-1)
               caArn - the ARN of the certificate authority to delete
               restorePeriod - (optional) The number of days for restoration before permanent deletion occurs
            """;

    if (args.length < 2 || args.length > 3) {
      System.out.println(usage);
      return;
    }

    String region = args[0];
    String caArn = args[1];
    Integer restorePeriod = args.length == 3 ? Integer.parseInt(args[2]) : null; 

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(Region.of(region)).build();

    // Create a request object.
    DeleteCertificateAuthorityRequest req =
        DeleteCertificateAuthorityRequest.builder()
            .certificateAuthorityArn(caArn)
            .permanentDeletionTimeInDays(restorePeriod)
            .build();
   
    try {
      client.deleteCertificateAuthority(req);
      System.out.println("Successfully deleted CA!");
    } catch (AcmPcaException ex) {
      System.err.println(ex.awsErrorDetails().errorMessage());
    }
  }
}
// snippet-end:[acmpca.java2.DeleteCertificateAuthority.main]
