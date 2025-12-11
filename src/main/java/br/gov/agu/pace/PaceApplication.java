package br.gov.agu.pace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaceApplication.class, args);
	}

}
