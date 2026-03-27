package com.dash_lat_net.dto;

public class PingResponseDTO {

    private String host;
    private Boolean reachable;
    private Long latency;

    public PingResponseDTO(String host, Boolean reachable, Long latency) {
        this.host = host;
        this.reachable = reachable;
        this.latency = latency;
    }

    public String getHost() {
        return host;
    }

    public Boolean getReachable() {
        return reachable;
    }

    public Long getLatency() {
        return latency;
    }
}