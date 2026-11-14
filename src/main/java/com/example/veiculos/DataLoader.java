package com.example.veiculos;

import com.example.veiculos.model.Carro;
import com.example.veiculos.model.Cliente;
import com.example.veiculos.model.Marca;
import com.example.veiculos.repository.CarroRepository;
import com.example.veiculos.repository.ClienteRepository;
import com.example.veiculos.repository.MarcaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner inicializarFrota(
            MarcaRepository marcaRepo,
            CarroRepository carroRepo,
            ClienteRepository clienteRepo) {
        return args -> {
            if (marcaRepo.count() == 0) {
                Marca toyota = marcaRepo.save(new Marca("Toyota"));
                Marca jeep = marcaRepo.save(new Marca("Jeep"));
                Marca fiat = marcaRepo.save(new Marca("Fiat"));

                carroRepo.save(new Carro("Corolla XEi 2.0", 240.0, toyota));
                carroRepo.save(new Carro("Compass Longitude", 310.0, jeep));
                carroRepo.save(new Carro("Argo Drive 1.3", 135.0, fiat));
                carroRepo.save(new Carro("Hilux SRX 4x4", 420.0, toyota));

                Cliente c1 = new Cliente();
                c1.setNome("Izabelly Cantalice");
                c1.setCpf("123.456.789-00");
                c1.setTelefone("(92) 99123-4567");
                clienteRepo.save(c1);

                Cliente c2 = new Cliente();
                c2.setNome("Carlos Eduardo Silva");
                c2.setCpf("987.654.321-11");
                c2.setTelefone("(92) 98877-6655");
                clienteRepo.save(c2);
            }
        };
    }
}