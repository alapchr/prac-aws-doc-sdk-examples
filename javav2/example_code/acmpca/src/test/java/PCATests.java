// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.*;
import software.amazon.awssdk.services.acmpca.model.InvalidPolicyException;
import software.amazon.awssdk.services.acmpca.model.LimitExceededException;

    

public class PCATests {

    @Mock
    private AcmPcaClient client;

    // Sets up all mock objects to ensure that they are ready to use
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testCreateCA (){
        
        // Fake arn expected 
        String expectedArn = "arn:aws:acm-pca:us-east-1:123456789012:certificate-authority/test-ca-id"; 

        CreateCertificateAuthorityResponse mockResponse = CreateCertificateAuthorityResponse.builder()
        .certificateAuthorityArn(expectedArn)
        .build();

      when(client.createCertificateAuthority(any(CreateCertificateAuthorityRequest.class)))
        .thenReturn(mockResponse);

     
         CreateCertificateAuthorityResponse rootResult = client.createCertificateAuthority(
            CreateCertificateAuthorityRequest.builder()
                .certificateAuthorityType(CertificateAuthorityType.ROOT)
                .build()
        );        

        CreateCertificateAuthorityResponse subResult = client.createCertificateAuthority(
            CreateCertificateAuthorityRequest.builder()
                .certificateAuthorityType(CertificateAuthorityType.SUBORDINATE)
                .build()
        ); 
        
       
        assertNotNull(rootResult);
        assertNotNull(subResult);
        assertEquals(expectedArn, rootResult.certificateAuthorityArn());
        assertEquals(expectedArn, subResult.certificateAuthorityArn());
        verify(client, times(2)).createCertificateAuthority(any(CreateCertificateAuthorityRequest.class));
        
        
        }

    @Test
     void testCreateCA_InvalidArgsException() {
        // Test exception handling
        when(client.createCertificateAuthority(any(CreateCertificateAuthorityRequest.class)))
            .thenThrow(InvalidArgsException.builder().message("Invalid arguments").build());

        // Verify exception is thrown
        assertThrows(InvalidArgsException.class, () -> {
            client.createCertificateAuthority(CreateCertificateAuthorityRequest.builder().build());
        });

    }


    @Test
    void testCreateCA_InvalidPolicyException() {
        when(client.createCertificateAuthority(any(CreateCertificateAuthorityRequest.class)))
            .thenThrow(InvalidPolicyException.builder().message("Invalid policy").build());

        assertThrows(InvalidPolicyException.class, () -> {
            client.createCertificateAuthority(CreateCertificateAuthorityRequest.builder().build());
        });
    }

    @Test
    void testCreateCA_LimitExceededException() {
        when(client.createCertificateAuthority(any(CreateCertificateAuthorityRequest.class)))
            .thenThrow(LimitExceededException.builder().message("Limit exceeded").build());

        assertThrows(LimitExceededException.class, () -> {
            client.createCertificateAuthority(CreateCertificateAuthorityRequest.builder().build());
        });
    }
}

