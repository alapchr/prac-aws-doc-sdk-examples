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

        try (MockedStatic<AcmPcaClient> mockedStatic = mockStatic(AcmPcaClient.class)) {
            setupMockClient(mockedStatic);
            
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

    private void setupMockClient(MockedStatic<AcmPcaClient> mockedStatic) {
        software.amazon.awssdk.services.acmpca.AcmPcaClientBuilder mockBuilder = 
            mock(software.amazon.awssdk.services.acmpca.AcmPcaClientBuilder.class);
        
        software.amazon.awssdk.services.acmpca.waiters.AcmPcaWaiter mockWaiter = 
            mock(software.amazon.awssdk.services.acmpca.waiters.AcmPcaWaiter.class);
        
        when(mockBuilder.region(any(Region.class))).thenReturn(mockBuilder);
        when(mockBuilder.build()).thenReturn(mockClient);
        
        when(mockClient.waiter()).thenReturn(mockWaiter);
        
        mockedStatic.when(AcmPcaClient::builder).thenReturn(mockBuilder);
    }
    
    private void resetStreamsAndMocks() {
        reset(mockClient);
        outputStream.reset();
        errorStream.reset();
    }
}

