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

import com.sig.web.modelo.Estado;
import com.sig.web.repositorio.RepositorioEstado;

@Controller
public class ControleEstado {
	private Sort sort = Sort.by("nome").ascending();
	Pageable pageable;

	@Autowired
	private RepositorioEstado repositorioEstado;
	
	@GetMapping("/administrativo/estado/cadastrar")
	public ModelAndView cadastrar(Estado estado) {
		ModelAndView mv =  new ModelAndView("administrativo/estados/cadastrar");
		mv.addObject("estado", estado);
		return mv;
	}
	
	@PostMapping("/administrativo/estado/salvar")
	public ModelAndView salvar(@Valid Estado estado) {
		try {
			repositorioEstado.saveAndFlush(estado);		
			return cadastrar(new Estado());
		} catch (Exception e) {
			System.out.println("Falh ao salvar: " + e);
			return cadastrar(estado);
		}		
	}
	

	
	@GetMapping("/administrativo/estado/editar/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Optional<Estado> estado = repositorioEstado.findByCodigo(codigo);
		return cadastrar(estado.get());
	}		
	
	@GetMapping("/administrativo/estado/remover/{codigo}")
	public ModelAndView remover(@PathVariable("codigo") Long codigo) {
		Optional<Estado> estado  = repositorioEstado.findByCodigo(codigo);
		repositorioEstado.delete(estado.get());
		return listar();
	}
	
	@GetMapping("/administrativo/estado/listar")
	public ModelAndView listar() {
		ModelAndView mv=new ModelAndView("administrativo/estados/listar");			
		pageable = PageRequest.of(0, 8, sort);
		int pageNumber  = 1;
		
		Page<Estado> page = repositorioEstado.findAll(pageable);	
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("totalItems", page.getTotalElements());
		mv.addObject("totalPages", page.getTotalPages());
		mv.addObject("listaEstados", page.getContent());
		return mv;
	}
	
	@GetMapping("/administrativo/estado/page/{pageNumber}")
	public ModelAndView page(@PathVariable("pageNumber") int pageNumber) {	
		ModelAndView mv = new ModelAndView("administrativo/estados/listar");
		pageable = PageRequest.of(pageNumber -1, 8, sort);
		Page<Estado> page = repositorioEstado.findAll(pageable);
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("totalItems", page.getTotalElements());
		mv.addObject("totalPages", page.getTotalPages());
		mv.addObject("listaEstados", page.getContent());
		return mv;
	}

}
