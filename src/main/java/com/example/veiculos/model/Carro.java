package com.example.veiculos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    @NotNull(message = "O valor da diária é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    private Double valor;

    private Boolean disponivel = true;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    @NotNull(message = "Selecione uma marca")
    private Marca marca;

    public Carro() {}

    public Carro(String modelo, Double valor, Marca marca) {
        this.modelo = modelo;
        this.valor = valor;
        this.marca = marca;
        this.disponivel = true;
    }

    // Cálculo automático 1: Classifica a categoria do carro pelo valor da diária
    public String getCategoriaAutomatica() {
        if (this.valor == null) return "Padrão";
        if (this.valor <= 150.0) return "Econômico";
        if (this.valor <= 280.0) return "SUV / Intermediário";
        return "Executivo Premium";
    }

    // Cálculo automático 2: Seguro proteção diária (10% do valor)
    public Double getSeguroDiario() {
        return (this.valor != null) ? Math.round(this.valor * 0.10 * 100.0) / 100.0 : 0.0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public Boolean getDisponivel() { return disponivel != null ? disponivel : true; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }

    public Marca getMarca() { return marca; }
    public void setMarca(Marca marca) { this.marca = marca; }
}