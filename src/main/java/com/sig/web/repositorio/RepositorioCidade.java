package com.sig.web.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sig.web.modelo.Cidade;


public interface RepositorioCidade extends PagingAndSortingRepository<Cidade, Long>{
	Optional<Cidade> findByCodigo(long codigo);
	Page<Cidade> findAll(Pageable sort);
}
