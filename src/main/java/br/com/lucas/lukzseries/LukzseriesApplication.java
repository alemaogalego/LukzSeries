package br.com.lucas.lukzseries;

import br.com.lucas.lukzseries.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LukzseriesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LukzseriesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibirMenu();
	}
}
