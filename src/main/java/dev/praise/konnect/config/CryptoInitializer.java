package dev.praise.konnect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class CryptoInitializer {

    @Value("${konnect.security.crypto-key}")
    private String configuredSecretKey;

    @PostConstruct
    public void init() {
        MessageCryptoConverter.setSecretKey(configuredSecretKey);
    }
}
