package com.sig.web.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sig.web.modelo.Fornecedor;


public interface RepositorioFornecedor extends JpaRepository<Fornecedor, Long>{
	Optional<Fornecedor> findByCodigo(long codigo);
	Page<Fornecedor> findAll(Pageable sort);
}
