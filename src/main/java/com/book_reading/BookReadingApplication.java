package com.book_reading;

import com.book_reading.config.MomoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class BookReadingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookReadingApplication.class, args);
    }

}
