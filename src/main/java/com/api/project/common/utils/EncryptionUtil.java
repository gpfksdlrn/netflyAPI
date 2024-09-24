package com.api.project.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {
    public static String key;
    @Value("${jwt.secret-key}")
    public void setKey(String value) {
        key = value;
    }

    public static Long exp;
    @Value("${jwt.expiration}")
    public void setExp(Long value) {
        exp = value;
    }

}
