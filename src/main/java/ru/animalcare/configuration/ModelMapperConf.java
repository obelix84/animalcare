package ru.animalcare.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConf {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
