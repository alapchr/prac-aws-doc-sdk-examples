// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

import com.example.acmpca.CreateCertificateAuthority;
import com.example.acmpca.CreateCertificateAuthorityAuditReport;
import com.example.acmpca.CreatePermission;
import com.example.acmpca.DeleteCertificateAuthority;
import com.example.acmpca.DeletePermission;
import com.example.acmpca.DeletePolicy;
import com.example.acmpca.DescribeCertificateAuthority;
import com.example.acmpca.DescribeCertificateAuthorityAuditReport;
import com.example.acmpca.GetCertificate;
import com.example.acmpca.GetCertificateAuthorityCertificate;
import com.example.acmpca.GetCertificateAuthorityCsr;
import com.example.acmpca.GetPolicy;
import com.example.acmpca.ImportCertificateAuthorityCertificate;
import com.example.acmpca.IssueCertificate;
import com.example.acmpca.ListCertificateAuthorities;
import com.example.acmpca.ListPermissions;
import com.example.acmpca.ListTags;
import com.example.acmpca.PutPolicy;
import com.example.acmpca.RestoreCertificateAuthority;
import com.example.acmpca.RevokeCertificate;
import com.example.acmpca.TagCertificateAuthorities;
import com.example.acmpca.UntagCertificateAuthority;
import com.example.acmpca.UpdateCertificateAuthority;
import com.example.acmpca.scenarios.CreatePrivateCertificateAuthorityAD;
import com.example.acmpca.scenarios.RootCAActivation;
import com.example.acmpca.scenarios.SubordinateCAActivation;

