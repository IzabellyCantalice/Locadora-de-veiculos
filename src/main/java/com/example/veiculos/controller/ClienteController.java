package com.example.veiculos.controller;

import com.example.veiculos.model.Cliente;
import com.example.veiculos.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "clientes/listar";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    @PostMapping("/salvar")
    public String salvarCliente(@Valid @ModelAttribute("cliente") Cliente cliente,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "clientes/formulario";
        }
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCliente(@PathVariable("id") long id) {
        clienteRepository.findById(id).ifPresent(c -> clienteRepository.delete(c));
        return "redirect:/clientes";
    }
}