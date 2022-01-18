package com.sig.web.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sig.web.modelo.EntradaItens;

public interface RepositorioEntradaItens extends JpaRepository<EntradaItens, Long>{
	Optional<EntradaItens> findByCodigo(long codigo);
	Page<EntradaItens> findAll(Pageable sort);

}
