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
import com.sig.web.modelo.Cidade;
import com.sig.web.repositorio.RepositorioCidade;
import com.sig.web.repositorio.RepositorioEstado;

@Controller
public class ControleCidade {
	private Sort sort = Sort.by("nome").ascending();
	Pageable pageable;


	@Autowired
	private RepositorioCidade repositorioCidade;
	
	@Autowired
	private RepositorioEstado repositorioEstado;
	
	@GetMapping("/administrativo/cidade/cadastrar")
	public ModelAndView cadastrar(Cidade cidade) {
		ModelAndView mv =  new ModelAndView("administrativo/cidades/cadastrar");
		mv.addObject("listaEstados", repositorioEstado.findAll(sort));
		mv.addObject("cidade", cidade);
		return mv;
	}
	
	@PostMapping("/administrativo/cidade/salvar")
	public ModelAndView salvar(@Valid Cidade cidade){
		try {
			repositorioCidade.save(cidade);		
			return cadastrar(new Cidade());
		} catch (Exception e) {
			System.out.println("Falha ao salvar: " + e);
			return cadastrar(cidade);
			
		}		
	}
	
	@GetMapping("/administrativo/cidade/editar/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Optional<Cidade> cidade = repositorioCidade.findByCodigo(codigo);
		return cadastrar(cidade.get());
	}		
	
	@GetMapping("/administrativo/cidade/remover/{codigo}")
	public ModelAndView remover(@PathVariable("codigo") Long codigo) {
		try {
			Optional<Cidade> cidade  = repositorioCidade.findByCodigo(codigo);
			repositorioCidade.delete(cidade.get());
			return listar();
		} catch (Exception e) {
			return listar();
		}
	}
	
	@GetMapping("/administrativo/cidade/listar")
	public ModelAndView listar() {
		ModelAndView mv=new ModelAndView("administrativo/cidades/listar");		
		pageable = PageRequest.of(0, 8, sort);
		int pageNumber  = 1;
		
		Page<Cidade> page = repositorioCidade.findAll(pageable);
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("totalItems", page.getTotalElements());
		mv.addObject("totalPages", page.getTotalPages());
		mv.addObject("listaCidades", page.getContent());
		return mv;
	}
	
	@GetMapping("/administrativo/cidade/page/{pageNumber}")
	public ModelAndView page(@PathVariable("pageNumber") int pageNumber) {	
		ModelAndView mv = new ModelAndView("administrativo/cidades/listar");
		pageable = PageRequest.of(pageNumber -1, 8, sort);
		Page<Cidade> page = repositorioCidade.findAll(pageable);
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("totalItems", page.getTotalElements());
		mv.addObject("totalPages", page.getTotalPages());
		mv.addObject("listaEstados", page.getContent());
		return mv;
	}

}
