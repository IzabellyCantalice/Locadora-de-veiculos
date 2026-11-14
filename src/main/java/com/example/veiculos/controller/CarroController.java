package com.example.veiculos.controller;

import com.example.veiculos.model.Carro;
import com.example.veiculos.repository.CarroRepository;
import com.example.veiculos.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/carros")
public class CarroController {

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping
    public String listarCarros(Model model) {
        model.addAttribute("carros", carroRepository.findAll());
        return "carros/listar";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovoCarro(Model model) {
        model.addAttribute("carro", new Carro());
        model.addAttribute("marcas", marcaRepository.findAll());
        return "carros/formulario";
    }

    @PostMapping("/salvar")
    public String salvarCarro(@Valid @ModelAttribute("carro") Carro carro,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("marcas", marcaRepository.findAll());
            return "carros/formulario";
        }
        carroRepository.save(carro);
        return "redirect:/carros";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCarro(@PathVariable("id") long id,
                                               Model model) {
        Carro carro = carroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
        model.addAttribute("carro", carro);
        model.addAttribute("marcas", marcaRepository.findAll());
        return "carros/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCarro(@PathVariable("id") long id) {
        Carro carro = carroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
        carroRepository.delete(carro);
        return "redirect:/carros";
    }
}