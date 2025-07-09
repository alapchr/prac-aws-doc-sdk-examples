// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCertificateResponse;

// snippet-start:[acmpca.java2.GetCertificateAuthorityCertificate.main] 
/**
 * Before running this Java V2 code example, set up your development
 * environment, including your credentials.
 * 
 * For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */

public class GetCertificateAuthorityCertificate {
   
   public static void main(String[] args) throws Exception {

      final String usage = """
         
         Usage: <regionName> <caArn>

         Where: 
            regionName - The AWS region (e.g. us-east-1)
            caArn - The ARN of the certificate authority 
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

      // Create a request object with the Certificate Authority Arn
      GetCertificateAuthorityCertificateRequest req = GetCertificateAuthorityCertificateRequest.builder()
            .certificateAuthorityArn(caArn)
            .build();

      // Create a result object.
      GetCertificateAuthorityCertificateResponse result = client.getCertificateAuthorityCertificate(req);

      // Retrieve and display the certificate information.
      String strPcaCert = result.certificate();
      System.out.println(strPcaCert);
      String strPCACChain = result.certificateChain();
      System.out.println(strPCACChain);
   }
}
// snippet-end:[acmpca.java2.GetCertificateAuthorityCertificate.main] 
