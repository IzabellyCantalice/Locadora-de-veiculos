package com.example.veiculos.repository;

import com.example.veiculos.model.Marca;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends CrudRepository<Marca, Long> {
}