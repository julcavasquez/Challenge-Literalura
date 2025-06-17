package com.aluracursos.challengeLiteratura.Literatura;

import com.aluracursos.challengeLiteratura.Literatura.principal.Principal;
import com.aluracursos.challengeLiteratura.Literatura.repositorio.AutorRepository;
import com.aluracursos.challengeLiteratura.Literatura.repositorio.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioDeLiteraturaApplication implements CommandLineRunner {
	private final Principal principal;

	public static void main(String[] args) {
		SpringApplication.run(DesafioDeLiteraturaApplication.class, args);
	}

	public DesafioDeLiteraturaApplication(Principal principal){
		this.principal = principal;

	}

	@Override
	public void run(String... args) throws Exception {
		principal.muestraElMenu();
	}

}
