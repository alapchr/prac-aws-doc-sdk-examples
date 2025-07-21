// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import java.util.ArrayList;
import java.util.List;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.Tag;
import software.amazon.awssdk.services.acmpca.model.TagCertificateAuthorityRequest;

// snippet-start:[acmpca.java2.TagCertificateAuthorities.main]
/**
 * Before running this Java V2 code example, set up your development environment, including your
 * credentials.
 *
 * <p>For more information, see the following documentation topic:
 *
 * <p>https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class TagCertificateAuthorities {

  public static void main(String[] args) throws Exception {

    final String usage =
        """

                Usage: <regionName> <caArn>

                Where:
                    region - The AWS region (e.g. us-east-1)
                    caArn - The ARN of the certificate authority
                """;

    if (args.length != 2) {
      System.out.println(usage);
      return;
    }

    String regionName = args[0];
    String caArn = args[1];

    // Define the region for your sample.
    Region region = Region.of(regionName);

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(region).build();

    // Create a tag - method 1
    /*
     *  Replace the parameter for KEY and VALUE with your appropriate information
     */
    Tag tag1 = Tag.builder().key("Administrator").value("Bob").build();

    // Create a tag - method 2
    /*
     *  Replace the parameter for KEY and VALUE with your appropriate information
     */
    Tag tag2 = Tag.builder().key("Purpose").value("WebServices").build();

    // Add the tags to a collection.
    List<Tag> tags = new ArrayList<>();
    tags.add(tag1);
    tags.add(tag2);

    // Create a request object and specify the certificate authority ARN and tags.
    TagCertificateAuthorityRequest req =
        TagCertificateAuthorityRequest.builder().certificateAuthorityArn(caArn).tags(tags).build();

    // Add tags
    try {
      client.tagCertificateAuthority(req);
    } catch (AcmPcaException e) {
      throw e;
    }
  }
}
// snippet-end:[acmpca.java2.TagCertificateAuthorities.main]
