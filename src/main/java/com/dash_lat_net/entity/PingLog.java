package com.dash_lat_net.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ping_log")
public class PingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String host;
    private Boolean reachable;
    private Long latency;

    @Column(name = "data_registro")
    private LocalDate dataRegistro; // Para agrupar por dia (e contar os 30 dias ativos)

    @Column(name = "hora_registro")
    private LocalTime horaRegistro; // A hora exata do ping

    // Construtor vazio obrigatório do JPA
    public PingLog() {}

    public PingLog(String host, Boolean reachable, Long latency) {
        this.host = host;
        this.reachable = reachable;
        this.latency = latency;
        this.dataRegistro = LocalDate.now();
        this.horaRegistro = LocalTime.now();
    }

    // Gere os Getters e Setters aqui (ou use @Data se tiver o Lombok)
}