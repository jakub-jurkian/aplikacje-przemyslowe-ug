package pl.edu.ug.cmms;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:beans.xml")
public class CmmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmmsApplication.class, args);
    }
}
