package com.charles.knightonline;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class KnightOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnightOnlineApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenApi(@Value("${app.version}") String appVersion,
                                 @Value("${app.description}") String appDescription) {
        return new OpenAPI().components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info().title("Knight Online API")
                        .version(appVersion)
                        .description(appDescription)
                        .license(new License().name("Knight Online by Charles Lana")
                                .url("https://github.com/charleslana")));
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
