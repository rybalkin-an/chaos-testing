package com.github.rybalkin_an.app;

import org.springframework.boot.SpringApplication;

public class TestApplication {

        public static void main(String[] args) {
            SpringApplication
                    .from(Application::main)
                    .run(args);
        }

}
