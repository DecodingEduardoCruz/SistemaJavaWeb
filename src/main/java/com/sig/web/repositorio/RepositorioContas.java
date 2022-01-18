package com.sig.web.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sig.web.modelo.Contas;


public interface RepositorioContas extends PagingAndSortingRepository<Contas, Long>{
	Optional<Contas> findByCodigo(long codigo);
	Page<Contas> findAll(Pageable sort);
}
