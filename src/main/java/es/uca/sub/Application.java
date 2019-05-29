package es.uca.sub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.spring.annotation.EnableVaadin;



@EnableVaadin
@SpringBootApplication
public class Application{
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
