package com.sig.web.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sig.web.modelo.Estado;


public interface RepositorioEstado extends JpaRepository<Estado, Long>{
	Optional<Estado> findByCodigo(long codigo);
	Page<Estado> findAll(Pageable sort);
}
