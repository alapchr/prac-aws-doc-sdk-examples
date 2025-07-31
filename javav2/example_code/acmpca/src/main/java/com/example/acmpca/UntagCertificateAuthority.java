// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import java.util.ArrayList;
import java.util.List;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.Tag;
import software.amazon.awssdk.services.acmpca.model.UntagCertificateAuthorityRequest;

// snippet-start:[acmpca.java2.UntagCertificateAuthority.main]
/**
 * Before running this Java V2 code example, set up your development 
 * environment, including your credentials.
 * 
 * For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class UntagCertificateAuthority {

  public static void main(String[] args) throws Exception {

    final String usage = 
        """
           Usage: <region> <caArn>

           Where:
               region - The AWS region (e.g. us-east-1)
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

    // Add both types of tags to remove in a single collection.
    List<Tag> tags = new ArrayList<>();
    
    // Key-only tag removal - removes any tag with this key regardless of value
    Tag tagKeyOnly = Tag.builder().key("Administrator").build();
    tags.add(tagKeyOnly);
    
    // Key-value tag removal - removes only the tag with this specific key-value pair
    Tag tagKeyValue = Tag.builder().key("PrivateCA").value("WebServices").build();
    tags.add(tagKeyValue);
        
    // Create a request object.
    UntagCertificateAuthorityRequest req =
        UntagCertificateAuthorityRequest.builder()
            .certificateAuthorityArn(caArn)
            .tags(tags)
            .build();

    try {
      client.untagCertificateAuthority(req);
      System.out.println("Successfully untagged CA!"); 
    } catch (AcmPcaException ex) {
      System.err.println(ex.awsErrorDetails().errorMessage());
    }
  }
}
// snippet-end:[acmpca.java2.UntagCertificateAuthority.main]

