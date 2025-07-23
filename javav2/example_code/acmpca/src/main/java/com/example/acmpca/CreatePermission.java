// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import java.util.ArrayList;
import java.util.List;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.ActionType;
import software.amazon.awssdk.services.acmpca.model.CreatePermissionRequest;

// snippet-start:[acmpca.java2.CreatePermission.main]
/**
 * Before running this Java V2 code example, set up your development environment, including your
 * credentials.
 *
 * <p>For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class CreatePermission {

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

    // Set the permissions to give the user.
    /*
     * Change permissions values to your specified permissions
     * Ex. "ISSUE_CERTIFICATE"
     */
    List<ActionType> permissions = new ArrayList<>();
    permissions.add(ActionType.ISSUE_CERTIFICATE);
    permissions.add(ActionType.GET_CERTIFICATE);
    permissions.add(ActionType.LIST_PERMISSIONS);

    // Create a request object
    /*
     * Set the AWS service principal
     */
    CreatePermissionRequest req =
        CreatePermissionRequest.builder()
            .certificateAuthorityArn(caArn) 
            .principal("acm.amazonaws.com") 
            .actions(permissions)
            .build();

    try {
      client.createPermission(req);
      System.out.println("Sucessfully created permissions!");
    } catch (AcmPcaException ex) {
      System.err.println(ex.awsErrorDetails().errorMessage());
    }
  }
}
// snippet-end:[acmpca.java2.CreatePermission.main]
