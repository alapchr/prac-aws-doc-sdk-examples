// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca.scenarios;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import java.util.Arrays;
import java.util.List;

import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.AcmPcaException;
import software.amazon.awssdk.services.acmpca.model.ASN1Subject;

import software.amazon.awssdk.services.acmpca.model.CertificateAuthorityConfiguration;
import software.amazon.awssdk.services.acmpca.model.CertificateAuthorityType;

import software.amazon.awssdk.services.acmpca.model.CreateCertificateAuthorityRequest;
import software.amazon.awssdk.services.acmpca.model.CreateCertificateAuthorityResponse;
import software.amazon.awssdk.services.acmpca.model.CustomAttribute;
import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCsrRequest;
import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCsrResponse;
import software.amazon.awssdk.services.acmpca.model.GetCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.GetCertificateResponse;
import software.amazon.awssdk.services.acmpca.model.ImportCertificateAuthorityCertificateRequest;

import software.amazon.awssdk.services.acmpca.model.IssueCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.IssueCertificateResponse;
import software.amazon.awssdk.services.acmpca.model.KeyAlgorithm;
import software.amazon.awssdk.services.acmpca.model.SigningAlgorithm;
import software.amazon.awssdk.services.acmpca.model.Validity;
import software.amazon.awssdk.services.acmpca.waiters.AcmPcaWaiter;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.io.pem.PemReader;
import java.util.Base64; 

import lombok.SneakyThrows;

