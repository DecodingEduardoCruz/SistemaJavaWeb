package com.sig.web.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sig.web.modelo.Pessoa;


public interface RepositorioPessoa extends JpaRepository<Pessoa, Long>{
	Optional<Pessoa> findByCodigo(long codigo);
	Page<Pessoa> findAll(Pageable sort);
}
