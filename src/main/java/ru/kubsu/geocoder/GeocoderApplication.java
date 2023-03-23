package ru.kubsu.geocoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chidasophia
 */
@SpringBootApplication
@SuppressWarnings({"PMD.UseUtilityClass", "HideUtilityClassConstructor"})
public class GeocoderApplication {
    public static void main(final String[] args) {

        SpringApplication.run(GeocoderApplication.class, args);
    }

}
