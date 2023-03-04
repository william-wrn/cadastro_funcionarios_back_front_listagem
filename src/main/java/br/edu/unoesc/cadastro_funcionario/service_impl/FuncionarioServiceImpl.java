package br.edu.unoesc.cadastro_funcionario.service_impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.unoesc.cadastro_funcionario.dto.FuncionarioDTO;
import br.edu.unoesc.cadastro_funcionario.model.Funcionario;
import br.edu.unoesc.cadastro_funcionario.repository.FuncionarioRepository;
import br.edu.unoesc.cadastro_funcionario.service.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    private FuncionarioRepository repositorio;

    @Override
    public void popularInicial() {
        repositorio.saveAll(List.of(
                new Funcionario(null, "Fulano da Silva", 10, new BigDecimal("1500.34"), LocalDate.of(2000, 10, 2)),
                new Funcionario(null, "Spring Function", 0, new BigDecimal("2500.00"), LocalDate.of(2005, 9, 3)),
                new Funcionario(null, "Is dificult", 3, new BigDecimal("1800.00"), LocalDate.of(2002, 3, 21)),
                new Funcionario(null, "Lets Go", 7, new BigDecimal("1670.45"), LocalDate.of(1990, 6, 15)),
                new Funcionario(null, "Mariazinha das Graças", 0, new BigDecimal("23500.54"),
                        LocalDate.of(1998, 9, 17)),
                new Funcionario(null, "Joaozinho Public", 2, new BigDecimal("900.23"), LocalDate.of(1993, 1, 5)),
                new Funcionario(null, "Ms. Cabal", 1, new BigDecimal("5.98"), LocalDate.of(1790, 5, 19))));
    }

    @Override
    public Funcionario incluir(Funcionario funcionario) {
        funcionario.setId(null);
        return repositorio.save(funcionario);
    }

    @Override
    public Funcionario alterar(Long id, Funcionario funcionario) {
        var f = repositorio.findById(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException("Funcionario não encontrado! Id: "
                                + id + ", Tipo: " + Funcionario.class.getName(), null));
        f.setNascimento(funcionario.getNascimento());
        f.setNome(funcionario.getNome());
        f.setNum_dep(funcionario.getNum_dep());
        f.setSalario(funcionario.getSalario());

        return repositorio.save(f);
    }

    @Override
    public void excluir(Long id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
        } else {
            throw new ObjectNotFoundException("Funcionário não encontrado! Id: "
                    + id + ", Tipo: " + Funcionario.class.getName(), null);
        }

    }

    @Override
    public List<Funcionario> listar() {
        List<Funcionario> livros = new ArrayList<Funcionario>();

        // Recupera todos os registros da tabela
        Iterable<Funcionario> itens = repositorio.findAll();

        // Cria uma cópia dos dados na lista 'livros'
        itens.forEach(livros::add);

        return livros;
    }

    @Override
    public Page<FuncionarioDTO> listarPaginado(Pageable pagina) {
        Page<Funcionario> lista = repositorio.findAll(pagina);
		
		Page<FuncionarioDTO> listaDTO = lista.map(funcionario -> new FuncionarioDTO(funcionario));
		
		return listaDTO;
    }

    @Override
    public Funcionario buscar(Long id) {
        Optional<Funcionario> obj = repositorio.findById(id);

        return obj.orElseThrow(
                () -> new ObjectNotFoundException("Funcionario não encontrado! Id: "
                        + id + ", Tipo: " + Funcionario.class.getName(), null));
    }

    @Override
    public Funcionario buscarPorId(Long id) {
        return repositorio.findById(id).orElse(new Funcionario());
    }

    @Override
    public Optional<Funcionario> porId(Long id) {
        return repositorio.findById(id);
    }

    @Override
    public List<Funcionario> buscarPorNome(String nome) {
        return repositorio.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public List<Funcionario> buscarPorFaixaSalarial(BigDecimal salarioMinimo, BigDecimal salarioMaximo) {
        return repositorio.findSalarioEntre(salarioMinimo, salarioMaximo);
    }

    @Override
    public List<Funcionario> buscaePossuiDependentes(Integer numDep) {
        return repositorio.findSeDependentes();
    }

}
