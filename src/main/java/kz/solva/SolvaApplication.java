package kz.solva;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SolvaApplication {

	public static void main(String[] args) {

		log.info("Solva project up");

		SpringApplication.run(SolvaApplication.class, args);
	}

}
