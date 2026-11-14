package com.example.veiculos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoContrato;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "Selecione um cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "carro_id", nullable = false)
    @NotNull(message = "Selecione um veículo disponível")
    private Carro carro;

    @NotNull(message = "Informe a data de retirada")
    private LocalDate dataRetirada = LocalDate.now();

    @NotNull(message = "Informe a data de devolução")
    private LocalDate dataDevolucao = LocalDate.now().plusDays(3);

    // Campos preenchidos e calculados AUTOMATICAMENTE pelo sistema
    private Long diasLocacao;
    private Double valorTotal;
    private String planoAplicado;
    private Boolean ativa = true;

    public Locacao() {}

    @PrePersist
    @PreUpdate
    public void calcularValoresAutomaticos() {
        if (this.codigoContrato == null || this.codigoContrato.isBlank()) {
            int numeroAleatorio = ThreadLocalRandom.current().nextInt(1000, 9999);
            this.codigoContrato = "LOC-2026-" + numeroAleatorio;
        }
        if (this.dataRetirada == null) {
            this.dataRetirada = LocalDate.now();
        }
        if (this.dataDevolucao == null || this.dataDevolucao.isBefore(this.dataRetirada)) {
            this.dataDevolucao = this.dataRetirada.plusDays(1);
        }

        long dias = ChronoUnit.DAYS.between(this.dataRetirada, this.dataDevolucao);
        if (dias < 1) dias = 1;
        this.diasLocacao = dias;

        if (this.carro != null && this.carro.getValor() != null) {
            double diariaComSeguro = this.carro.getValor() + this.carro.getSeguroDiario();
            double bruto = dias * diariaComSeguro;

            if (dias >= 15) {
                this.valorTotal = Math.round(bruto * 0.85 * 100.0) / 100.0;
                this.planoAplicado = "Quinzenal (15% OFF Automático)";
            } else if (dias >= 7) {
                this.valorTotal = Math.round(bruto * 0.90 * 100.0) / 100.0;
                this.planoAplicado = "Semanal (10% OFF Automático)";
            } else {
                this.valorTotal = Math.round(bruto * 100.0) / 100.0;
                this.planoAplicado = "Tarifa Padrão + Seguro 10%";
            }
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigoContrato() { return codigoContrato; }
    public void setCodigoContrato(String codigoContrato) { this.codigoContrato = codigoContrato; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Carro getCarro() { return carro; }
    public void setCarro(Carro carro) { this.carro = carro; }

    public LocalDate getDataRetirada() { return dataRetirada; }
    public void setDataRetirada(LocalDate dataRetirada) { this.dataRetirada = dataRetirada; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public Long getDiasLocacao() { return diasLocacao; }
    public void setDiasLocacao(Long diasLocacao) { this.diasLocacao = diasLocacao; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public String getPlanoAplicado() { return planoAplicado; }
    public void setPlanoAplicado(String planoAplicado) { this.planoAplicado = planoAplicado; }

    public Boolean getAtiva() { return ativa != null ? ativa : true; }
    public void setAtiva(Boolean ativa) { this.ativa = ativa; }
}