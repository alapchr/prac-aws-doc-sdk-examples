// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import java.util.ArrayList;
import java.util.List;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.ASN1Subject;
import software.amazon.awssdk.services.acmpca.model.CertificateAuthorityConfiguration;
import software.amazon.awssdk.services.acmpca.model.CertificateAuthorityType;
import software.amazon.awssdk.services.acmpca.model.CreateCertificateAuthorityRequest;
import software.amazon.awssdk.services.acmpca.model.CreateCertificateAuthorityResponse;
import software.amazon.awssdk.services.acmpca.model.CrlConfiguration;

import software.amazon.awssdk.services.acmpca.model.KeyAlgorithm;
import software.amazon.awssdk.services.acmpca.model.RevocationConfiguration;
import software.amazon.awssdk.services.acmpca.model.SigningAlgorithm;
import software.amazon.awssdk.services.acmpca.model.Tag;

// snippet-start:[acmpca.java2.CreateCA.main]

/**
 * Before running this Java V2 code example, set up your development
 * environment, including your credentials.
 * <p>
 * For more information, see the following documentation topic:
 * <p>
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */


public class CreateCertificateAuthority {

    public static void main(String[] args) throws Exception {
       
        // Define the region for your sample.
        Region region = Region.US_EAST_1;  // Change to your region.
        
        // Create a client that you can use to make requests.
        AcmPcaClient client = AcmPcaClient.builder()
            .region(region)
            .build();
    
        // Define a CA subject.
        /*
         * Replace organization, organizationalUnit, country, state, locality, and commonName 
         * to the appropriate information for you
         */
        ASN1Subject subject = ASN1Subject.builder()
            .organization("Example Organization") 
            .organizationalUnit("Example")
            .country("US") 
            .state("Virginia") 
            .locality("Arlington") 
            .commonName("www.example.com") 
            .build();

        // Define the CA configuration.
        CertificateAuthorityConfiguration configCA = CertificateAuthorityConfiguration.builder()
            .keyAlgorithm(KeyAlgorithm.RSA_2048)
            .signingAlgorithm(SigningAlgorithm.SHA256_WITHRSA)
            .subject(subject)
            .build();

      // Define a certificate revocation list configuration 
         CrlConfiguration crlConfigure = CrlConfiguration.builder()
            .enabled(true)
            .expirationInDays(365)
            .customCname(null)
            .s3BucketName("your-bucket-name")
            .build();
       
         RevocationConfiguration revokeConfig = RevocationConfiguration.builder()
            .crlConfiguration(crlConfigure)
            .build(); 

        // Define a certificate authority type: ROOT or SUBORDINATE
        CertificateAuthorityType CAtype = CertificateAuthorityType.ROOT;
      
        // Create a tag - method 1
        /*
         * Replace the parameter for key and value with your appropritate information 
         */
        Tag tag1 = Tag.builder()
            .key("PrivateCA") 
            .value("Sample") 
            .build();
      
        // Create a tag - method 2
        /*
         *  Replace the parameter for key and value with your appropriate information 
        */
        Tag tag2 = Tag.builder()
            .key("Purpose") 
            .value("WebServices") 
            .build();
      
        // Add the tags to a collection.
        List<Tag> tags = new ArrayList<>();      
        tags.add(tag1);
        tags.add(tag2);
      
        // Create the request object.
        CreateCertificateAuthorityRequest req = CreateCertificateAuthorityRequest.builder()
            .certificateAuthorityConfiguration(configCA)
            .revocationConfiguration(revokeConfig) 
            .idempotencyToken("123987")
            .certificateAuthorityType(CAtype)
            .tags(tags)
            .build();
     
        // Create the private CA.
        CreateCertificateAuthorityResponse result = client.createCertificateAuthority(req);


        // Retrieve the ARN of the private CA.
        String arn = result.certificateAuthorityArn();
        System.out.println(arn);
    }
}

// snippet-end:[acmpca.java2.CreateCA.main] 