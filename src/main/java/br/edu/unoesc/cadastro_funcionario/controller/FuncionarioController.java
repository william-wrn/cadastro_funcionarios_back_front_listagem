package br.edu.unoesc.cadastro_funcionario.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.unoesc.cadastro_funcionario.dto.FuncionarioDTO;
import br.edu.unoesc.cadastro_funcionario.model.Funcionario;
import br.edu.unoesc.cadastro_funcionario.service.FuncionarioService;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {
    @Autowired
	private FuncionarioService servico;

    @GetMapping
	public Iterable<Funcionario> listar() {
		return servico.listar();
	}

	@GetMapping("/paginado")
	public ResponseEntity<Page<FuncionarioDTO>> listarPaginado(Pageable pagina) {
		return ResponseEntity.ok(servico.listarPaginado(pagina));
	}

	@GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Funcionario> porId(@PathVariable(value = "id") Long id){
		Optional<Funcionario> resp = servico.porId(id);
		if (resp.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(resp.get());
	}

	@GetMapping(value = "/xml/{id}")
	public ResponseEntity<Funcionario> porIdXML(@PathVariable(value = "id") Long id){
		Optional<Funcionario> resp = servico.porId(id);
		if (resp.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(resp.get());
	}

	@GetMapping("/find")
	public List<Funcionario> porNome(@RequestParam("nome") String nome){
		return servico.buscarPorNome(nome);
	}

	@GetMapping("/salarios")
	public List<Funcionario> porFaixaSalarial(
			@RequestParam(value = "min",defaultValue = "0.00") String min,
			@RequestParam(value = "max", defaultValue = "9999999999999") String max){
		return servico.buscarPorFaixaSalarial(new BigDecimal(min),new BigDecimal(max));
	}

	@PostMapping("/incluir")
	public ResponseEntity<Void> incluir(@RequestBody Funcionario funcionario){
		funcionario = servico.incluir(funcionario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
									.path("/{id}")
									.buildAndExpand(funcionario.getId())
									.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PatchMapping("/alterar")
	public ResponseEntity<Funcionario> atualizar(@RequestParam("id") Long id,
												@RequestBody Funcionario funcionario){
		funcionario.setId(id);
		try{
			funcionario = servico.alterar(id, funcionario);
			return ResponseEntity.ok(funcionario);
		} catch (ObjectNotFoundException e){
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
		try{
			servico.excluir(id);
			return ResponseEntity.status(202).build();
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
