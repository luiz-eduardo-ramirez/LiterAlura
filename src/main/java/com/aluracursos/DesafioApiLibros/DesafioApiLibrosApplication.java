package com.aluracursos.DesafioApiLibros;

import com.aluracursos.DesafioApiLibros.principal.Principal;
import com.aluracursos.DesafioApiLibros.repository.AutorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioApiLibrosApplication implements CommandLineRunner {

	private AutorRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DesafioApiLibrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.muestraElMenu();
	}
}
