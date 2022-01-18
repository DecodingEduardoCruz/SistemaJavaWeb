package com.sig.web.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sig.web.modelo.Venda;


public interface RepositorioVenda extends JpaRepository<Venda, Long>{
	Optional<Venda> findByCodigo(long codigo);
}
