package com.sig.web.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sig.web.modelo.EntradaItens;
import com.sig.web.modelo.EntradaProduto;
import com.sig.web.modelo.Produto;
import com.sig.web.repositorio.RepositorioEntradaItens;
import com.sig.web.repositorio.RepositorioEntradaProduto;
import com.sig.web.repositorio.RepositorioFornecedor;
import com.sig.web.repositorio.RepositorioProduto;
import com.sig.web.repositorio.RepositorioUsuario;

@Controller
public class ControleEntradaProduto {
	List<EntradaItens> listaEntrada = new ArrayList<EntradaItens>();

	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private RepositorioFornecedor repositoriofornecedor;
	
	@Autowired
	private RepositorioEntradaProduto repositorioEntradaProduto;
	
	@Autowired
	private RepositorioEntradaItens repositorioEntradaItens;
	
	@Autowired
	private RepositorioProduto repositorioProduto;
	
	@GetMapping("/administrativo/compras/cadastrar")
	public ModelAndView cadastrar(EntradaProduto entrada, EntradaItens entradaItens) {
		ModelAndView mv =  new ModelAndView("administrativo/compras/cadastrar");
		mv.addObject("listaFornecedores", repositoriofornecedor.findAll());
		mv.addObject("entrada", entrada);
		mv.addObject("entradaItens", entradaItens);		
		mv.addObject("listaEntradaItens", this.listaEntrada);
		mv.addObject("listaProdutos", repositorioProduto.findAll());
		mv.addObject("listaUsuarios", repositorioUsuario.findAll());
		mv.addObject("listaFornecedores", repositoriofornecedor.findAll());
		return mv;
	}	
	
	@PostMapping("/administrativo/compras/salvar")
	public ModelAndView salvar(String acao, Long codigo,  EntradaProduto entrada, EntradaItens entradaItens ) {	
		if(acao.equals("select")) {// adiciona itens a lista
			this.listaEntrada.add(entradaItens);			
		}else if(acao.equals("salvar")){ // salva produtos no banco
			repositorioEntradaProduto.saveAndFlush(entrada);
			//salva itens da lista no banco
			for(EntradaItens it:listaEntrada) {
				it.setEntrada(entrada);
				repositorioEntradaItens.saveAndFlush(it);
				Optional<Produto> prod = repositorioProduto.findByCodigo(it.getProduto().getCodigo());
				Produto produto = prod.get();				
				//soma estoque com a compra
				short novoEstoque = (short) (produto.getEstoque() + it.getQuantidade());
				//adiciona novo estoque e novo preco de venda ao produto
				produto.setEstoque(novoEstoque);
				produto.setCusto(it.getPrecoCompra());				
				produto.setPreco(it.getPrecoCompra());
				//salva  no banco novos dados
				repositorioProduto.saveAndFlush(produto);
				//reinicia lista e retorna ao inicio
				this.listaEntrada = new ArrayList<>();				
			}
			return cadastrar(new EntradaProduto(), new EntradaItens());	
		}	
		return cadastrar(entrada, new EntradaItens());
	}
	
	public String getUser() {	
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String nome;
		if (principal instanceof UserDetails) {
		    nome = ((UserDetails)principal).getUsername();
		} else {
		    nome = principal.toString();
		}
		return nome;	
	}
	
	@GetMapping("/administrativo/compras/listar")
	public ModelAndView listar() {
		ModelAndView mv=new ModelAndView("administrativo/compras/listar");	
		mv.addObject("listaItens", repositorioEntradaItens.findAll());
		return mv;
	}	
}