package com.sig.web.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sig.web.modelo.ItemVenda;


public interface RepositorioItemVenda extends PagingAndSortingRepository<ItemVenda, Long>{
	Optional<ItemVenda> findByCodigo(long codigo);
	Page<ItemVenda> findAll(Pageable sort);
}
