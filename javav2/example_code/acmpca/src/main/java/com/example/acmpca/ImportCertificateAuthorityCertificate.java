// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.example.acmpca;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.acmpca.AcmPcaClient;
import software.amazon.awssdk.services.acmpca.model.ImportCertificateAuthorityCertificateRequest;

public class ImportCertificateAuthorityCertificate {

  public static ByteBuffer stringToByteBuffer(final String string) {
    if (Objects.isNull(string)) {
      return null;
    }
    byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
    return ByteBuffer.wrap(bytes);
  }

  public static void main(String[] args) throws Exception {

    final String usage =
        """

         Usage: <region> <caArn> <signedCertPath> <certChainPath>

         Where:
            region -
            caArn -
            signedCertPath -
            certChainPath -
      """;

    if (args.length != 4) {
      System.out.println(usage);
      return;
    }

    String regionName = args[0];
    String caArn = args[1];
    String signedCertPath = args[2];
    String certChainPath = args[3];

    // Define the region for your sample.
    Region region = Region.of(regionName);

    // Create a client that you can use to make requests.
    AcmPcaClient client = AcmPcaClient.builder().region(region).build();

    // Create the request object and set the signed certificate, chain and CA ARN.
    ImportCertificateAuthorityCertificateRequest req =
        ImportCertificateAuthorityCertificateRequest.builder().build();

    // // try {

    // //    String signedCert = readFile(signedCertPath);
    // //    String certChain = readFile(certChainPath);

    // //    // Set the signed certificate.
    // //    ByteBuffer certByteBuffer = stringToByteBuffer(signedCertPath);
    // //    ByteBuffer chainByteBuffer = stringToByteBuffer(certChainPath);

    // // }

    // // req = req.toBuilder().certificate(certByteBuffer).build();

    // // Set the certificate chain.
    // // String strCertificateChain =
    // //       "<replaceable>-----BEGIN CERTIFICATE-----\n</replaceable>" +
    // //       "<replaceable>base64-encoded certificate\n</replaceable>" +
    // //       "<replaceable>-----END CERTIFICATE-----\n</replaceable>";
    // ByteBuffer chainByteBuffer = stringToByteBuffer(strCertificateChain);
    // req = req.toBuilder().certificateChain(chainByteBuffer).build();

    // // Create a request object with required parameters
    // ImportCertificateAuthorityCertificate req = ImportCertificateAuthorityCertificate.builder()
    //      .certificateAuthorityArn(caArn);
    //      .certificate(certByteBuffer)
    //      .certificateChain(chainByteBuffer)
    //      .build();

    // // Import the certificate.
    // try {
    //    client.importCertificateAuthorityCertificate(req);
    // } catch (CertificateMismatchException ex) {
    //    throw ex;
    // } catch (MalformedCertificateException ex) {
    //    throw ex;
    // } catch (InvalidArnException ex) {
    //    throw ex;
    // } catch (ResourceNotFoundException ex) {
    //    throw ex;
    // } catch (RequestInProgressException ex) {
    //    throw ex;
    // } catch (ConcurrentModificationException ex) {
    //    throw ex;
    // } catch (RequestFailedException ex) {
    //    throw ex;
    // }
  }
}
