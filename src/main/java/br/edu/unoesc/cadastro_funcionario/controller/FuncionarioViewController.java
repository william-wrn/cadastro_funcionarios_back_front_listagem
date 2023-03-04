package br.edu.unoesc.cadastro_funcionario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FuncionarioViewController {
    
    @GetMapping
	public String home(Model modelo) {
		return "index.html";
	}
}
