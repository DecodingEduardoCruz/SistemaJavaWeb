package com.sig.web.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sig.web.modelo.EntradaProduto;

public interface RepositorioEntradaProduto extends JpaRepository<EntradaProduto, Long>{
	Optional<EntradaProduto> findByCodigo(long codigo);
	Page<EntradaProduto> findAll(Pageable sort);

}
