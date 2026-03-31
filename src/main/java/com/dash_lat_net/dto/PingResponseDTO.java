package com.dash_lat_net.dto;

public class PingResponseDTO {
    private String host;
    private boolean reachable;
    private Long latency;
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

    // Getters e Setters (Essenciais para o JSON)
    public String getHost() { return host; }
    public boolean isReachable() { return reachable; }
    public Long getLatency() { return latency; }
    public String getData_registro() { return data_registro; }
    public String getHora_registro() { return hora_registro; }
}