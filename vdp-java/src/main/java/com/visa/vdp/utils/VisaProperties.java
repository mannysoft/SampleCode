package com.visa.vdp.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class VisaProperties {

    static Properties properties;

    static {
        try {
            properties = new Properties();
            String propertiesFile = System.getProperty("ftproperties");
            if (propertiesFile == null) {
                properties.load(VisaProperties.class.getResourceAsStream("/configuration.properties"));
            } else {
                properties.load(new FileReader(propertiesFile));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(Property property) {
        return (String) properties.get(property.getValue());
    }
}
