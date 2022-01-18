package com.sig.web;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sig.web.modelo.Cidade;
import com.sig.web.modelo.Estado;
import com.sig.web.modelo.Fornecedor;
import com.sig.web.modelo.Pessoa;
import com.sig.web.modelo.Produto;
import com.sig.web.modelo.Usuario;
import com.sig.web.repositorio.RepositorioCidade;
import com.sig.web.repositorio.RepositorioEstado;
import com.sig.web.repositorio.RepositorioFornecedor;
import com.sig.web.repositorio.RepositorioPessoa;
import com.sig.web.repositorio.RepositorioProduto;
import com.sig.web.repositorio.RepositorioUsuario;

@SpringBootApplication
public class ApplicationRunner implements CommandLineRunner{
	@Autowired
	RepositorioEstado re;
	
	@Autowired
	RepositorioCidade rc;
	
	@Autowired
	RepositorioPessoa rp;
	
	@Autowired
	RepositorioUsuario ru;
	
	@Autowired
	RepositorioFornecedor rf;
	
	@Autowired
	RepositorioProduto rpro;
	
	
	@Override
	public void run(String... args) throws Exception {	
		if(re.findAll().isEmpty()) {
			Estado estado = new Estado();
			estado.setCodigo(0);
			estado.setNome("Paraná");
			estado.setSigla("PR");
			re.save(estado);
			

			Cidade cidade = new Cidade();
			cidade.setCodigo(0);
			cidade.setNome("Palmas");
			cidade.setEstado(estado);			
			rc.save(cidade);
			
			Pessoa pessoa = new Pessoa();
			pessoa.setCodigo(0);
			pessoa.setNome("Super Usuario");
			pessoa.setCpf("000.000.000.00");
			pessoa.setBairro("Santuário");
			pessoa.setContato("(046) 99914-7716");
			pessoa.setEmail("tecnologiaesolucoespr@gmail.com");
			pessoa.setCidade(cidade);
			rp.save(pessoa);
			
			Usuario usuario = new Usuario();
			usuario.setCodigo(0);
			usuario.setUserName("admin");
			usuario.setPassword("tulipa");
			usuario.setPessoa(pessoa);
			usuario.setActive(true);
			usuario.setRoles("ADMIN");
			ru.save(usuario);	
			
			Fornecedor fornecedor = new Fornecedor();
			fornecedor.setCodigo(0);
			fornecedor.setNome("Serviços");
			fornecedor.setCidade(cidade);
			rf.save(fornecedor);
			
			Produto produto = new Produto();
			produto.setCodigo(0);
			produto.setNome("Serviço de formatação");
			produto.setPreco(new BigDecimal(60));
			produto.setCusto(new BigDecimal(0));
			produto.setEstoque((short) 999);			
			produto.setFornecedor(fornecedor);
			produto.setDataCompra("2021-12-31 0:0:00.12000");
			rpro.save(produto);
		
			
			
		} 		
	}

}
