package com.example.veiculos.controller;

import com.example.veiculos.model.Locacao;
import com.example.veiculos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LocacaoRepository locacaoRepository;

    @GetMapping("/")
    public String paginaInicial(Model model) {
        long totalMarcas = marcaRepository.count();
        long totalCarros = carroRepository.count();
        long disponiveis = carroRepository.findByDisponivelTrue().size();
        long totalClientes = clienteRepository.count();

        long locacoesAtivas = 0;
        double receitaTotal = 0.0;
        for (Locacao l : locacaoRepository.findAll()) {
            if (l.getAtiva()) locacoesAtivas++;
            if (l.getValorTotal() != null) receitaTotal += l.getValorTotal();
        }

        model.addAttribute("totalMarcas", totalMarcas);
        model.addAttribute("totalCarros", totalCarros);
        model.addAttribute("carrosDisponiveis", disponiveis);
        model.addAttribute("carrosAlugados", totalCarros - disponiveis);
        model.addAttribute("totalClientes", totalClientes);
        model.addAttribute("locacoesAtivas", locacoesAtivas);
        model.addAttribute("receitaTotal", Math.round(receitaTotal * 100.0) / 100.0);
        return "index";
    }
}