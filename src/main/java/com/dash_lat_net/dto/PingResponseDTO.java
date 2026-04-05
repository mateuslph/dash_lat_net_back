package com.dash_lat_net.dto;

import lombok.Getter;

@Getter
public class PingResponseDTO {
    // Getters e Setters (Essenciais para o JSON)
    private final String host;
    private final boolean reachable;
    private final Long latency;
    private String data_registro;
    private String hora_registro;

    // Construtor para lista (Gráficos)
    public PingResponseDTO(String host, boolean reachable, Long latency, String data, String hora) {
        this.host = host;
        this.reachable = reachable;
        this.latency = latency;
        this.data_registro = data;
        this.hora_registro = hora;
    }

    // Construtor para ping único
    public PingResponseDTO(String host, boolean reachable, Long latency) {
        this.host = host;
        this.reachable = reachable;
        this.latency = latency;
    }

}