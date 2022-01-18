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
import com.sig.web.modelo.Fornecedor;
import com.sig.web.repositorio.RepositorioCidade;
import com.sig.web.repositorio.RepositorioFornecedor;

@Controller
public class ControleFornecedor {
	private Sort sort = Sort.by("nome").ascending();
	Pageable pageable;

	@Autowired
	private RepositorioCidade repositorioCidade;
	
	@Autowired
	private RepositorioFornecedor repositorioFornecedor;
	
	@GetMapping("/administrativo/fornecedor/cadastrar")
	public ModelAndView cadastrar(Fornecedor fornecedor) {
		ModelAndView mv =  new ModelAndView("administrativo/fornecedores/cadastrar");
		mv.addObject("listaCidades", repositorioCidade.findAll(sort));
		mv.addObject("fornecedor", fornecedor);
		return mv;
	}
	
	@PostMapping("/administrativo/fornecedor/salvar")
	public ModelAndView salvar(@Valid Fornecedor fornecedor){
		try {
			repositorioFornecedor.saveAndFlush(fornecedor);		
			return cadastrar(new Fornecedor());
		} catch (Exception e) {
			System.out.println("Falha ao salvar: " + e);
			return cadastrar(fornecedor);
			
		}		
	}

	
	@GetMapping("/administrativo/fornecedor/editar/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Optional<Fornecedor> fornecedor = repositorioFornecedor.findByCodigo(codigo);
		return cadastrar(fornecedor.get());
	}		
	
	@GetMapping("/administrativo/fornecedor/remover/{codigo}")
	public ModelAndView remover(@PathVariable("codigo") Long codigo) {
		try {
			Optional<Fornecedor> fornecedor  = repositorioFornecedor.findByCodigo(codigo);
			repositorioFornecedor.delete(fornecedor.get());
			return listar();
		} catch (Exception e) {
			return listar();
		}
	}	
	
	@GetMapping("/administrativo/fornecedor/listar")
	public ModelAndView listar() {
		ModelAndView mv=new ModelAndView("administrativo/fornecedores/listar");				
		pageable = PageRequest.of(0, 8, sort);
		int pageNumber  = 1;
		
		Page<Fornecedor> page = repositorioFornecedor.findAll(pageable);
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("totalItems", page.getTotalElements());
		mv.addObject("totalPages", page.getTotalPages());
		mv.addObject("listaFornecedores", page.getContent());
		return mv;
	}
	
	
	@GetMapping("/administrativo/fornecedor/page/{pageNumber}")
	public ModelAndView page(@PathVariable("pageNumber") int pageNumber) {	
		ModelAndView mv = new ModelAndView("administrativo/fornecedores/listar");
		pageable = PageRequest.of(pageNumber -1, 8, sort);
		Page<Fornecedor> page = repositorioFornecedor.findAll(pageable);
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("totalItems", page.getTotalElements());
		mv.addObject("totalPages", page.getTotalPages());
		mv.addObject("listaFornecedores", page.getContent());
		return mv;
	}


}
