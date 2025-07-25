// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

import com.example.acmpca.CreateCertificateAuthority;
import com.example.acmpca.DescribeCertificateAuthority;
import com.example.acmpca.GetCertificateAuthorityCertificate;
import com.example.acmpca.ListCertificateAuthorities;
import com.example.acmpca.ListTags;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PCATests {
    private static final Logger logger = LoggerFactory.getLogger(PCATests.class);

    @Test
    @Order(1)
    public void testCreateCertificateAuthority() {
        assertDoesNotThrow(() -> {
            CreateCertificateAuthority.main(new String[]{"us-east-1"}); 
            CreateCertificateAuthority.main(new String[]{}); 
            CreateCertificateAuthority.main(new String[]{"us-east-1", "test-bucket", "extra-arg"}); 
            CreateCertificateAuthority.main(new String[]{"us-east-1", "test-bucket"});
        });
        logger.info("Test 1 passed");
    }

    @Test
    @Order(2)
    public void testDescribeCertificateAuthority() {
        assertDoesNotThrow(() -> {
            DescribeCertificateAuthority.main(new String[]{"us-east-1"}); 
            DescribeCertificateAuthority.main(new String[]{}); 
            DescribeCertificateAuthority.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "extra-arg"}); 
            DescribeCertificateAuthority.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
        });
        logger.info("Test 2 passed");
    }

    @Test
    @Order(3)
    public void testListCertificateAuthorities() {
        assertDoesNotThrow(() -> {
            ListCertificateAuthorities.main(new String[]{}); 
            ListCertificateAuthorities.main(new String[]{"us-east-1", "extra-arg"}); 
            ListCertificateAuthorities.main(new String[]{"us-east-1"});
        });
        logger.info("Test 3 passed");
    }

    @Test
    @Order(4)
    public void testGetCertificateAuthorityCertificate() {
        assertDoesNotThrow(() -> {
            GetCertificateAuthorityCertificate.main(new String[]{"us-east-1"}); 
            GetCertificateAuthorityCertificate.main(new String[]{}); 
            GetCertificateAuthorityCertificate.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "extra-arg"}); 
            GetCertificateAuthorityCertificate.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
        });
        logger.info("Test 4 passed");
    }

    @Test
    @Order(5)
    public void testListTags() {
        assertDoesNotThrow(() -> {
            ListTags.main(new String[]{"us-east-1"}); 
            ListTags.main(new String[]{}); 
            ListTags.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "extra-arg"}); 
            ListTags.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
        });
        logger.info("Test 5 passed");
    }
}
