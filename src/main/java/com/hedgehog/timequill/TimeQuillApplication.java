package com.hedgehog.timequill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TimeQuillApplication {

    // main is the method used by spring boot to start the time quill application
    public static void main(String[] args) {
        SpringApplication.run(TimeQuillApplication.class, args);
    }
}
