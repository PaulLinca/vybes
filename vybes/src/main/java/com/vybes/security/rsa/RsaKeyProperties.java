package com.vybes.security.rsa;

import lombok.Data;

import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@Component
public class RsaKeyProperties {
    // Nice explanation:  https://www.youtube.com/watch?v=YEBfamv-_do

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RsaKeyProperties() {
        KeyPair pair = KeyGeneratorUtility.generateRsaKey();

        this.publicKey = (RSAPublicKey) pair.getPublic();
        this.privateKey = (RSAPrivateKey) pair.getPrivate();
    }
}
