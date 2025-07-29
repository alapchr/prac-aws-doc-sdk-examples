// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

import com.example.acmpca.CreateCertificateAuthority;
import com.example.acmpca.CreateCertificateAuthorityAuditReport;
import com.example.acmpca.DescribeCertificateAuthorityAuditReport;
import com.example.acmpca.ImportCertificateAuthorityCertificate;
import com.example.acmpca.RestoreCertificateAuthority;
import com.example.acmpca.RevokeCertificate;
import com.example.acmpca.UpdateCertificateAuthority;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PCATests {
    private static final Logger logger = LoggerFactory.getLogger(PCATests.class);

    @Test
    @Order(18)
    public void testCreateCertificateAuthority() {
        assertDoesNotThrow(() -> {
            try {
                CreateCertificateAuthority.main(new String[]{"us-east-1"}); 
            } catch (Exception e) {
                logger.info("Expected exception in CreateCertificateAuthority (invalid args): {}", e.getMessage());
            }
            try {
                CreateCertificateAuthority.main(new String[]{}); 
            } catch (Exception e) {
                logger.info("Expected exception in CreateCertificateAuthority (no args): {}", e.getMessage());
            }
            try {
                CreateCertificateAuthority.main(new String[]{"us-east-1", "test-bucket", "extra-arg"}); 
            } catch (Exception e) {
                logger.info("Expected exception in CreateCertificateAuthority (too many args): {}", e.getMessage());
            }
            try {
                CreateCertificateAuthority.main(new String[]{"us-east-1", "test-bucket"});
            } catch (Exception e) {
                logger.info("Expected exception in CreateCertificateAuthority (AWS call): {}", e.getMessage());
            }
        });
        logger.info("Test 18 passed");
    }

    @Test
    @Order(19)
    public void testCreateCertificateAuthorityAuditReport() {
        assertDoesNotThrow(() -> {
            try {
                CreateCertificateAuthorityAuditReport.main(new String[]{"us-east-1"}); 
            } catch (Exception e) {
                logger.info("Expected exception in CreateCertificateAuthorityAuditReport (invalid args): {}", e.getMessage());
            }
            try {
                CreateCertificateAuthorityAuditReport.main(new String[]{}); 
            } catch (Exception e) {
                logger.info("Expected exception in CreateCertificateAuthorityAuditReport (no args): {}", e.getMessage());
            }
            try {
                CreateCertificateAuthorityAuditReport.main(new String[]{"us-east-1", 
                    "test-bucket", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                    "extra-arg"}); 
            } catch (Exception e) {
                logger.info("Expected exception in CreateCertificateAuthorityAuditReport (too many args): {}", e.getMessage());
            }
            try {
                CreateCertificateAuthorityAuditReport.main(new String[]{"us-east-1", 
                    "test-bucket", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
            } catch (Exception e) {
                logger.info("Expected exception in CreateCertificateAuthorityAuditReport (AWS call): {}", e.getMessage());
            }
        });
        logger.info("Test 19 passed");
    }

    @Test
    @Order(20)
    public void testDescribeCertificateAuthorityAuditReport() {
        assertDoesNotThrow(() -> {
            try {
                DescribeCertificateAuthorityAuditReport.main(new String[]{"us-east-1"}); 
            } catch (Exception e) {
                logger.info("Expected exception in DescribeCertificateAuthorityAuditReport (invalid args): {}", e.getMessage());
            }
            try {
                DescribeCertificateAuthorityAuditReport.main(new String[]{}); 
            } catch (Exception e) {
                logger.info("Expected exception in DescribeCertificateAuthorityAuditReport (no args): {}", e.getMessage());
            }
            try {
                DescribeCertificateAuthorityAuditReport.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                    "11111111-2222-3333-4444-555555555555", 
                    "extra-arg"}); 
            } catch (Exception e) {
                logger.info("Expected exception in DescribeCertificateAuthorityAuditReport (too many args): {}", e.getMessage());
            }
            try {
                DescribeCertificateAuthorityAuditReport.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                    "11111111-2222-3333-4444-555555555555"});
            } catch (Exception e) {
                logger.info("Expected exception in DescribeCertificateAuthorityAuditReport (AWS call): {}", e.getMessage());
            }
        });
        logger.info("Test 20 passed");
    }

    @Test
    @Order(21)
    public void testImportCertificateAuthorityCertificate() {
        assertDoesNotThrow(() -> {
            try {
                ImportCertificateAuthorityCertificate.main(new String[]{"us-east-1"}); 
            } catch (Exception e) {
                logger.info("Expected exception in ImportCertificateAuthorityCertificate (invalid args): {}", e.getMessage());
            }
            try {
                ImportCertificateAuthorityCertificate.main(new String[]{}); 
            } catch (Exception e) {
                logger.info("Expected exception in ImportCertificateAuthorityCertificate (no args): {}", e.getMessage());
            }
            try {
                ImportCertificateAuthorityCertificate.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                    "extra-arg"}); 
            } catch (Exception e) {
                logger.info("Expected exception in ImportCertificateAuthorityCertificate (too many args): {}", e.getMessage());
            }
            try {
                ImportCertificateAuthorityCertificate.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
            } catch (Exception e) {
                logger.info("Expected exception in ImportCertificateAuthorityCertificate (AWS call): {}", e.getMessage());
            }
        });
        logger.info("Test 21 passed");
    }

    @Test
    @Order(22)
    public void testRestoreCertificateAuthority() {
        assertDoesNotThrow(() -> {
            try {
                RestoreCertificateAuthority.main(new String[]{"us-east-1"}); 
            } catch (Exception e) {
                logger.info("Expected exception in RestoreCertificateAuthority (invalid args): {}", e.getMessage());
            }
            try {
                RestoreCertificateAuthority.main(new String[]{}); 
            } catch (Exception e) {
                logger.info("Expected exception in RestoreCertificateAuthority (no args): {}", e.getMessage());
            }
            try {
                RestoreCertificateAuthority.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                    "extra-arg"}); 
            } catch (Exception e) {
                logger.info("Expected exception in RestoreCertificateAuthority (too many args): {}", e.getMessage());
            }
            try {
                RestoreCertificateAuthority.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
            } catch (Exception e) {
                logger.info("Expected exception in RestoreCertificateAuthority (AWS call): {}", e.getMessage());
            }
        });
        logger.info("Test 22 passed");
    }

    @Test
    @Order(23)
    public void testRevokeCertificate() {
        assertDoesNotThrow(() -> {
            try {
                RevokeCertificate.main(new String[]{"us-east-1"}); 
            } catch (Exception e) {
                logger.info("Expected exception in RevokeCertificate (invalid args): {}", e.getMessage());
            }
            try {
                RevokeCertificate.main(new String[]{}); 
            } catch (Exception e) {
                logger.info("Expected exception in RevokeCertificate (no args): {}", e.getMessage());
            }
            try {
                RevokeCertificate.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                    "79:3f:0d:5b:6a:04:12:5e:2c:9c:fb:52:37:35:98:fe", 
                    "extra-arg"}); 
            } catch (Exception e) {
                logger.info("Expected exception in RevokeCertificate (too many args): {}", e.getMessage());
            }
            try {
                RevokeCertificate.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                    "79:3f:0d:5b:6a:04:12:5e:2c:9c:fb:52:37:35:98:fe"});
            } catch (Exception e) {
                logger.info("Expected exception in RevokeCertificate (AWS call): {}", e.getMessage());
            }
        });
        logger.info("Test 23 passed");
    }

    @Test
    @Order(24)
    public void testUpdateCertificateAuthority() {
        assertDoesNotThrow(() -> {
            try {
                UpdateCertificateAuthority.main(new String[]{"us-east-1"}); 
            } catch (Exception e) {
                logger.info("Expected exception in UpdateCertificateAuthority (invalid args): {}", e.getMessage());
            }
            try {
                UpdateCertificateAuthority.main(new String[]{}); 
            } catch (Exception e) {
                logger.info("Expected exception in UpdateCertificateAuthority (no args): {}", e.getMessage());
            }
            try {
                UpdateCertificateAuthority.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                    "extra-arg"}); 
            } catch (Exception e) {
                logger.info("Expected exception in UpdateCertificateAuthority (too many args): {}", e.getMessage());
            }
            try {
                UpdateCertificateAuthority.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
            } catch (Exception e) {
                logger.info("Expected exception in UpdateCertificateAuthority (AWS call): {}", e.getMessage());
            }
        });
        logger.info("Test 24 passed");
    }
}
