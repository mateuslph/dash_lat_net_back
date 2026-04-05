package com.dash_lat_net.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ping_logs")
public class PingLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Getters e Setters (Essenciais)...
    @Getter
    private String host;
    @Getter
    private boolean reachable;
    @Getter
    private Long latency;

    @Getter
    @Column(name = "data_registro")
    private LocalDate dataRegistro;

    @Getter
    @Column(name = "hora_registro")
    private LocalTime horaRegistro;

    public PingLog() {}

    public PingLog(String host, boolean reachable, Long latency) {
        this.host = host;
        this.reachable = reachable;
        this.latency = latency;
        this.dataRegistro = LocalDate.now();
        this.horaRegistro = LocalTime.now();
    }

}