// snippet-start:[acmpca.CreatePrivateCertificateAuthorityAD.main]
/**
 * Before running this Java V2 code example, set up your development 
 * environment, including your credentials.
 * 
 * For more information, see the following documentation topic:
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class CreatePrivateCertificateAuthorityAD{
    public static void main(String[] args) throws Exception {
        final String usage =
            """
                Usage: <region> 

                Where:
                    region - The AWS region (e.g. us-east-1)
                """;

        if (args.length != 1) {
          System.out.println(usage);
          return;
        }

        String region = args[0];

        // Define custom attributes
        List<CustomAttribute> customAttributes = Arrays.asList(
                CustomAttribute.builder()
                        .objectIdentifier("2.5.4.3") // OID for Common Name
                        .value("root CA")
                .build(),
                CustomAttribute.builder()
                        .objectIdentifier("0.9.2342.19200300.100.1.25") // OID for Domain Component
                        .value("example")
                .build(),
                CustomAttribute.builder()
                        .objectIdentifier("0.9.2342.19200300.100.1.25") // OID for Domain Component
                        .value("com")
                .build()
            
        );

        // Define a CA subject.
        ASN1Subject subject = ASN1Subject.builder()
                .customAttributes(customAttributes)
                .build();

        // Define the CA configuration.
        CertificateAuthorityConfiguration configCA = 
            CertificateAuthorityConfiguration.builder()
                .keyAlgorithm(KeyAlgorithm.EC_PRIME256_V1)
                .signingAlgorithm(SigningAlgorithm.SHA256_WITHECDSA)
                .subject(subject)
                .build();

        // Define a certificate authority type
        CertificateAuthorityType CAtype = CertificateAuthorityType.ROOT;

        // ** Execute core code samples for Root CA activation in sequence **
        AcmPcaClient client = ClientBuilder(region);
        String rootCAArn = CreateCertificateAuthority(configCA, CAtype, client);
        String csr = GetCertificateAuthorityCsr(rootCAArn, client);
        String rootCertificateArn = IssueCertificate(rootCAArn, csr, client);
        String rootCertificate = GetCertificate(rootCertificateArn, rootCAArn, client);
        ImportCertificateAuthorityCertificate(rootCertificate, rootCAArn, client);
    }

    private static AcmPcaClient ClientBuilder(String region) {
        AcmPcaClient client = AcmPcaClient.builder().region(Region.of(region)).build();
        return client;
    }

    private static String CreateCertificateAuthority(CertificateAuthorityConfiguration configCA, CertificateAuthorityType CAtype, AcmPcaClient client) {
        // Create the request object.
        CreateCertificateAuthorityRequest createCARequest = CreateCertificateAuthorityRequest.builder()
                .certificateAuthorityConfiguration(configCA)
                .idempotencyToken("123987")
                .certificateAuthorityType(CAtype)
                .build();
        
        try {
          CreateCertificateAuthorityResponse createCAResult = client.createCertificateAuthority(createCARequest);
          // Retrieve the ARN of the private CA.
          String rootCAArn = createCAResult.certificateAuthorityArn();
          System.out.println("Root CA Arn: " + rootCAArn);
          return rootCAArn;
        } catch (AcmPcaException ex) {
          System.err.println("Failed to create CA: " + ex.awsErrorDetails().errorMessage());
          throw new RuntimeException("Certificate Authority creation failed", ex);
        } 
    }

    private static String GetCertificateAuthorityCsr(String rootCAArn, AcmPcaClient client) {

        // Create the CSR request object.
        GetCertificateAuthorityCsrRequest csrRequest = 
            GetCertificateAuthorityCsrRequest.builder()
                .certificateAuthorityArn(rootCAArn)
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
          System.out.println(csr);
          return csr;
        } catch (AcmPcaException ex) {
          System.err.println("Failed to get CSR: " + ex.awsErrorDetails().errorMessage());
          throw new RuntimeException("Failed to get Certificate Authority CSR", ex);
        }
    }

    private static String IssueCertificate(String rootCAArn, String csr, AcmPcaClient client) {

        SdkBytes csrSdkBytes = SdkBytes.fromUtf8String(csr);

        // Create a certificate request.
        IssueCertificateRequest issueRequest = 
            IssueCertificateRequest.builder()
                .certificateAuthorityArn(rootCAArn)
                .templateArn("arn:aws:acm-pca:::template/RootCACertificate/V1")
                .signingAlgorithm(SigningAlgorithm.SHA256_WITHECDSA)
                .validity(Validity.builder().value(3650L).type("DAYS").build())
                .idempotencyToken("1234")
                .csr(csrSdkBytes)
                .build();

        try {
          IssueCertificateResponse issueResult = client.issueCertificate(issueRequest);
          // Retrieve and display the certificate ARN.
          String rootCertificateArn = issueResult.certificateArn();
          System.out.println("Root Certificate Arn: " + rootCertificateArn);
          return rootCertificateArn;
        } catch (AcmPcaException ex) {
          System.err.println("Failed to issue certificate: " + ex.awsErrorDetails().errorMessage());
          throw new RuntimeException("Failed to issue certificate", ex);
        }
    }
    
    private static String GetCertificate(String rootCertificateArn, String rootCAArn, AcmPcaClient client) {

        // Create a request object.
        GetCertificateRequest certificateRequest =
             GetCertificateRequest.builder()
                .certificateArn(rootCertificateArn)
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
          String rootCertificate = certificateResult.certificate();
          System.out.println(rootCertificate);
          return rootCertificate;
        } catch (AcmPcaException ex) {
          System.err.println("Failed to get certificate: " + ex.awsErrorDetails().errorMessage());
          throw new RuntimeException("Failed to get certificate", ex);
        }
    }

    private static void ImportCertificateAuthorityCertificate(String rootCertificate, String rootCAArn, AcmPcaClient client) {
        
        SdkBytes certSdkBytes = SdkBytes.fromUtf8String(rootCertificate);

        // Create the request object.
        ImportCertificateAuthorityCertificateRequest importRequest =
            ImportCertificateAuthorityCertificateRequest.builder()
                .certificate(certSdkBytes)
                .certificateChain(null)
                .certificateAuthorityArn(rootCAArn)
                .build();

        try {
          client.importCertificateAuthorityCertificate(importRequest);
          System.out.println("Root CA certificate successfully imported.");
          System.out.println("Root CA activated successfully.");
        } catch (AcmPcaException ex) {
          System.err.println(ex.awsErrorDetails().errorMessage());
        }
    }
}
// snippet-end:[acmpca.CreatePrivateCertificateAuthorityAD.main]
