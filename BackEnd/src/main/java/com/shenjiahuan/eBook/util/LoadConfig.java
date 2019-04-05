package com.shenjiahuan.eBook.util;

import java.io.InputStream;
import java.util.Properties;

public class LoadConfig {
    public static String get(String key) throws java.io.IOException {
        Properties pro;
        pro = new Properties();
        InputStream in = LoadConfig.class.getClassLoader().getResourceAsStream("/config.properties");
        pro.load(in);
        return pro.getProperty(key);
    }
}
