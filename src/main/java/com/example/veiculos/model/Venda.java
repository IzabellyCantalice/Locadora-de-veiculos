package com.example.veiculos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "Selecione um cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "carro_id", nullable = false)
    @NotNull(message = "Selecione um carro")
    private Carro carro;

    @NotNull(message = "Informe o número de parcelas")
    @Min(value = 1, message = "Mínimo de 1 parcela")
    private Integer parcelas = 1;

    // Campos preenchidos e calculados AUTOMATICAMENTE pelo sistema:
    private LocalDate dataVenda;
    private Double valorFinalCalculado;
    private Double valorParcelaCalculado;
    private String regraAplicada;

    public Venda() {}

    @PrePersist
    @PreUpdate
    public void processarCalculosAutomaticos() {
        if (this.dataVenda == null) {
            this.dataVenda = LocalDate.now();
        }
        if (this.carro != null && this.carro.getValor() != null && this.parcelas != null && this.parcelas >= 1) {
            double precoBase = this.carro.getValor();
            if (this.parcelas == 1) {
                // À vista: 5% de desconto automático
                this.valorFinalCalculado = Math.round(precoBase * 0.95 * 100.0) / 100.0;
                this.valorParcelaCalculado = this.valorFinalCalculado;
                this.regraAplicada = "À vista (5% de desconto automático)";
            } else {
                // Parcelado: acréscimo de 1.5% por parcela
                double fatorJuros = 1.0 + (this.parcelas * 0.015);
                this.valorFinalCalculado = Math.round(precoBase * fatorJuros * 100.0) / 100.0;
                this.valorParcelaCalculado = Math.round((this.valorFinalCalculado / this.parcelas) * 100.0) / 100.0;
                this.regraAplicada = this.parcelas + "x com juros automáticos (1,5% a.m.)";
            }
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Carro getCarro() { return carro; }
    public void setCarro(Carro carro) { this.carro = carro; }

    public Integer getParcelas() { return parcelas; }
    public void setParcelas(Integer parcelas) { this.parcelas = parcelas; }

    public LocalDate getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDate dataVenda) { this.dataVenda = dataVenda; }

    public Double getValorFinalCalculado() { return valorFinalCalculado; }
    public void setValorFinalCalculado(Double valorFinalCalculado) { this.valorFinalCalculado = valorFinalCalculado; }

    public Double getValorParcelaCalculado() { return valorParcelaCalculado; }
    public void setValorParcelaCalculado(Double valorParcelaCalculado) { this.valorParcelaCalculado = valorParcelaCalculado; }

    public String getRegraAplicada() { return regraAplicada; }
    public void setRegraAplicada(String regraAplicada) { this.regraAplicada = regraAplicada; }
}