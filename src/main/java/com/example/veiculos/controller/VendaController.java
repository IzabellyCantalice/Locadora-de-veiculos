package com.example.veiculos.controller;

import com.example.veiculos.model.Venda;
import com.example.veiculos.repository.CarroRepository;
import com.example.veiculos.repository.ClienteRepository;
import com.example.veiculos.repository.VendaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CarroRepository carroRepository;

    @GetMapping
    public String listarVendas(Model model) {
        model.addAttribute("vendas", vendaRepository.findAll());
        return "vendas/listar";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovaVenda(Model model) {
        model.addAttribute("venda", new Venda());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("carros", carroRepository.findAll());
        return "vendas/formulario";
    }

    @PostMapping("/salvar")
    public String salvarVenda(@Valid @ModelAttribute("venda") Venda venda,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteRepository.findAll());
            model.addAttribute("carros", carroRepository.findAll());
            return "vendas/formulario";
        }
        venda.processarCalculosAutomaticos();
        vendaRepository.save(venda);
        return "redirect:/vendas";
    }

    @GetMapping("/excluir/{id}")
    public String excluirVenda(@PathVariable("id") long id) {
        vendaRepository.findById(id).ifPresent(v -> vendaRepository.delete(v));
        return "redirect:/vendas";
    }
}