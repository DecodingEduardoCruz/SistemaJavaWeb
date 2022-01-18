package com.sig.web.controle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sig.web.config.Conversor;
import com.sig.web.modelo.ItemVenda;
import com.sig.web.modelo.Pessoa;
import com.sig.web.modelo.Produto;
import com.sig.web.modelo.Venda;
import com.sig.web.repositorio.RepositorioItemVenda;
import com.sig.web.repositorio.RepositorioPessoa;
import com.sig.web.repositorio.RepositorioProduto;
import com.sig.web.repositorio.RepositorioUsuario;
import com.sig.web.repositorio.RepositorioVenda;

@Controller
public class ControleVenda {
	Conversor conversor;
	List<Pessoa> listaClientes = new ArrayList<Pessoa>();
	List<ItemVenda> listaItemsVenda = new ArrayList<ItemVenda>();
	
	@Autowired
	private RepositorioPessoa repositorioPessoa;
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private RepositorioProduto repositorioProduto;
	
	@Autowired
	private RepositorioVenda repositorioVenda;
	
	@Autowired
	private RepositorioItemVenda repositorioItemVenda;
	
	
	@GetMapping("/administrativo/vendas/cadastrar")
	public ModelAndView cadastrar(Venda venda, ItemVenda itemVenda) {
		ModelAndView mv =  new ModelAndView("administrativo/vendas/cadastrar");
		mv.addObject("venda", venda);
		mv.addObject("itemVenda", itemVenda);
		mv.addObject("listaProdutos", repositorioProduto.findAll());
		mv.addObject("listaPessoas", repositorioPessoa.findAll());
		mv.addObject("listaItemsVenda", this.listaItemsVenda);
		mv.addObject("usuario", repositorioUsuario.findByUserName(getUsuarioLogado()).get());
		return mv;
	}
	
	@PostMapping("/administrativo/vendas/salvar")
	public ModelAndView salvar(String acao, Venda venda, ItemVenda itemVenda) {
		if(acao.equals("select")) {
			BigDecimal multiplicador = new BigDecimal(itemVenda.getQuantidade());				
			itemVenda.setTotalVenda(itemVenda.getProduto().getPreco().multiply(multiplicador));			
			this.listaItemsVenda.add(itemVenda);			
		}else if(acao.equals("remover")){			
			for(ItemVenda it:listaItemsVenda) {				
				if(it.getProduto().getCodigo() == itemVenda.getProduto().getCodigo()) {System.out.println("remove: ");
					this.listaItemsVenda.remove(itemVenda);
				}
			}
		}else if(acao.equals("salvar")){
			repositorioVenda.save(venda);			
			for(ItemVenda it:listaItemsVenda) {//salva itens da lista no banco
				it.setVenda(venda);
				repositorioItemVenda.save(it);
				Optional<Produto> prod = repositorioProduto.findByCodigo(it.getProduto().getCodigo());
				Produto produto = prod.get();
				short novoEstoque = (short) (produto.getEstoque() - it.getQuantidade());//desconta estoque com a venda				
				produto.setEstoque(novoEstoque);//adiciona novo estoque e novo preco de venda ao produto				
				repositorioProduto.saveAndFlush(produto);//salva  no banco novos dados				
				this.listaItemsVenda = new ArrayList<>();	//reinicia lista e retorna ao inicio			
			}
			return cadastrar(new Venda(), new ItemVenda());	
		}
		return cadastrar(venda, new ItemVenda());
	}
	
	@GetMapping("/administrativo/vendas/listar")
	public ModelAndView listar() {
		ModelAndView mv=new ModelAndView("administrativo/vendas/listar");	
		mv.addObject("relatorioVendas", repositorioItemVenda.findAll());
		return mv;
	}
	
	public String getUsuarioLogado() {
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
           authentication.getName();
        }
        return authentication.getName();

}

}
