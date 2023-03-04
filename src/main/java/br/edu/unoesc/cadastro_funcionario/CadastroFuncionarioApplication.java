package br.edu.unoesc.cadastro_funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.edu.unoesc.cadastro_funcionario.model.Funcionario;
import br.edu.unoesc.cadastro_funcionario.service.FuncionarioService;

@SpringBootApplication
public class CadastroFuncionarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastroFuncionarioApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(FuncionarioService servico) {
		return args -> {
			
			servico.popularInicial();
			servico.incluir(new Funcionario(null, "Fulano da Silva 3", 0, new BigDecimal("4420.34"), LocalDate.of(1980, 11, 3)));
	};
}

}
