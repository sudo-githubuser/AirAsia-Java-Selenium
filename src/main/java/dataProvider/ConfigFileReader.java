package dataProvider;

import enums.DriverType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

public class ConfigFileReader {

    private final Properties properties;
    private static final String PROPERTY_FILE_PATH = "src/main/resources/configs/configuration.properties";

    public ConfigFileReader(){
        properties = new Properties();
        try (var reader = Files.newBufferedReader(Paths.get(PROPERTY_FILE_PATH))){
            properties.load(reader);
        } catch (IOException e){
            throw new RuntimeException("Failed to load configuration.properties from:" + PROPERTY_FILE_PATH, e);
        }
    }

    public String getExcelFilePath(){
        return getProperty("excelFilePath",
                "Excel file path not specified in the configuration.properties file");
    }

    public Duration getImplicitWait(){
        String implicitlyWait = properties.getProperty("implicitlyWait");
        return implicitlyWait != null
                ? Duration.ofSeconds(parseLongOrThrow(implicitlyWait))
                : Duration.ofSeconds(30);
    }

    public String getApplicationURL(){
        return getProperty("url",
                "Application URL not specified in the configuration.properties file for the key: url");
    }

    public DriverType getBrowser(){
        String browserName = properties.getProperty("browser");
        return switch (browserName == null ? "" : browserName.toLowerCase()){
            case "chrome", "" -> DriverType.CHROME;
            case "firefox" -> DriverType.FIREFOX;
            case "internet explorer" -> DriverType.INTERNETEXPLORER;
            default -> throw new RuntimeException(
                    "Browser name key value in configuration.properties is not matched: " + browserName);
        };
    }

    private String getProperty(String key, String errorMessage){
        String value = properties.getProperty(key);
        if(value != null){
            return value;
        }
        throw new RuntimeException(errorMessage);
    }

    private long parseLongOrThrow(String value){
        try{
            return Long.parseLong(value);
        } catch (NumberFormatException e){
            throw new RuntimeException("Not able to parse value: " + value + " to long", e);
        }
    }
}
