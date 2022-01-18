package com.sig.web.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sig.web.modelo.Produto;


public interface RepositorioProduto extends JpaRepository<Produto, Long>{
	Optional<Produto> findByCodigo(long codigo);
	Page<Produto> findAll(Pageable sort);	
}
