package ru.alfabattle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.alfabattle.conf.SpringConfiguration;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(SpringConfiguration.class, args);
  }
}
