package ru.ctf.af.tl.aqaEasm.helpers;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Getter
public class ConfigReader {
    private final String browser;
    private final String testPageUrl;
    private final String apiUrl;
    private final boolean isHeadless;
    private final String callUserForSignUpEndpoint;

    public ConfigReader() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/config.properties"));
        this.browser = properties.getProperty("browser");
        this.testPageUrl = properties.getProperty("testPageUrl");
        this.apiUrl = properties.getProperty("apiUrl");
        this.isHeadless = Boolean.parseBoolean(properties.getProperty("isHeadless"));
        this.callUserForSignUpEndpoint = properties.getProperty("callUserForSignUpEndpoint");
    }
}