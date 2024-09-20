package com.jenna.pennypilot.core.jasypt;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@SpringBootTest(properties = {"jasypt.encryptor.password=pennypenny"})
public class JasyptConfigTest {
    @Value("${jasypt.encryptor.password}")
    private String encryptKey;

    @Test
    public void jasyptTest() {
        Map<String, String> dbInfo = new LinkedHashMap<>();

        dbInfo.put("url", "jdbc:oracle:thin:@localhost:1521:xe");
        dbInfo.put("username", "penny_pilot");
        dbInfo.put("password", "qwer1234");

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

        log.info("Encrypt Key: {}", encryptKey);

        dbInfo.forEach((key, value) -> {
            String encryptedText = encryptor.encrypt(value);
            String decryptedText = encryptor.decrypt(encryptedText);

            log.info("Encrypted {}: {}", key, encryptedText);
            log.info("Decrypted {}: {}", key, decryptedText);
        });

        log.info("username hard value: {}", encryptor.decrypt("/qRPiX/qFCQLKqOAduNI5VcqQtGsUyWDoLGCRRCYwbw="));
        log.info("password hard value: {}", encryptor.decrypt("yPuP4hpCPGJMfyimsgPmrcwW0leC7fKn8FF7w6TqZIk="));

    }
}
