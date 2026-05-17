package com.gestion.fabrication.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration de l'application.
 *
 * Cours du prof slide 20 :
 * "Définir un bean de type ModelMapper"
 *
 * @Bean : Spring gère une seule instance de ModelMapper
 * et l'injecte partout où on en a besoin (@Autowired / constructeur).
 */
@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
