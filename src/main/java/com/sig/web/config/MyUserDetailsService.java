package com.sig.web.config;

import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sig.web.modelo.Usuario;
import com.sig.web.repositorio.RepositorioUsuario;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	RepositorioUsuario repositorioUsuario;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<Usuario> usuario = repositorioUsuario.findByUserName(userName);
		usuario.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
		return usuario.map(MyUserDetails::new).get();	
	}

}
