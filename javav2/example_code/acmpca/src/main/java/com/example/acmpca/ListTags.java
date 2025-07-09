//Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.regions.Region;

import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.ListTagsRequest;
import software.amazon.awssdk.services.acmpca.model.ListTagsResponse;

// snippet-start:[acmpca.java2.ListTags.main] 
/**
 * Before running this Java V2 code example, set up your development
 * environment, including your credentials.
 * <p>
 * For more information, see the following documentation topic:
 * <p>
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */

public class ListTags {

   public static void main(String[] args) throws Exception {

        final String usage =  """
                
            Usage: <region> <caArn>

            Where: 
                region - The AWS region (e.g., us-east-1).
                caArn - The ARN of the certificate authority.

                """;
        if (args.length != 2){
            System.out.println(usage);
            return;
        }

        String regionName = args[0];
        String caArn = args[1];

        // Define the region for your sample.
        Region region = Region.of(regionName); 

      // Create a client that you can use to make requests.
      AcmPcaClient client = AcmPcaClient.builder()
            .region(region)
            .build();

      // Create a request object and set the CA ARN.
      ListTagsRequest req = ListTagsRequest.builder()
            .certificateAuthorityArn(caArn)
            .build();

      // List the tags
      try {
         ListTagsResponse result =  client.listTags(req);
         // Retrieve and display the tags.
         System.out.println(result);
      } catch (AcmPcaException e) {
        System.out.println("ERROR: " + e.getMessage());
      } 

   }
}
// snippet-end:[acmpca.java2.ListTags.main] 
