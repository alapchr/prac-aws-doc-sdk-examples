// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca.scenarios;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.model.KeyAlgorithm;
import software.amazon.awssdk.services.acmpca.model.RevocationConfiguration;
import software.amazon.awssdk.services.acmpca.model.SigningAlgorithm;
import software.amazon.awssdk.services.acmpca.model.Validity;
import software.amazon.awssdk.services.acmpca.waiters.AcmPcaWaiter;

import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.ASN1Subject;
import software.amazon.awssdk.services.acmpca.model.CrlConfiguration;

import software.amazon.awssdk.services.acmpca.model.CertificateAuthorityConfiguration;
import software.amazon.awssdk.services.acmpca.model.CertificateAuthorityType;
import software.amazon.awssdk.services.acmpca.model.CreateCertificateAuthorityRequest;
import software.amazon.awssdk.services.acmpca.model.CreateCertificateAuthorityResponse;
import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCertificateResponse;
import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCsrRequest;
import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCsrResponse;
import software.amazon.awssdk.services.acmpca.model.GetCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.GetCertificateResponse;
import software.amazon.awssdk.services.acmpca.model.IssueCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.IssueCertificateResponse;
import software.amazon.awssdk.services.acmpca.model.ImportCertificateAuthorityCertificateRequest;

