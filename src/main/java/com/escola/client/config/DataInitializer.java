package com.escola.client.config;

import com.escola.client.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DataInitializer implements CommandLineRunner {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    Random random = new Random();

    @Override
    public void run(String... args) {
        log.info("Iniciando a verificação de dados iniciais...");

        log.info("Verificação de dados iniciais concluída.");
    }

}