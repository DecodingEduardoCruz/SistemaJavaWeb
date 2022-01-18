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

import com.sig.web.modelo.Produto;
import com.sig.web.repositorio.RepositorioFornecedor;
import com.sig.web.repositorio.RepositorioProduto;

@Controller
public class ControleProdutoNovos {
	private Sort sort = Sort.by("nome").ascending();
	Pageable pageable;

	@Autowired
	private RepositorioFornecedor repositorioFornecedor;
	
	@Autowired
	private RepositorioProduto repositorioProduto;	
	
	@GetMapping("/administrativo/produto/cadastrar")
	public ModelAndView cadastrar(Produto produto) {
		ModelAndView mv =  new ModelAndView("administrativo/produtos/cadastrar");
		mv.addObject("listaFornecedores", repositorioFornecedor.findAll());
		mv.addObject("produto", produto);		
		return mv;
	}
	
	@PostMapping("/administrativo/produto/salvar")
	public ModelAndView salvar(@Valid Produto produto) {	
		try {
			repositorioProduto.saveAndFlush(produto);		
			return listar();
		}catch (Exception e) {
			return cadastrar(produto);
		}
	}
	
	
	@GetMapping("/administrativo/produtos/editar/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Optional<Produto> produto = repositorioProduto.findByCodigo(codigo);
		return cadastrar(produto.get());
	}		
	
	@GetMapping("/administrativo/produto/remover/{codigo}")
	public ModelAndView remover(@PathVariable("codigo") Long codigo) {
		Optional<Produto> produto = repositorioProduto.findByCodigo(codigo);
		repositorioProduto.delete(produto.get());
		return listar();
	}
	
	@GetMapping("/administrativo/produto/listar")
	public ModelAndView listar() {
		ModelAndView mv=new ModelAndView("administrativo/produtos/listar");				
		pageable = PageRequest.of(0, 8, sort);
		int pageNumber  = 1;
		
		Page<Produto> page = repositorioProduto.findAll(pageable);
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("totalItems", page.getTotalElements());
		mv.addObject("totalPages", page.getTotalPages());
		mv.addObject("listaProdutos", page.getContent());
		return mv;
	}
	
	@GetMapping("/administrativo/produtos/page/{pageNumber}")
	public ModelAndView page(@PathVariable("pageNumber") int pageNumber) {	
		ModelAndView mv = new ModelAndView("administrativo/produtos/listar");
		pageable = PageRequest.of(pageNumber -1, 8, sort);
		Page<Produto> page = repositorioProduto.findAll(pageable);
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("totalItems", page.getTotalElements());
		mv.addObject("totalPages", page.getTotalPages());
		mv.addObject("listaProdutos", page.getContent());
		return mv;
	}	
}
