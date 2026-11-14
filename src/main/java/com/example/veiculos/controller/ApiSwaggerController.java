package com.example.veiculos.controller;

import com.example.veiculos.model.*;
import com.example.veiculos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class ApiSwaggerController {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LocacaoRepository locacaoRepository;

    @GetMapping("/marcas")
    public Iterable<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }

    @PostMapping("/marcas")
    public Marca criarMarca(@RequestParam String nome) {
        return marcaRepository.save(new Marca(nome));
    }

    @GetMapping("/carros")
    public Iterable<Carro> listarCarros() {
        return carroRepository.findAll();
    }

    @GetMapping("/clientes")
    public Iterable<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @GetMapping("/locacoes")
    public Iterable<Locacao> listarLocacoes() {
        return locacaoRepository.findAll();
    }

    @PostMapping("/locacoes")
    public Locacao registrarLocacao(@RequestParam Long clienteId,
                                    @RequestParam Long carroId,
                                    @RequestParam(defaultValue = "10") int dias) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
        Carro carro = carroRepository.findById(carroId).orElseThrow();
        carro.setDisponivel(false);
        carroRepository.save(carro);

        Locacao locacao = new Locacao();
        locacao.setCliente(cliente);
        locacao.setCarro(carro);
        locacao.setDataRetirada(LocalDate.now());
        locacao.setDataDevolucao(LocalDate.now().plusDays(dias));
        locacao.setAtiva(true);
        locacao.calcularValoresAutomaticos();
        return locacaoRepository.save(locacao);
    }

    @PutMapping("/locacoes/{id}/devolver")
    public Locacao devolverCarro(@PathVariable Long id) {
        Locacao locacao = locacaoRepository.findById(id).orElseThrow();
        locacao.setAtiva(false);
        if (locacao.getCarro() != null) {
            locacao.getCarro().setDisponivel(true);
            carroRepository.save(locacao.getCarro());
        }
        return locacaoRepository.save(locacao);
    }
}