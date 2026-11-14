package com.example.veiculos.repository;

import com.example.veiculos.model.Carro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarroRepository extends CrudRepository<Carro, Long> {
    List<Carro> findByDisponivelTrue();
}