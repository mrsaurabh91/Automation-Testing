package com.magic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * EnvironmentPropertiesReader is to set the environment variable declaration
 * mapping for config properties in the UI test
 */
public class EnvironmentPropertiesReader {

	private static EnvironmentPropertiesReader envProperties;

	private Properties properties;

	private EnvironmentPropertiesReader() {
		properties = loadProperties();
	}

	private Properties loadProperties() {
		File file = new File("./Resources/config.properties");
		FileInputStream fileInput = null;
		Properties props = new Properties();
		try {
			fileInput = new FileInputStream(file);			
			props.load(fileInput);
			fileInput.close();
		} catch (FileNotFoundException e) {
	
		} catch (IOException e) {
		
		}

		return props;
	}

	public static EnvironmentPropertiesReader getInstance() {
		if (envProperties == null) {
			envProperties = new EnvironmentPropertiesReader();
		}
		return envProperties;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public boolean hasProperty(String key) {		
		return StringUtils.isNotBlank(properties.getProperty(key));
	}
}
