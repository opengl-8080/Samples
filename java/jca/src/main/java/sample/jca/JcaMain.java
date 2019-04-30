package sample.jca;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Arrays;
import java.util.Base64;

public class JcaMain {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();

        Signature signature = Signature.getInstance("SHA256withDSA");
        signature.initSign(keyPair.getPrivate());
        String source = "hoge";
        byte[] sourceBytes = source.getBytes();
        signature.update(sourceBytes);
        byte[] signed = signature.sign();
        
        Signature signature2 = Signature.getInstance("SHA256withDSA");
        signature2.initVerify(keyPair.getPublic());
        signature2.update(sourceBytes);
        boolean result = signature2.verify(signed);
        System.out.println(result);
    }

    private static void printAsBase64(byte[] bytes) {
        System.out.println(Base64.getEncoder().encodeToString(bytes));
    }
}