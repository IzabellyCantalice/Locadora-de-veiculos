package com.example.veiculos.controller;

import com.example.veiculos.model.Carro;
import com.example.veiculos.model.Locacao;
import com.example.veiculos.repository.CarroRepository;
import com.example.veiculos.repository.ClienteRepository;
import com.example.veiculos.repository.LocacaoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/locacoes")
public class LocacaoController {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CarroRepository carroRepository;

    @GetMapping
    public String listarLocacoes(Model model) {
        model.addAttribute("locacoes", locacaoRepository.findAll());
        return "locacoes/listar";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovaLocacao(Model model) {
        model.addAttribute("locacao", new Locacao());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("carros", carroRepository.findByDisponivelTrue());
        return "locacoes/formulario";
    }

    @PostMapping("/salvar")
    public String salvarLocacao(@Valid @ModelAttribute("locacao") Locacao locacao,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteRepository.findAll());
            model.addAttribute("carros", carroRepository.findByDisponivelTrue());
            return "locacoes/formulario";
        }
        Carro carroSelecionado = carroRepository.findById(locacao.getCarro().getId()).orElse(null);
        if (carroSelecionado != null) {
            carroSelecionado.setDisponivel(false);
            carroRepository.save(carroSelecionado);
            locacao.setCarro(carroSelecionado);
        }
        locacao.setAtiva(true);
        locacao.calcularValoresAutomaticos();
        locacaoRepository.save(locacao);
        return "redirect:/locacoes";
    }

    @GetMapping("/devolver/{id}")
    public String devolverVeiculo(@PathVariable("id") long id) {
        Locacao locacao = locacaoRepository.findById(id).orElse(null);
        if (locacao != null && locacao.getAtiva()) {
            locacao.setAtiva(false);
            Carro carro = locacao.getCarro();
            if (carro != null) {
                carro.setDisponivel(true);
                carroRepository.save(carro);
            }
            locacaoRepository.save(locacao);
        }
        return "redirect:/locacoes";
    }
}