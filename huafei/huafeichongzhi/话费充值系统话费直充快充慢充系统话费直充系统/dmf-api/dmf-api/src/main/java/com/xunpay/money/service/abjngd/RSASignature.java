package com.xunpay.money.service.abjngd;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA签名验签类
 */
public class RSASignature{

    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";


    public static boolean doCheck(String content, String sign, String publicKey)
    {
        try
        {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = (new BASE64Decoder()).decodeBuffer(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update( content.getBytes() );

            boolean bverify = signature.verify( (new BASE64Decoder()).decodeBuffer(sign) );
            return bverify;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) throws Exception {
    	byte[] plainText = RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCQtQ0xfEVTramI7V6T6P4DbIWaFVutuUUnqYRx1YqUwawr1gi8D5hbmuutXcwnhhDtHSOa5FWYiOzrKDHGj+iGRZk7v/bFTHMJ2I2AW9SUq25cJBPy5y7va+aumSvrOhVrac0rTJB9gdkUJWkybBv0BB3mEQmvEJ5zGqsTAKXIerRvR5754ujZCJadiWQOv7bQ/uhhFVDaGK40AOVuHRlRsc7jd0CByljNCTci+HrMk57sf3glilkH6H4F1hdapGM6HH7wH5LeBUi/SjoAD4nL5xGRUzMqn01rlG71l64d9DSciPXK/HXGZCcLTUVxSLmQDqS7IF5n1oh8J098aLybAgMBAAECggEAYisxZBzWO0pY9RdzTgyGJnR8vmc25sgFbj7GJyOi+//XeXWeP1alGaAjul0jnryIxxM5kF7O4sghUfbWrcn7CPw2VB6KnpaahFdwx1+E+8bA+6lODz9ey/X7bP9kmBOzbEc+dN1gOITaa5xlnVtSK1yPnAB6/VTc6WFyJVYPIc13YbtGP8GhWntvaq5/xBzp52pJj2kLNJuKikKg5rN2UlUaVCMB4lJzt3vDDpCHW3c05Ql5/OKmg6ntQFCRJEWOhv84K+4mz3D+fOhV42GxVG+EXYeaoYvCUKn+PwUSzRJ0kxqyIQMjzv/tSOIsSRLfmEBC1AvGdbphd+qKn7qH4QKBgQDEGZ7uwqOd7liMbOy7ge56I3dGTwvnyXca7s+IJPjG3YS0Kd80YJ2gD8zq1khI2Ezstn8IVFhSTvAoWQ2JYw2rZC8oPN1Clp0P8R+ZvTnrFWp2QyJQfoyIHzvQR8kpaqojnUXa9kY6CyC+BJuwlbw8dHdAq+I7yDU7pF5veOPA3QKBgQC86K32eAOEtAdANevZ5HkVCbm+tmCvt6p+WM8vHz6kRqU2T2LPFlkF+ihz8nl7s9dPxWKJEdHBlyIzkyNuIdY5XTTnWH/0TaB6Ufwtcs/5Gdd2ilm+QIYAEsO5gVvmpJCOw/tsHL+aQ+nQex+NYGbfqp7bxx1ekSlDKAE+G/Mf1wKBgQC8aP1p2zqGx/Tt3s3JDnqJgT/dHp+KsOetyds9rIAV2cnhrAVXdqibm/K/guhqjN/D40fEQf2l/1ABBCbcZH5CuPwSBgB0NO9s3LgG52cZdjcWr0Pt4Ni3BINch0xAbcpQc5AlY89vfzeTOiw4CnWUBDZ7vWmdFKwIxNEy+wygRQKBgEI0aYNPXCxNy5y9SQL8B1NQLUChOhFbFO1iwnmJQbLg71WxTLe25Uwq97Jq8BhiVrsmOfo/vHtUNDBPCMO4KBPCjp/lbDJ6Yht8UBy9eBWc8N4iboE2q7+q091XcJXMZr0iAiNJj/zIgFEIVW5+so8AhNwRVEGoLgXTJ1xU/0fRAoGAMvFRxDdd/uAv3VQlwIQBV09y5grCdt+Q4IiKynPwLuA+hcimafMyLYOt51txAX9vwJzrKcMRr9XAxrkR6ZSyu/mZqfwk/MgRhR67nacuimY/W90ytlg+9TJ/rWKLuJ4OBqaUv+A/dg3TdxytWzqhqoa1H8eUgdXqC6JYAWeVWGI="),
    			(new BASE64Decoder()).decodeBuffer("bUkjV2LChEtnikqGZ81j0VCb+HyUPblf3pU/aCGKDeRA5lDokJNNfVfZi4EHiIfzCYAZ5rZhZaIdoLeizmV9iNvIzyGsvi3VOFTabcc3MS2KMlbeTH1RRu055zsVPz/AtcC0mwLGwbQ9N3rtNBAcqjNRMRSSdgds8A4bSA6Q3txpWazggTe+gcBTqNF9EuRzpkZjQxCwsohAdzCzVF58uwKbDDHk/XODV1C2FK2CGOzKjcuVNegsJ5BvnnxcncS//opO3vYO2GxHvc+NQcqed3bxhQSuxStU5/GrfagNBYB2QQvSmoEMaUapGYWoArmXztasfF8ofHKvgRWY7hJ75Q=="));
    	String plainTextStr =new String(plainText);
    	System.out.println(plainTextStr);
	}
    
}