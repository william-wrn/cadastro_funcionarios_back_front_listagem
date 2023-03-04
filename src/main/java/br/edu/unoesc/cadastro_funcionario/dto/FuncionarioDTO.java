package br.edu.unoesc.cadastro_funcionario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.edu.unoesc.cadastro_funcionario.model.Funcionario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FuncionarioDTO implements Serializable {
    String nome;
	Integer num_dep;
	BigDecimal salario;
    LocalDate nascimento;
    
    public FuncionarioDTO(Funcionario funcionario){
        this.nome = funcionario.getNome();
        this.nascimento = funcionario.getNascimento();
        this.salario = funcionario.getSalario();
        this.num_dep = funcionario.getNum_dep();
    }
}
