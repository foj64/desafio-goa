package com.foj.goa.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.foj.goa.domain.Pdv;


@Repository
public interface PdvRepository extends JpaRepository<Pdv, Integer> {
	
	@Transactional(readOnly=true)
	Optional<Pdv> findById(Integer id);
	
	@Transactional(readOnly=true)
	Pdv findByNome(String nome);
	
	@Transactional(readOnly=true)
	Page<Pdv> findByNomeContainingAndCidadeContainingAndCepContaining(
										@Param("nome") String nome,
										@Param("cidade") String cidade,
										@Param("cep") String cep,
										Pageable pageRequest);

}