import software.amazon.awssdk.services.acmpca.model.CreateCertificateAuthorityResponse;
import software.amazon.awssdk.services.acmpca.model.CreateCertificateAuthorityRequest;
import software.amazon.awssdk.services.acmpca.model.CreateCertificateAuthorityAuditReportResponse;
import software.amazon.awssdk.services.acmpca.model.CreateCertificateAuthorityAuditReportRequest;
import software.amazon.awssdk.services.acmpca.model.DescribeCertificateAuthorityResponse;
import software.amazon.awssdk.services.acmpca.model.DescribeCertificateAuthorityRequest;
import software.amazon.awssdk.services.acmpca.model.DescribeCertificateAuthorityAuditReportResponse;
import software.amazon.awssdk.services.acmpca.model.DescribeCertificateAuthorityAuditReportRequest;
import software.amazon.awssdk.services.acmpca.model.CertificateAuthority;
import software.amazon.awssdk.services.acmpca.model.CertificateAuthorityStatus;
import software.amazon.awssdk.services.acmpca.model.CertificateAuthorityType;
import software.amazon.awssdk.services.acmpca.model.ListCertificateAuthoritiesResponse;
import software.amazon.awssdk.services.acmpca.model.ListCertificateAuthoritiesRequest;
import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCertificateResponse;
import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.ListTagsResponse;
import software.amazon.awssdk.services.acmpca.model.ListTagsRequest;
import software.amazon.awssdk.services.acmpca.model.CreatePermissionResponse;
import software.amazon.awssdk.services.acmpca.model.CreatePermissionRequest;
import software.amazon.awssdk.services.acmpca.model.DeletePermissionResponse;
import software.amazon.awssdk.services.acmpca.model.DeletePermissionRequest;
import software.amazon.awssdk.services.acmpca.model.DeletePolicyResponse;
import software.amazon.awssdk.services.acmpca.model.DeletePolicyRequest;
import software.amazon.awssdk.services.acmpca.model.GetPolicyResponse;
import software.amazon.awssdk.services.acmpca.model.GetPolicyRequest;
import software.amazon.awssdk.services.acmpca.model.ListPermissionsResponse;
import software.amazon.awssdk.services.acmpca.model.ListPermissionsRequest;
import software.amazon.awssdk.services.acmpca.model.Permission;
import software.amazon.awssdk.services.acmpca.model.ActionType;
import software.amazon.awssdk.services.acmpca.model.PutPolicyResponse;
import software.amazon.awssdk.services.acmpca.model.PutPolicyRequest;
import software.amazon.awssdk.services.acmpca.model.DeleteCertificateAuthorityResponse;
import software.amazon.awssdk.services.acmpca.model.DeleteCertificateAuthorityRequest;
import software.amazon.awssdk.services.acmpca.model.GetCertificateResponse;
import software.amazon.awssdk.services.acmpca.model.GetCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCsrResponse;
import software.amazon.awssdk.services.acmpca.model.GetCertificateAuthorityCsrRequest;
import software.amazon.awssdk.services.acmpca.model.IssueCertificateResponse;
import software.amazon.awssdk.services.acmpca.model.IssueCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.TagCertificateAuthorityResponse;
import software.amazon.awssdk.services.acmpca.model.TagCertificateAuthorityRequest;
import software.amazon.awssdk.services.acmpca.model.UntagCertificateAuthorityResponse;
import software.amazon.awssdk.services.acmpca.model.UntagCertificateAuthorityRequest;
import software.amazon.awssdk.services.acmpca.model.ImportCertificateAuthorityCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.RestoreCertificateAuthorityRequest;
import software.amazon.awssdk.services.acmpca.model.RevokeCertificateRequest;
import software.amazon.awssdk.services.acmpca.model.UpdateCertificateAuthorityRequest;
import software.amazon.awssdk.services.acmpca.model.AuditReportStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.Tag;
import software.amazon.awssdk.services.acmpca.waiters.AcmPcaWaiter;
import software.amazon.awssdk.services.acmpca.AcmPcaClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PCATests {
    
    @Mock
    private AcmPcaClient mockClient;
    
    @Mock
    private AcmPcaWaiter mockWaiter;
    
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    
    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(errorStream));
    }
    
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        outputStream.reset();
        errorStream.reset();
    }

    @Test
    @Order(1)
    public void testCreateCertificateAuthority() {
        String expectedArn = "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca";
        CreateCertificateAuthorityResponse mockResponse = CreateCertificateAuthorityResponse.builder()
                .certificateAuthorityArn(expectedArn)
                .build();
        
        when(mockClient.createCertificateAuthority(any(CreateCertificateAuthorityRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> CreateCertificateAuthority.main(new String[]{"us-east-1", "test-bucket"}));
            verify(mockClient).createCertificateAuthority(any(CreateCertificateAuthorityRequest.class));
            assertTrue(outputStream.toString().contains(expectedArn), "Should output the certificate authority ARN");
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> CreateCertificateAuthority.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message for invalid arguments");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 1 passed");
    }

    @Test
    @Order(2)
    public void testDescribeCertificateAuthority() {
        CertificateAuthority mockCA = CertificateAuthority.builder()
                .arn("arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca")
                .status(CertificateAuthorityStatus.ACTIVE)
                .type(CertificateAuthorityType.ROOT)
                .build();
        
        DescribeCertificateAuthorityResponse mockResponse = DescribeCertificateAuthorityResponse.builder()
                .certificateAuthority(mockCA)
                .build();
        
        when(mockClient.describeCertificateAuthority(any(DescribeCertificateAuthorityRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> DescribeCertificateAuthority.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).describeCertificateAuthority(any(DescribeCertificateAuthorityRequest.class));
            assertTrue(outputStream.toString().contains("ACTIVE"), "Should output CA status");
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> DescribeCertificateAuthority.main(new String[]{}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
       originalOut.println("Test 2 passed");
    }

    @Test
    @Order(3)
    public void testListCertificateAuthorities() {
        CertificateAuthority mockCA = CertificateAuthority.builder()
                .arn("arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca")
                .status(CertificateAuthorityStatus.ACTIVE)
                .build();
        
        ListCertificateAuthoritiesResponse mockResponse = ListCertificateAuthoritiesResponse.builder()
                .certificateAuthorities(mockCA)
                .build();
        
        when(mockClient.listCertificateAuthorities(any(ListCertificateAuthoritiesRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> ListCertificateAuthorities.main(new String[]{"us-east-1"}));
            verify(mockClient).listCertificateAuthorities(any(ListCertificateAuthoritiesRequest.class));
            assertTrue(outputStream.toString().contains("test-ca"), "Should output CA information");
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> ListCertificateAuthorities.main(new String[]{"us-east-1", "extra-arg"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 3 passed");
    } 

    @Test
    @Order(4)
    public void testGetCertificateAuthorityCertificate() {
        GetCertificateAuthorityCertificateResponse mockResponse = GetCertificateAuthorityCertificateResponse.builder()
                .certificate("-----BEGIN CERTIFICATE-----\nMIIBkTCB+wIJAK...")
                .certificateChain("-----BEGIN CERTIFICATE-----\nMIIBkTCB+wIJAK...")
                .build();
        
        when(mockClient.getCertificateAuthorityCertificate(any(GetCertificateAuthorityCertificateRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> GetCertificateAuthorityCertificate.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).getCertificateAuthorityCertificate(any(GetCertificateAuthorityCertificateRequest.class));
            assertTrue(outputStream.toString().contains("BEGIN CERTIFICATE"), "Should output certificate");
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> GetCertificateAuthorityCertificate.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 4 passed");
    }

    @Test
    @Order(5)
    public void testListTags() {
        Tag mockTag = Tag.builder()
                .key("Environment")
                .value("Test")
                .build();
        
        ListTagsResponse mockResponse = ListTagsResponse.builder()
                .tags(mockTag)
                .build();
        
        when(mockClient.listTags(any(ListTagsRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> ListTags.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).listTags(any(ListTagsRequest.class));
            assertTrue(outputStream.toString().contains("Environment"), "Should output tag information");
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> ListTags.main(new String[]{}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 5 passed");
    }

    @Test
    @Order(6)
    public void testCreatePermission() {
        CreatePermissionResponse mockResponse = CreatePermissionResponse.builder().build();
        
        when(mockClient.createPermission(any(CreatePermissionRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> CreatePermission.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).createPermission(any(CreatePermissionRequest.class));
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> CreatePermission.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 6 passed");
    }

    @Test
    @Order(7)
    public void testDeletePermission() {
        DeletePermissionResponse mockResponse = DeletePermissionResponse.builder().build();
        
        when(mockClient.deletePermission(any(DeletePermissionRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> DeletePermission.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).deletePermission(any(DeletePermissionRequest.class));
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> DeletePermission.main(new String[]{}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 7 passed");
    }

    @Test
    @Order(8)
    public void testDeletePolicy() {
        DeletePolicyResponse mockResponse = DeletePolicyResponse.builder().build();
        
        when(mockClient.deletePolicy(any(DeletePolicyRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> DeletePolicy.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).deletePolicy(any(DeletePolicyRequest.class));
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> DeletePolicy.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 8 passed");
    }

    @Test
    @Order(9)
    public void testGetPolicy() {
        GetPolicyResponse mockResponse = GetPolicyResponse.builder()
                .policy("{\"Version\":\"2012-10-17\",\"Statement\":[]}")
                .build();
        
        when(mockClient.getPolicy(any(GetPolicyRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> GetPolicy.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).getPolicy(any(GetPolicyRequest.class));
            assertTrue(outputStream.toString().contains("Version"), "Should output policy content");
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> GetPolicy.main(new String[]{}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 9 passed");
    }

    @Test
    @Order(10)
    public void testListPermissions() {
        Permission mockPermission = Permission.builder()
                .principal("acm.amazonaws.com")
                .actions(ActionType.ISSUE_CERTIFICATE)
                .build();
        
        ListPermissionsResponse mockResponse = ListPermissionsResponse.builder()
                .permissions(mockPermission)
                .build();
        
        when(mockClient.listPermissions(any(ListPermissionsRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> ListPermissions.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).listPermissions(any(ListPermissionsRequest.class));
            assertTrue(outputStream.toString().contains("acm.amazonaws.com"), "Should output permission details");
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> ListPermissions.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
       originalOut.println("Test 10 passed");
    }

    @Test
    @Order(11)
    public void testPutPolicy() {
        PutPolicyResponse mockResponse = PutPolicyResponse.builder().build();
        
        when(mockClient.putPolicy(any(PutPolicyRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class);
             MockedStatic<Files> mockedFiles = mockStatic(Files.class);
             MockedStatic<Paths> mockedPaths = mockStatic(Paths.class)) {
            
            setupMockClient(mockedStatic); 
         
            Path mockPath = mock(Path.class);
            String mockPolicyContent = "{\"Version\":\"2012-10-17\",\"Statement\":[]}";
            
            mockedPaths.when(() -> Paths.get("policy", "Policy.json")).thenReturn(mockPath);
            mockedFiles.when(() -> Files.readAllBytes(mockPath)).thenReturn(mockPolicyContent.getBytes());
            
            
            assertDoesNotThrow(() -> PutPolicy.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).putPolicy(any(PutPolicyRequest.class));
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> PutPolicy.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 11 passed");
    }

    @Test
    @Order(12)
    public void testDeleteCertificateAuthority() {
        DeleteCertificateAuthorityResponse mockResponse = DeleteCertificateAuthorityResponse.builder().build();
        
        when(mockClient.deleteCertificateAuthority(any(DeleteCertificateAuthorityRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> DeleteCertificateAuthority.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).deleteCertificateAuthority(any(DeleteCertificateAuthorityRequest.class));
            
            resetStreamsAndMocks();
            
             assertDoesNotThrow(() -> DeleteCertificateAuthority.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca",
                "30"
            }));
            verify(mockClient).deleteCertificateAuthority(any(DeleteCertificateAuthorityRequest.class));
            
            resetStreamsAndMocks();

            assertDoesNotThrow(() -> DeleteCertificateAuthority.main(new String[]{}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 12 passed");
    }

    @Test
    @Order(13)
    public void testGetCertificate() {
        GetCertificateResponse mockResponse = GetCertificateResponse.builder()
                .certificate("-----BEGIN CERTIFICATE-----\nMIIBkTCB+wIJAK...")
                .certificateChain("-----BEGIN CERTIFICATE-----\nMIIBkTCB+wIJAK...")
                .build();
        
        when(mockClient.getCertificate(any(GetCertificateRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> GetCertificate.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate/test-cert",
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).getCertificate(any(GetCertificateRequest.class));
            assertTrue(outputStream.toString().contains("BEGIN CERTIFICATE"), "Should output certificate");
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> GetCertificate.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 13 passed");
    }

    @Test
    @Order(14)
    public void testGetCertificateAuthorityCsr() {
        GetCertificateAuthorityCsrResponse mockResponse = GetCertificateAuthorityCsrResponse.builder()
                .csr("-----BEGIN CERTIFICATE REQUEST-----\nMIIBkTCB+wIJAK...")
                .build();
        
        when(mockClient.getCertificateAuthorityCsr(any(GetCertificateAuthorityCsrRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> GetCertificateAuthorityCsr.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).getCertificateAuthorityCsr(any(GetCertificateAuthorityCsrRequest.class));
            assertTrue(outputStream.toString().contains("BEGIN CERTIFICATE REQUEST"), "Should output CSR");
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> GetCertificateAuthorityCsr.main(new String[]{}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 14 passed");
    }

    @Test
    @Order(15)
    public void testIssueCertificate() {
        IssueCertificateResponse mockResponse = IssueCertificateResponse.builder()
                .certificateArn("arn:aws:acm-pca:us-east-1:123456789012:certificate/test-cert")
                .build();
        
        when(mockClient.issueCertificate(any(IssueCertificateRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class);
             MockedStatic<Files> mockedFiles = mockStatic(Files.class);
             MockedStatic<Paths> mockedPaths = mockStatic(Paths.class)) {
            
            setupMockClient(mockedStatic);
            
            Path mockPath = mock(Path.class);
            String mockCsrContent = "-----BEGIN CERTIFICATE REQUEST-----\nMIIBkTCB+wIJAK...\n-----END CERTIFICATE REQUEST-----";
            
            mockedPaths.when(() -> Paths.get("ca.csr")).thenReturn(mockPath);
            mockedFiles.when(() -> Files.readString(mockPath, StandardCharsets.UTF_8)).thenReturn(mockCsrContent);
            
            assertDoesNotThrow(() -> IssueCertificate.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).issueCertificate(any(IssueCertificateRequest.class));
            assertTrue(outputStream.toString().contains("certificate/test-cert"), "Should output certificate ARN");
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> IssueCertificate.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 15 passed");
    }

    @Test
    @Order(16)
    public void testTagCertificateAuthorities() {
        TagCertificateAuthorityResponse mockResponse = TagCertificateAuthorityResponse.builder().build();
        
        when(mockClient.tagCertificateAuthority(any(TagCertificateAuthorityRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> TagCertificateAuthorities.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).tagCertificateAuthority(any(TagCertificateAuthorityRequest.class));
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> TagCertificateAuthorities.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 16 passed");
    }

    @Test
    @Order(17)
    public void testUntagCertificateAuthority() {
        UntagCertificateAuthorityResponse mockResponse = UntagCertificateAuthorityResponse.builder().build();
        
        when(mockClient.untagCertificateAuthority(any(UntagCertificateAuthorityRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> UntagCertificateAuthority.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).untagCertificateAuthority(any(UntagCertificateAuthorityRequest.class));
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> UntagCertificateAuthority.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 17 passed");
    }

    @Test
    @Order(18)
    public void testCreateCertificateAuthorityAuditReport() {
        CreateCertificateAuthorityAuditReportResponse mockResponse = CreateCertificateAuthorityAuditReportResponse.builder()
                .auditReportId("test-audit-report-id")
                .s3Key("audit-reports/test-audit-report.json")
                .build();
        
        when(mockClient.createCertificateAuthorityAuditReport(any(CreateCertificateAuthorityAuditReportRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> CreateCertificateAuthorityAuditReport.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca",
                "test-bucket"
            }));
            verify(mockClient).createCertificateAuthorityAuditReport(any(CreateCertificateAuthorityAuditReportRequest.class));
            assertTrue(outputStream.toString().contains("test-audit-report-id"), "Should output audit report ID");
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> CreateCertificateAuthorityAuditReport.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 18 passed");
    }

    @Test
    @Order(19)
    public void testDescribeCertificateAuthorityAuditReport() {
        DescribeCertificateAuthorityAuditReportResponse mockResponse = DescribeCertificateAuthorityAuditReportResponse.builder()
                .auditReportStatus(AuditReportStatus.SUCCESS)
                .s3BucketName("test-bucket")
                .s3Key("audit-reports/test-audit-report.json")
                .build();
        
        when(mockClient.describeCertificateAuthorityAuditReport(any(DescribeCertificateAuthorityAuditReportRequest.class)))
                .thenReturn(mockResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> DescribeCertificateAuthorityAuditReport.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca",
                "test-audit-report-id"
            }));
            verify(mockClient).describeCertificateAuthorityAuditReport(any(DescribeCertificateAuthorityAuditReportRequest.class));
            assertTrue(outputStream.toString().contains("SUCCESS"), "Should output audit report status");
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> DescribeCertificateAuthorityAuditReport.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 19 passed");
    }

    @Test
    @Order(20)
    public void testImportCertificateAuthorityCertificate() {
        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class);
             MockedStatic<Files> mockedFiles = mockStatic(Files.class);
             MockedStatic<Paths> mockedPaths = mockStatic(Paths.class)) {
            
            setupMockClient(mockedStatic);
            
            Path mockCertPath = mock(Path.class);
            Path mockChainPath = mock(Path.class);
            String mockCertContent = "-----BEGIN CERTIFICATE-----\nMIIBkTCB+wIJAK...\n-----END CERTIFICATE-----";
            String mockChainContent = "-----BEGIN CERTIFICATE-----\nMIIBkTCB+wIJAK...\n-----END CERTIFICATE-----";
            
            mockedPaths.when(() -> Paths.get("cert.pem")).thenReturn(mockCertPath);
            mockedPaths.when(() -> Paths.get("certChain.pem")).thenReturn(mockChainPath);
            mockedFiles.when(() -> Files.readString(mockCertPath, StandardCharsets.UTF_8)).thenReturn(mockCertContent);
            mockedFiles.when(() -> Files.readString(mockChainPath, StandardCharsets.UTF_8)).thenReturn(mockChainContent);
            
            assertDoesNotThrow(() -> ImportCertificateAuthorityCertificate.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).importCertificateAuthorityCertificate(any(ImportCertificateAuthorityCertificateRequest.class));
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> ImportCertificateAuthorityCertificate.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 20 passed");
    }

    @Test
    @Order(21)
    public void testRestoreCertificateAuthority() {
        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> RestoreCertificateAuthority.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).restoreCertificateAuthority(any(RestoreCertificateAuthorityRequest.class));
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> RestoreCertificateAuthority.main(new String[]{}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 21 passed");
    }

    @Test
    @Order(22)
    public void testRevokeCertificate() {
        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> RevokeCertificate.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca",
                "arn:aws:acm-pca:us-east-1:123456789012:certificate/test-cert"
            }));
            verify(mockClient).revokeCertificate(any(RevokeCertificateRequest.class));
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> RevokeCertificate.main(new String[]{"us-east-1"}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 22 passed");
    }

    @Test
    @Order(23)
    public void testUpdateCertificateAuthority() {
        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> UpdateCertificateAuthority.main(new String[]{
                "us-east-1", 
                "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca"
            }));
            verify(mockClient).updateCertificateAuthority(any(UpdateCertificateAuthorityRequest.class));
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> UpdateCertificateAuthority.main(new String[]{}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 23 passed");
    }

    @Test
    @Order(24)
    public void testCreatePrivateCertificateAuthorityAD() {
        String expectedArn = "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca-ad";
        CreateCertificateAuthorityResponse mockResponse = CreateCertificateAuthorityResponse.builder()
                .certificateAuthorityArn(expectedArn)
                .build();
        
        GetCertificateAuthorityCsrResponse mockCsrResponse = GetCertificateAuthorityCsrResponse.builder()
                .csr("-----BEGIN CERTIFICATE REQUEST-----\nMIIBkTCB+wIJAK...\n-----END CERTIFICATE REQUEST-----")
                .build();
        
        IssueCertificateResponse mockIssueResponse = IssueCertificateResponse.builder()
                .certificateArn("arn:aws:acm-pca:us-east-1:123456789012:certificate/test-cert")
                .build();
        
        GetCertificateResponse mockGetCertResponse = GetCertificateResponse.builder()
                .certificate("-----BEGIN CERTIFICATE-----\nMIIBkTCB+wIJAK...\n-----END CERTIFICATE-----")
                .build();
        
        when(mockClient.createCertificateAuthority(any(CreateCertificateAuthorityRequest.class)))
                .thenReturn(mockResponse);
        when(mockClient.getCertificateAuthorityCsr(any(GetCertificateAuthorityCsrRequest.class)))
                .thenReturn(mockCsrResponse);
        when(mockClient.issueCertificate(any(IssueCertificateRequest.class)))
                .thenReturn(mockIssueResponse);
        when(mockClient.getCertificate(any(GetCertificateRequest.class)))
                .thenReturn(mockGetCertResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> CreatePrivateCertificateAuthorityAD.main(new String[]{"us-east-1"}));
            verify(mockClient).createCertificateAuthority(any(CreateCertificateAuthorityRequest.class));
            verify(mockClient).getCertificateAuthorityCsr(any(GetCertificateAuthorityCsrRequest.class));
            verify(mockClient).issueCertificate(any(IssueCertificateRequest.class));
            verify(mockClient).getCertificate(any(GetCertificateRequest.class));
            verify(mockClient).importCertificateAuthorityCertificate(any(ImportCertificateAuthorityCertificateRequest.class));
            assertTrue(outputStream.toString().contains(expectedArn), "Should output the certificate authority ARN");
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> CreatePrivateCertificateAuthorityAD.main(new String[]{}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 24 passed");
    }

    @Test
    @Order(25)
    public void testRootCAActivation() {
        String expectedArn = "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-root-ca";
        CreateCertificateAuthorityResponse mockCreateResponse = CreateCertificateAuthorityResponse.builder()
                .certificateAuthorityArn(expectedArn)
                .build();
        
        GetCertificateAuthorityCsrResponse mockCsrResponse = GetCertificateAuthorityCsrResponse.builder()
                .csr("-----BEGIN CERTIFICATE REQUEST-----\nMIIBkTCB+wIJAK...\n-----END CERTIFICATE REQUEST-----")
                .build();
        
        IssueCertificateResponse mockIssueResponse = IssueCertificateResponse.builder()
                .certificateArn("arn:aws:acm-pca:us-east-1:123456789012:certificate/test-cert")
                .build();
        
        GetCertificateResponse mockGetCertResponse = GetCertificateResponse.builder()
                .certificate("-----BEGIN CERTIFICATE-----\nMIIBkTCB+wIJAK...\n-----END CERTIFICATE-----")
                .build();
        
        when(mockClient.createCertificateAuthority(any(CreateCertificateAuthorityRequest.class)))
                .thenReturn(mockCreateResponse);
        when(mockClient.getCertificateAuthorityCsr(any(GetCertificateAuthorityCsrRequest.class)))
                .thenReturn(mockCsrResponse);
        when(mockClient.issueCertificate(any(IssueCertificateRequest.class)))
                .thenReturn(mockIssueResponse);
        when(mockClient.getCertificate(any(GetCertificateRequest.class)))
                .thenReturn(mockGetCertResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> RootCAActivation.main(new String[]{"us-east-1", "test-bucket"}));
            verify(mockClient).createCertificateAuthority(any(CreateCertificateAuthorityRequest.class));
            verify(mockClient).getCertificateAuthorityCsr(any(GetCertificateAuthorityCsrRequest.class));
            verify(mockClient).issueCertificate(any(IssueCertificateRequest.class));
            verify(mockClient).getCertificate(any(GetCertificateRequest.class));
            verify(mockClient).importCertificateAuthorityCertificate(any(ImportCertificateAuthorityCertificateRequest.class));
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> RootCAActivation.main(new String[]{}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 25 passed");
    }

    @Test
    @Order(26)
    public void testSubordinateCAActivation() {
        String rootCaArn = "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-root-ca";
        String subCaArn = "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-sub-ca";
        
        GetCertificateAuthorityCertificateResponse mockRootCertResponse = GetCertificateAuthorityCertificateResponse.builder()
                .certificate("-----BEGIN CERTIFICATE-----\nMIIBkTCB+wIJAK...\n-----END CERTIFICATE-----")
                .build();
        
        CreateCertificateAuthorityResponse mockCreateResponse = CreateCertificateAuthorityResponse.builder()
                .certificateAuthorityArn(subCaArn)
                .build();
        
        GetCertificateAuthorityCsrResponse mockCsrResponse = GetCertificateAuthorityCsrResponse.builder()
                .csr("-----BEGIN CERTIFICATE REQUEST-----\nMIIBkTCB+wIJAK...\n-----END CERTIFICATE REQUEST-----")
                .build();
        
        IssueCertificateResponse mockIssueResponse = IssueCertificateResponse.builder()
                .certificateArn("arn:aws:acm-pca:us-east-1:123456789012:certificate/test-sub-cert")
                .build();
        
        GetCertificateResponse mockGetCertResponse = GetCertificateResponse.builder()
                .certificate("-----BEGIN CERTIFICATE-----\nMIIBkTCB+wIJAK...\n-----END CERTIFICATE-----")
                .certificateChain("-----BEGIN CERTIFICATE-----\nMIIBkTCB+wIJAK...\n-----END CERTIFICATE-----")
                .build();
        
        when(mockClient.getCertificateAuthorityCertificate(any(GetCertificateAuthorityCertificateRequest.class)))
                .thenReturn(mockRootCertResponse);
        when(mockClient.createCertificateAuthority(any(CreateCertificateAuthorityRequest.class)))
                .thenReturn(mockCreateResponse);
        when(mockClient.getCertificateAuthorityCsr(any(GetCertificateAuthorityCsrRequest.class)))
                .thenReturn(mockCsrResponse);
        when(mockClient.issueCertificate(any(IssueCertificateRequest.class)))
                .thenReturn(mockIssueResponse);
        when(mockClient.getCertificate(any(GetCertificateRequest.class)))
                .thenReturn(mockGetCertResponse);

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
            assertDoesNotThrow(() -> SubordinateCAActivation.main(new String[]{
                "us-east-1", 
                rootCaArn,
                "test-bucket"
            }));
            verify(mockClient).getCertificateAuthorityCertificate(any(GetCertificateAuthorityCertificateRequest.class));
            verify(mockClient).createCertificateAuthority(any(CreateCertificateAuthorityRequest.class));
            verify(mockClient).getCertificateAuthorityCsr(any(GetCertificateAuthorityCsrRequest.class));
            verify(mockClient).issueCertificate(any(IssueCertificateRequest.class));
            verify(mockClient).getCertificate(any(GetCertificateRequest.class));
            verify(mockClient).importCertificateAuthorityCertificate(any(ImportCertificateAuthorityCertificateRequest.class));
            
            resetStreamsAndMocks();
            
            assertDoesNotThrow(() -> SubordinateCAActivation.main(new String[]{}));
            assertTrue(outputStream.toString().contains("Usage:"), "Should show usage message");
            verifyNoInteractions(mockClient);
        }
        originalOut.println("Test 26 passed");
    }

    private void setupMockClient(MockedStatic<AcmPcaClient> mockedStatic) {
       AcmPcaClientBuilder mockBuilder = 
            mock(AcmPcaClientBuilder.class);
        
        when(mockBuilder.region(any(Region.class))).thenReturn(mockBuilder);
        when(mockBuilder.build()).thenReturn(mockClient);
        
        when(mockClient.waiter()).thenReturn(mockWaiter);
        
        mockedStatic.when(AcmPcaClient::builder).thenReturn(mockBuilder);
    }
    
    private void resetStreamsAndMocks() {
        reset(mockClient);
        reset(mockWaiter);
        outputStream.reset();
        errorStream.reset();
    }
}