// snippet-start:[acmpca.java2.SubordinateCAActivation.main]
/**
 * Before running this Java V2 code example, set up your development 
 * environment, including your credentials.
 * 
 * For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class SubordinateCAActivation {

    public static void main(String[] args) throws Exception {
        
      final String usage =
          """
              Usage: <region> <rootCAArn> <s3BucketName>

              Where:
                  region - The AWS region (e.g. us-east-1)
                  rootArn - The ARN of the rootCA 
                  s3BucketName - The name of your bucket for CRL revocation
              """;

      if (args.length != 3) {
        System.out.println(usage);
        return;
      }

      String region = args[0];
      String rootArn = args[1];
      String s3BucketName = args[2];
      
      // Place your own Root CA ARN here.
      String rootCAArn = rootArn;

      // Define a CA subject.
      ASN1Subject subject = 
          ASN1Subject.builder()
              .organization("Example Organization")
              .organizationalUnit("Example")
              .country("US")
              .state("Virginia")
              .locality("Arlington")
              .commonName("www.example.com")
              .build();

      // Define the CA configuration.
      CertificateAuthorityConfiguration configCA = 
          CertificateAuthorityConfiguration.builder()
              .keyAlgorithm(KeyAlgorithm.RSA_2048)
              .signingAlgorithm(SigningAlgorithm.SHA256_WITHRSA)
              .subject(subject)
              .build();

      // Define a certificate revocation list configuration.
      CrlConfiguration crlConfigure = 
          CrlConfiguration.builder()
              .enabled(true)
              .expirationInDays(365)
              .customCname(null)
              .s3BucketName(s3BucketName)
              .build();

      // Define a certificate authority type.
      CertificateAuthorityType CAtype = CertificateAuthorityType.SUBORDINATE;

      // ** Execute core code samples for Subordinate CA activation in sequence **
      AcmPcaClient client = createClient(region);
      String rootCertificate = getCertificateAuthorityCertificate(rootCAArn, client);
      String subordinateCAArn = createCertificateAuthority(configCA, crlConfigure, CAtype, client);
      String csr = getCertificateAuthorityCsr(subordinateCAArn, client);
      String subordinateCertificateArn = issueCertificate(rootCAArn, csr, client);
      String subordinateCertificate = getCertificate(subordinateCertificateArn, rootCAArn, client);
      importCertificateAuthorityCertificate(subordinateCertificate, rootCertificate, subordinateCAArn, client);
    }

    private static AcmPcaClient createClient(String region) {
      AcmPcaClient client = AcmPcaClient.builder().region(Region.of(region)).build();
      return client;
    }

    private static String getCertificateAuthorityCertificate(String rootCAArn, AcmPcaClient client) {
      // Create a request object.
      GetCertificateAuthorityCertificateRequest getCACertificateRequest =
          GetCertificateAuthorityCertificateRequest.builder()
              .certificateAuthorityArn(rootCAArn)
              .build();
      
      try {
        GetCertificateAuthorityCertificateResponse getCACertificateResult = client.getCertificateAuthorityCertificate(getCACertificateRequest);
        // Retrieve and display the certificate information.
        String rootCertificate = getCACertificateResult.certificate();
        System.out.println("Root CA Certificate / Certificate Chain:");
        System.out.println(rootCertificate);
        return rootCertificate;
      } catch (AcmPcaException ex) {
        System.err.println(ex.awsErrorDetails().errorMessage());
        throw new RuntimeException("Certificate Authority creation failed", ex);
      } 
    }

    private static String createCertificateAuthority(CertificateAuthorityConfiguration configCA, CrlConfiguration crlConfigure, CertificateAuthorityType CAtype, AcmPcaClient client) {
      RevocationConfiguration revokeConfig = 
        RevocationConfiguration.builder()
            .crlConfiguration(crlConfigure)
            .build();

      // Create the request object.
      CreateCertificateAuthorityRequest createCARequest = 
          CreateCertificateAuthorityRequest.builder()
              .certificateAuthorityConfiguration(configCA)
              .revocationConfiguration(revokeConfig)
              .idempotencyToken("123987")
              .certificateAuthorityType(CAtype)
              .build();

      try {
        CreateCertificateAuthorityResponse createCAResult = client.createCertificateAuthority(createCARequest);
        // Retrieve the ARN of the private CA.
        String subordinateCAArn = createCAResult.certificateAuthorityArn();
        System.out.println("Subordinate CA Arn: " + subordinateCAArn);
        return subordinateCAArn;
      } catch (AcmPcaException ex) {
        System.err.println(ex.awsErrorDetails().errorMessage());
        throw new RuntimeException("Certificate Authority creation failed", ex);
      }     
    }

    private static String getCertificateAuthorityCsr(String subordinateCAArn, AcmPcaClient client) {
      // Create the CSR request object.
      GetCertificateAuthorityCsrRequest csrRequest = 
          GetCertificateAuthorityCsrRequest.builder()
              .certificateAuthorityArn(subordinateCAArn)
              .build();

      // Create waiter to wait on successful creation of the CSR file.
      try (AcmPcaWaiter waiter = client.waiter()) {
        waiter.waitUntilCertificateAuthorityCSRCreated(
            b -> b.certificateAuthorityArn(csrRequest.certificateAuthorityArn()).build());
      } catch (AcmPcaException ex) {
        System.err.println(ex.awsErrorDetails().errorMessage());
      }

      try {
        GetCertificateAuthorityCsrResponse csrResult = client.getCertificateAuthorityCsr(csrRequest);
        // Retrieve and display the CSR.
        String csr = csrResult.csr();
        System.out.println("Subordinate CSR:");
        System.out.println(csr);
        return csr;
      } catch (AcmPcaException ex) {
        System.err.println(ex.awsErrorDetails().errorMessage());
        throw new RuntimeException("Failed to get Certificate Authority CSR", ex);
      }
    }

    private static String issueCertificate(String rootCAArn, String csr, AcmPcaClient client) {
      
      SdkBytes csrSdkBytes = SdkBytes.fromUtf8String(csr);

      // Create a certificate request.
      IssueCertificateRequest issueRequest = 
          IssueCertificateRequest.builder()
              .certificateAuthorityArn(rootCAArn)
              .templateArn("arn:aws:acm-pca:::template/SubordinateCACertificate_PathLen0/V1")
              .signingAlgorithm(SigningAlgorithm.SHA256_WITHRSA)
              .validity(Validity.builder().value(730L).type("DAYS").build())
              .idempotencyToken("1234")
              .csr(csrSdkBytes)
              .build();

      try {
        IssueCertificateResponse issueResult = client.issueCertificate(issueRequest);
        // Retrieve and display the certificate ARN.
        String subordinateCertificateArn = issueResult.certificateArn();
        System.out.println("Subordinate Certificate Arn: " + subordinateCertificateArn);
        return subordinateCertificateArn;
      } catch (AcmPcaException ex) {
        System.err.println(ex.awsErrorDetails().errorMessage());
        throw new RuntimeException("Failed to issue certificate", ex);
      }
    }

    private static String getCertificate(String subordinateCertificateArn, String rootCAArn, AcmPcaClient client) {
      // Create a request object.
      GetCertificateRequest certificateRequest =
          GetCertificateRequest.builder()
              .certificateArn(subordinateCertificateArn)
              .certificateAuthorityArn(rootCAArn)
              .build();
              
      // Create waiter to wait on successful creation of the certificate file.
      try (AcmPcaWaiter waiter = client.waiter()) {
        waiter.waitUntilCertificateIssued(
            b -> b.certificateArn(certificateRequest.certificateArn()).certificateAuthorityArn(certificateRequest.certificateAuthorityArn()).build());
      } catch (AcmPcaException ex) {
        System.err.println(ex.awsErrorDetails().errorMessage());
      }

      try {
        GetCertificateResponse certificateResult = client.getCertificate(certificateRequest);
        // Get the certificate and certificate chain and display the result.
        String subordinateCertificate = certificateResult.certificate();
        System.out.println("Subordinate CA Certificate:");
        System.out.println(subordinateCertificate);
        return subordinateCertificate;
      } catch (AcmPcaException ex) {
        System.err.println("Failed to get certificate: " + ex.awsErrorDetails().errorMessage());
        throw new RuntimeException("Failed to get certificate", ex);
      }
    }

    private static void importCertificateAuthorityCertificate(String subordinateCertificate, String rootCertificate, String subordinateCAArn, AcmPcaClient client) {

      SdkBytes certSdkBytes = SdkBytes.fromUtf8String(subordinateCertificate);
      SdkBytes rootSdkBytes = SdkBytes.fromUtf8String(rootCertificate);

      // Create the request object.
      ImportCertificateAuthorityCertificateRequest importRequest =
          ImportCertificateAuthorityCertificateRequest.builder()
              .certificate(certSdkBytes)
              .certificateChain(rootSdkBytes)
              .certificateAuthorityArn(subordinateCAArn)
              .build();

      try {
        client.importCertificateAuthorityCertificate(importRequest);
        System.out.println("Subordinate CA certificate successfully imported.");
        System.out.println("Subordinate CA activated successfully.");
      } catch (AcmPcaException ex) {
        System.err.println(ex.awsErrorDetails().errorMessage());
      }    
    }
}
// snippet-end:[acmpca.java2.SubordinateCAActivation.main]
