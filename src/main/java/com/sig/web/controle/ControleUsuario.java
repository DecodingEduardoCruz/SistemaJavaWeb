package com.sig.web.controle;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sig.web.modelo.Usuario;
import com.sig.web.repositorio.RepositorioPessoa;
import com.sig.web.repositorio.RepositorioUsuario;

@Controller
public class ControleUsuario {
	
	@Autowired
	private RepositorioPessoa repositorioPessoa;

	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@GetMapping("/administrativo/usuario/cadastrar")
	public ModelAndView cadastrar(Usuario usuario) {
		ModelAndView mv =  new ModelAndView("administrativo/usuarios/cadastrar");
		mv.addObject("listaPessoas", repositorioPessoa.findAll());
		mv.addObject("usuario", usuario);
		return mv;
	}
	
	@PostMapping("/administrativo/usuario/salvar")
	public ModelAndView salvar(@Valid Usuario usuario) {
		try {
			repositorioUsuario.saveAndFlush(usuario);		
			return cadastrar(new Usuario());
		} catch (Exception e) {
			System.out.println("Falh ao salvar: " + e);
			return cadastrar(usuario);
		}		
	}
	
	
	@GetMapping("/administrativo/usuario/editar/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Optional<Usuario> usuario = repositorioUsuario.findByCodigo(codigo);
		return cadastrar(usuario.get());
	}		
	
	@GetMapping("/administrativo/usuario/remover/{codigo}")
	public ModelAndView remover(@PathVariable("codigo") Long codigo) {
		Optional<Usuario> usuario  = repositorioUsuario.findByCodigo(codigo);
		repositorioUsuario.delete(usuario.get());
		return listar();
	}
	
	@GetMapping("/administrativo/usuario/listar")
	public ModelAndView listar() {
		ModelAndView mv=new ModelAndView("administrativo/usuarios/listar");
		mv.addObject("listaUsuarios", repositorioUsuario.findAll());
		return mv;
	}



}
