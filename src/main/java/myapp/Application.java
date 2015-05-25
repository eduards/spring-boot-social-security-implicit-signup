package myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * The main {@link SpringBootApplication}
 */
@SpringBootApplication
public class Application {

  /**
   * Runs the Spring Boot application
   * @param args - the cli arguments
   */
  public static void main(String[] args) {
    ApplicationContext ctx =
        SpringApplication.run(Application.class, args);
  }

}
