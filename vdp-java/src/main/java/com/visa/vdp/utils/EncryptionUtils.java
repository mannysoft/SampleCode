package com.visa.vdp.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.json.JSONObject;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jose.util.IOUtils;

public class EncryptionUtils {

  final static Logger logger = Logger.getLogger(EncryptionUtils.class);

  private static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
  private static final String END_CERT = "-----END CERTIFICATE-----";
  private static final String BEGIN_RSA_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----";
  private static final String END_RSA_PRIVATE_KEY = "-----END RSA PRIVATE KEY-----";
  private static final String ENC_DATA = "encData";

  public static String getEncryptedPayload(String payload) throws CertificateException, JOSEException, IOException {
    logger.info("Encrypting the payload...");
    JWEHeader.Builder headerBuilder = new JWEHeader.Builder(
        JWEAlgorithm.RSA_OAEP_256, 
        EncryptionMethod.A128GCM);

    headerBuilder.keyID(VisaProperties.getProperty(Property.VPP_KEYID));
    headerBuilder.customParam("iat", System.currentTimeMillis());

    JWEObject jweObject = new JWEObject(headerBuilder.build(), new Payload(payload));
    jweObject.encrypt(new RSAEncrypter(getRSAPublicKey()));
    logger.info("Payload Encrypted Successfully");
    return "{\"encData\":\""+jweObject.serialize()+"\"}";
  }

  public static String getDecryptedPayload(String encryptedPayload) throws Exception {
    logger.info("Decrypting the payload...");
    String response = encryptedPayload;
    if(encryptedPayload.contains(ENC_DATA)) {
      JSONObject jsonObj = new JSONObject(encryptedPayload);
      String value = (String) jsonObj.get(ENC_DATA);
      if(StringUtils.isNotEmpty(value)) {
        JWEObject jweObject = JWEObject.parse(value);
        jweObject.decrypt(new RSADecrypter(getRSAPrivateKey()));
        response = jweObject.getPayload().toString();
        logger.info("Payload Decrypted Successfully. Decrypted payload : \n" + response);
      }
    }
    return response;
  }

  /*
   * Converts PEM file content to RSAPrivateKey
   */  
  private static RSAPrivateKey getRSAPrivateKey()
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    String pathToClientEncPrivateKey = VisaProperties.getProperty(Property.VPP_CLIENT_ENC_PRIVATE_KEY_PATH);
    String pemEncodedKey = IOUtils.readFileToString(new File(pathToClientEncPrivateKey), Charset.forName("UTF-8"));
    Base64 base64 = new Base64(pemEncodedKey
        .replaceAll(BEGIN_RSA_PRIVATE_KEY, "").replaceAll(END_RSA_PRIVATE_KEY, ""));
    ASN1Sequence primitive = (ASN1Sequence) ASN1Sequence.fromByteArray(base64.decode());
    Enumeration<?> e = primitive.getObjects();
    BigInteger v = ((ASN1Integer) e.nextElement()).getValue();
    int version = v.intValue();
    if (version != 0 && version != 1) {
      throw new IllegalArgumentException("wrong version for RSA private key");
    }
    BigInteger modulus = ((ASN1Integer) e.nextElement()).getValue();
    ((ASN1Integer) e.nextElement()).getValue();
    BigInteger privateExponent = ((ASN1Integer) e.nextElement()).getValue();
    ((ASN1Integer) e.nextElement()).getValue();
    ((ASN1Integer) e.nextElement()).getValue();
    ((ASN1Integer) e.nextElement()).getValue();
    ((ASN1Integer) e.nextElement()).getValue();
    ((ASN1Integer) e.nextElement()).getValue();
    RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(modulus, privateExponent);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
  }

  /*
   * Converts PEM file content to RSAPublicKey
   */
  private static RSAPublicKey getRSAPublicKey() throws CertificateException, IOException {
    String pathToClientEncPrivateKey = VisaProperties.getProperty(Property.VPP_SERVER_ENC_PUBLIC_CERT_PATH);
    String pemEncodedPublicKey = IOUtils.readFileToString(new File(pathToClientEncPrivateKey), Charset.forName("UTF-8"));
    Base64 base64 = new Base64(
        pemEncodedPublicKey.replaceAll(BEGIN_CERT, "").replaceAll(END_CERT, ""));
    Certificate cf = CertificateFactory.getInstance("X.509")
        .generateCertificate(new ByteArrayInputStream(base64.decode()));
    return (RSAPublicKey) cf.getPublicKey();
  }

}
