// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

import com.example.acmpca.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.*;
import software.amazon.awssdk.services.acmpca.model.ActionType;
import software.amazon.awssdk.services.acmpca.model.Tag;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PCATests {
    
    @Mock
    private AcmPcaClient mockClient;
    
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

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
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

    private void setupMockClient(MockedStatic<AcmPcaClient> mockedStatic) {
        software.amazon.awssdk.services.acmpca.AcmPcaClientBuilder mockBuilder = 
            mock(software.amazon.awssdk.services.acmpca.AcmPcaClientBuilder.class);
        
        when(mockBuilder.region(any(Region.class))).thenReturn(mockBuilder);
        when(mockBuilder.build()).thenReturn(mockClient);
        
        mockedStatic.when(AcmPcaClient::builder).thenReturn(mockBuilder);
    }
    
    private void resetStreamsAndMocks() {
        reset(mockClient);
        outputStream.reset();
        errorStream.reset();
    }
}

