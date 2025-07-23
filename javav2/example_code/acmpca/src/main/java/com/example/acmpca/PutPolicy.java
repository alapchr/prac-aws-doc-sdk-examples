// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import java.nio.file.Files;
import java.nio.file.Paths;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.PutPolicyRequest;

// snippet-start:[acmpca.java2.PutPolicy.main]
/**
 * Before running this Java V2 code example, set up your development 
 * environment, including your credentials.
 * 
 * For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class PutPolicy {

  public static void main(String[] args) throws Exception {

    final String usage =
        """
            Usage: <region> <resourceArn>

            Where:
                region - The AWS region (e.g., us-east-1)
                resourceArn - The ARN of your Certificate Authority
            """;

    if (args.length != 2) {
      System.out.println(usage);
      return;
    }

    String region = args[0];
    String resourceArn = args[1];

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(Region.of(region)).build();

    // Import policy
    /* 
    * Note: This code assumes the file "Policy.json" is in a folder titled policy
    */ 
    String policy =
        new String(Files.readAllBytes(Paths.get("policy", "Policy.json")));

    // Create the request object.
    PutPolicyRequest req =
        PutPolicyRequest.builder()
            .resourceArn(resourceArn)
            .policy(policy) 
            .build();

    try {
      client.putPolicy(req);
      System.out.println("Successfully added policy!");
    } catch (AcmPcaException ex) {
      System.err.println(ex.awsErrorDetails().errorMessage());
    }
  }
}
// snippet-end:[acmpca.java2.PutPolicy.main]
