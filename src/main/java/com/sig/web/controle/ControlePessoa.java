package com.sig.web.controle;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.sig.web.modelo.Pessoa;
import com.sig.web.repositorio.RepositorioCidade;
import com.sig.web.repositorio.RepositorioPessoa;

@Controller
public class ControlePessoa {
	private Sort sort = Sort.by("nome").ascending();
	Pageable pageable;

	@Autowired
	private RepositorioCidade repositorioCidade;
	
	@Autowired
	private RepositorioPessoa repositorioPessoa;
	
	@GetMapping("/administrativo/pessoa/cadastrar")
	public ModelAndView cadastrar(Pessoa pessoa) {
		ModelAndView mv =  new ModelAndView("administrativo/pessoas/cadastrar");
		mv.addObject("listaCidades", repositorioCidade.findAll(sort));
		mv.addObject("pessoa", pessoa);
		return mv;
	}
	
	@PostMapping("/administrativo/pessoa/salvar")
	public ModelAndView salvar(@Valid Pessoa pessoa){
		try {
			repositorioPessoa.saveAndFlush(pessoa);		
			return cadastrar(new Pessoa());
		} catch (Exception e) {
			System.out.println("Falha ao salvar: " + e);
			return cadastrar(pessoa);			
		}		
	}
	
	@GetMapping("/administrativo/pessoa/editar/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Optional<Pessoa> pessoa = repositorioPessoa.findByCodigo(codigo);
		return cadastrar(pessoa.get());
	}		
	
	@GetMapping("/administrativo/pessoa/remover/{codigo}")
	public ModelAndView remover(@PathVariable("codigo") Long codigo) {
		try {
			Optional<Pessoa> pessoa  = repositorioPessoa.findByCodigo(codigo);
			repositorioPessoa.delete(pessoa.get());
			return listar();
		} catch (Exception e) {
			return listar();
		}
	}

	@GetMapping("/administrativo/pessoa/listar")
	public ModelAndView listar() {
		ModelAndView mv=new ModelAndView("administrativo/pessoas/listar");				
		pageable = PageRequest.of(0, 8, sort);
		int pageNumber  = 1;
		
		Page<Pessoa> page = repositorioPessoa.findAll(pageable);
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("totalItems", page.getTotalElements());
		mv.addObject("totalPages", page.getTotalPages());
		mv.addObject("listaPessoas", page.getContent());
		return mv;
	}
	
	@GetMapping("/administrativo/pessoa/page/{pageNumber}")
	public ModelAndView page(@PathVariable("pageNumber") int pageNumber) {	
		ModelAndView mv = new ModelAndView("administrativo/pessoas/listar");
		pageable = PageRequest.of(pageNumber -1, 8, sort);
		Page<Pessoa> page = repositorioPessoa.findAll(pageable);
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("totalItems", page.getTotalElements());
		mv.addObject("totalPages", page.getTotalPages());
		mv.addObject("listaPessoas", page.getContent());
		return mv;
	}
	
	
}
