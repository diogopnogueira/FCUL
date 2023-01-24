package com.seatseller;


import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Properties;
import java.io.FileInputStream;
import java.util.Optional;
import java.util.function.Supplier;
/*
 * Configuration gathers all possible configuration variables defined in the config.properties files.
 * 
 *  Configuration follows the singleton pattern.
 */
public class Configuration {
	private static Configuration INSTANCE = new Configuration();

	// Passo 2: getter estático da instância
	public static Configuration getInstance() {
		return INSTANCE;
	}

	private Properties p = new Properties();

	// Passo 3: Construtor privado
	private Configuration() {
		try {
			p.load(new FileInputStream("config.properties"));
		} catch (IOException e) {
			// Uses default values
		}
	}

	public String getString(String key, String defaultValue) {
		Optional<String> value = Optional.ofNullable(p.getProperty(key));
		return value.orElse(defaultValue);
		/*
		return value.orElseThrow( () -> {
			throw new RuntimeException();
		});
		 */
	}

	public int getInteger(String key, int defaultValue) {
		Optional<String> value = Optional.ofNullable(p.getProperty(key));

		// Alt A
		// int r = defaultValue;
		// value.ifPresent( s -> { r = Integer.parseInt(s); } );
		// return r

		return value.map(Integer::parseInt).orElse(defaultValue);

	}

	@SuppressWarnings("unchecked")
	public <T> T getDynamic(String key, Supplier<T> defaultValueSupplier) {

		String className = getString(key, "");
		if (!className.isEmpty()) {
			try {
				Class<T> c = (Class<T>) Class.forName(className);
				if (c != null)
					return c.newInstance();
			} catch (ClassCastException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			}
		}
		return defaultValueSupplier.get();

	}
}
