package com.example.veiculos.repository;

import com.example.veiculos.model.Locacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocacaoRepository extends CrudRepository<Locacao, Long> {
}