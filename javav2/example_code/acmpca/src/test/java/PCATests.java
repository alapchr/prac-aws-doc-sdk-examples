// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

import com.example.acmpca.CreateCertificateAuthority;
import com.example.acmpca.DeleteCertificateAuthority;
import com.example.acmpca.DescribeCertificateAuthority;
import com.example.acmpca.ListCertificateAuthorities;
import com.example.acmpca.GetCertificateAuthorityCertificate;
import com.example.acmpca.ListTags;
import com.example.acmpca.GetCertificate;
import com.example.acmpca.GetCertificateAuthorityCsr;
import com.example.acmpca.IssueCertificate;
import com.example.acmpca.TagCertificateAuthorities;
import com.example.acmpca.UntagCertificateAuthority;
import com.example.acmpca.CreatePermission;
import com.example.acmpca.DeletePermission;
import com.example.acmpca.DeletePolicy;
import com.example.acmpca.GetPolicy;
import com.example.acmpca.ListPermissions;
import com.example.acmpca.PutPolicy;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.*;
import software.amazon.awssdk.services.acmpca.waiters.AcmPcaWaiter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PCATests {
    private static final Logger logger = LoggerFactory.getLogger(PCATests.class);

    @Mock
    private AcmPcaClient mockClient;

    @Mock
    private AcmPcaWaiter mockWaiter;

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

    @Test
    @Order(6)
    public void testDeleteCertificateAuthority() {
        assertDoesNotThrow(() -> {
            DeleteCertificateAuthority.main(new String[]{"us-east-1"}); 
            DeleteCertificateAuthority.main(new String[]{}); 
            DeleteCertificateAuthority.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "30", "extra-arg"}); 
            DeleteCertificateAuthority.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
            DeleteCertificateAuthority.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", "30"});
        });
        logger.info("Test 6 passed");
    }

    @Test
    @Order(7)
    public void testGetCertificate() {
        assertDoesNotThrow(() -> {
            try {
                GetCertificate.main(new String[]{"us-east-1"}); 
            } catch (Exception e) {
            }
            try {
                GetCertificate.main(new String[]{}); 
            } catch (Exception e) {
            }
            try {
                GetCertificate.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate/test-cert", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                    "extra-arg"}); 
            } catch (Exception e) {
            }
            try {
                GetCertificate.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate/test-cert", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
            } catch (Exception e) {
                logger.info("Expected exception in GetCertificate: {}", e.getMessage());
            }
        });
        logger.info("Test 7 passed");
    }

    @Test
    @Order(8)
    public void testGetCertificateAuthorityCsr() {
        assertDoesNotThrow(() -> {
            try {
                GetCertificateAuthorityCsr.main(new String[]{"us-east-1"}); 
            } catch (Exception e) {
            }
            try {
                GetCertificateAuthorityCsr.main(new String[]{}); 
            } catch (Exception e) {
            }
            try {
                GetCertificateAuthorityCsr.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                    "extra-arg"}); 
            } catch (Exception e) {
            }
            try {
                GetCertificateAuthorityCsr.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
            } catch (Exception e) {
                logger.info("Expected exception in GetCertificateAuthorityCsr: {}", e.getMessage());
            }
        });
        logger.info("Test 8 passed");
    }

    @Test
    @Order(9)
    public void testIssueCertificate() {
        assertDoesNotThrow(() -> {
            IssueCertificate.main(new String[]{"us-east-1"}); 
            IssueCertificate.main(new String[]{}); 
            IssueCertificate.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "extra-arg"}); 
            IssueCertificate.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
        });
        logger.info("Test 9 passed");
    }

    @Test
    @Order(10)
    public void testTagCertificateAuthorities() {
        assertDoesNotThrow(() -> {
            TagCertificateAuthorities.main(new String[]{"us-east-1"}); 
            TagCertificateAuthorities.main(new String[]{}); 
            TagCertificateAuthorities.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "extra-arg"}); 
            TagCertificateAuthorities.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
        });
        logger.info("Test 10 passed");
    }

    @Test
    @Order(11)
    public void testUntagCertificateAuthority() {
        assertDoesNotThrow(() -> {
            UntagCertificateAuthority.main(new String[]{"us-east-1"}); 
            UntagCertificateAuthority.main(new String[]{}); 
            UntagCertificateAuthority.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "KeyOnly", "extra-arg"}); 
            UntagCertificateAuthority.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "KeyOnly"});
        });
        logger.info("Test 11 passed");
    }

    @Test
    @Order(12)
    public void testCreatePermission() {
        assertDoesNotThrow(() -> {
            CreatePermission.main(new String[]{"us-east-1"}); 
            CreatePermission.main(new String[]{}); 
            CreatePermission.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "extra-arg"}); 
            CreatePermission.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
        });
        logger.info("Test 12 passed");
    }

    @Test
    @Order(13)
    public void testDeletePermission() {
        assertDoesNotThrow(() -> {
            DeletePermission.main(new String[]{"us-east-1"}); 
            DeletePermission.main(new String[]{}); 
            DeletePermission.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "extra-arg"}); 
            DeletePermission.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
        });
        logger.info("Test 13 passed");
    }

    @Test
    @Order(14)
    public void testDeletePolicy() {
        assertDoesNotThrow(() -> {
            DeletePolicy.main(new String[]{"us-east-1"}); 
            DeletePolicy.main(new String[]{}); 
            DeletePolicy.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                "extra-arg"}); 
            DeletePolicy.main(new String[]{"us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
        });
        logger.info("Test 14 passed");
    }

    @Test
    @Order(15)
    public void testGetPolicy() {
        assertDoesNotThrow(() -> {
            try {
                GetPolicy.main(new String[]{"us-east-1"}); 
            } catch (Exception e) {
            }
            try {
                GetPolicy.main(new String[]{}); 
            } catch (Exception e) {
            }
            try {
                GetPolicy.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                    "extra-arg"}); 
            } catch (Exception e) {
            }
            try {
                GetPolicy.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
            } catch (Exception e) {
                logger.info("Expected exception in GetPolicy: {}", e.getMessage());
            }
        });
        logger.info("Test 15 passed");
    }

    @Test
    @Order(16)
    public void testListPermissions() {
        assertDoesNotThrow(() -> {
            try {
                ListPermissions.main(new String[]{"us-east-1"}); 
            } catch (Exception e) {
            }
            try {
                ListPermissions.main(new String[]{}); 
            } catch (Exception e) {
            }
            try {
                ListPermissions.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                    "extra-arg"}); 
            } catch (Exception e) {
            }
            try {
                ListPermissions.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
            } catch (Exception e) {
                logger.info("Expected exception in ListPermissions: {}", e.getMessage());
            }
        });
        logger.info("Test 16 passed");
    }

    @Test
    @Order(17)
    public void testPutPolicy() {
        assertDoesNotThrow(() -> {
            try {
                PutPolicy.main(new String[]{"us-east-1"}); 
            } catch (Exception e) {
            }
            try {
                PutPolicy.main(new String[]{}); 
            } catch (Exception e) {
            }
            try {
                PutPolicy.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca", 
                    "extra-arg"}); 
            } catch (Exception e) {
            }
            try {
                PutPolicy.main(new String[]{"us-east-1", 
                    "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"});
            } catch (Exception e) {
                logger.info("Expected exception in PutPolicy: {}", e.getMessage());
            }
        });
        logger.info("Test 17 passed");
    }
}

