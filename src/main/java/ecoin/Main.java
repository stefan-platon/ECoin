package ecoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//import view.LoginMenu;

/**
 * Starting point of the program.
 */
@SpringBootApplication
@EnableJpaRepositories("/ecoin/repository")
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
