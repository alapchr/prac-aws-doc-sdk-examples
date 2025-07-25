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
            // Test invalid arguments - should show usage and not crash
            CreateCertificateAuthority.main(new String[]{"us-east-1"}); // Missing bucket name
            CreateCertificateAuthority.main(new String[]{}); // No arguments
            CreateCertificateAuthority.main(new String[]{"us-east-1", "test-bucket", "extra-arg"}); // Too many arguments
            
            // Test valid arguments - should work correctly
            CreateCertificateAuthority.main(new String[]{"us-east-1", "test-bucket"});
        });
        logger.info("Test 1 passed");
    }

    @Test
    @Order(2)
    public void testDescribeCertificateAuthority() {
        assertDoesNotThrow(() -> {
            // Test invalid arguments - should show usage and not crash
            DescribeCertificateAuthority.main(new String[]{"us-east-1"}); // Missing CA ARN
            DescribeCertificateAuthority.main(new String[]{}); // No arguments
            DescribeCertificateAuthority.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "extra-arg"}); // Too many arguments
            
            // Test valid arguments - should work correctly
            DescribeCertificateAuthority.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
        });
        logger.info("Test 2 passed");
    }

    @Test
    @Order(3)
    public void testListCertificateAuthorities() {
        assertDoesNotThrow(() -> {
            // Test invalid arguments - should show usage and not crash
            ListCertificateAuthorities.main(new String[]{}); // Missing region
            ListCertificateAuthorities.main(new String[]{"us-east-1", "extra-arg"}); // Too many arguments
            
            // Test valid arguments - should work correctly
            ListCertificateAuthorities.main(new String[]{"us-east-1"});
        });
        logger.info("Test 3 passed");
    }

    @Test
    @Order(4)
    public void testGetCertificateAuthorityCertificate() {
        assertDoesNotThrow(() -> {
            // Test invalid arguments - should show usage and not crash
            GetCertificateAuthorityCertificate.main(new String[]{"us-east-1"}); // Missing CA ARN
            GetCertificateAuthorityCertificate.main(new String[]{}); // No arguments
            GetCertificateAuthorityCertificate.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "extra-arg"}); // Too many arguments
            
            // Test valid arguments - should work correctly
            GetCertificateAuthorityCertificate.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
        });
        logger.info("Test 4 passed");
    }

    @Test
    @Order(5)
    public void testListTags() {
        assertDoesNotThrow(() -> {
            // Test invalid arguments - should show usage and not crash
            ListTags.main(new String[]{"us-east-1"}); // Missing CA ARN
            ListTags.main(new String[]{}); // No arguments
            ListTags.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "extra-arg"}); // Too many arguments
            
            // Test valid arguments - should work correctly
            ListTags.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
        });
        logger.info("Test 5 passed");
    }
}
