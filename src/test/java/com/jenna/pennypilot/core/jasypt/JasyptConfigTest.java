package com.jenna.pennypilot.core.jasypt;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"jasypt.encryptor.password=pennypenny"})
public class JasyptConfigTest {
    @Value("{jasypt.encryptor.password}")
    private String encryptKey;

    @Test
    public void jasyptTest() {
        String plainText = "jdbc:oracle:thin:@localhost:1521:xe";   // DB url
        String plainUsername = "penny_pilot";    // username
        String plainPassword = "qwer1234";  // password

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(encryptKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        String encryptText = encryptor.encrypt(plainText);
        String decryptText = encryptor.decrypt(encryptText);

        System.out.println("encryptKey: " + encryptKey);
        System.out.println("plainText: " + plainText);
        System.out.println("encryptText: " + encryptText);
        System.out.println("decryptText: " + decryptText);

        System.out.println("encryptUsername: " + encryptor.encrypt(plainUsername));
        System.out.println("encryptPassword: " + encryptor.encrypt(plainPassword));
    }
}
