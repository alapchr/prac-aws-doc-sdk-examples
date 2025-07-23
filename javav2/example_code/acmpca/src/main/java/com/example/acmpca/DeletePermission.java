// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.DeletePermissionRequest;

// snippet-start:[acmpca.java2.DeletePermission.main]
/**
 * Before running this Java V2 code example, set up your development environment, including your
 * credentials.
 *
 * <p>For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class DeletePermission {

  public static void main(String[] args) throws Exception {

    final String usage =
        """

            Usage: <region> <caArn>

            Where:
                region - The AWS region (e.g., us-east-1).
                caArn - The ARN of the certificate authority.
            """;

    if (args.length != 2) {
      System.out.println(usage);
      return;
    }

    String regionName = args[0];
    String caArn = args[1];

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(Region.of(regionName)).build();

    // Create a request object.
    DeletePermissionRequest req =
        DeletePermissionRequest.builder()
            .certificateAuthorityArn(caArn) // Set the certificate authority ARN.
            .principal("acm.amazonaws.com") // Set the AWS service principal.
            .build();

    try {
      client.deletePermission(req);
      System.out.println("Successfully deleted Permissions!");
    } catch (AcmPcaException ex) {
       System.err.println(ex.awsErrorDetails().errorMessage());
    }
  }
}
// snippet-end:[acmpca.java2.DeletePermission.main]
