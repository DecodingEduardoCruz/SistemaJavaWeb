package com.sig.web.repositorio;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sig.web.modelo.Usuario;


public interface RepositorioUsuario extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByUserName(String userName);
	Optional<Usuario> findByCodigo(long codigo);
	Page<Usuario> findAll(Pageable sort);
}
