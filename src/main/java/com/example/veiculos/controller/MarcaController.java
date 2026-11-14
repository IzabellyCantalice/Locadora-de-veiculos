package com.example.veiculos.controller;

import com.example.veiculos.model.Marca;
import com.example.veiculos.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/marcas")
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping
    public String listarMarcas(Model model) {
        model.addAttribute("marcas", marcaRepository.findAll());
        return "marcas/listar";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovaMarca(Model model) {
        model.addAttribute("marca", new Marca());
        return "marcas/formulario";
    }

    @PostMapping("/salvar")
    public String salvarMarca(@Valid @ModelAttribute("marca") Marca marca,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "marcas/formulario";
        }
        marcaRepository.save(marca);
        return "redirect:/marcas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarMarca(@PathVariable("id") long id,
                                               Model model) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
        model.addAttribute("marca", marca);
        return "marcas/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluirMarca(@PathVariable("id") long id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
        marcaRepository.delete(marca);
        return "redirect:/marcas";
    }
}