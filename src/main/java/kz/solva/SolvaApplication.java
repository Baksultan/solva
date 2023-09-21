package kz.solva;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Slf4j
public class SolvaApplication {

	public static void main(String[] args) {

		log.info("Solva project up");

		SpringApplication.run(SolvaApplication.class, args);
	}

}
