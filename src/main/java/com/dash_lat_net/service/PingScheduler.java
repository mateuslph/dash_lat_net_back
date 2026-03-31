package com.dash_lat_net.service;

import com.dash_lat_net.config.PingProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PingScheduler {

    private final PingService pingService;
    private final PingProperties pingProperties;

    public PingScheduler(PingService pingService, PingProperties pingProperties) {
        this.pingService = pingService;
        this.pingProperties = pingProperties;
    }

    @Scheduled(fixedRateString = "${ping.interval}")
    public void executarPingsAutomaticos() {
        List<String> hostsConfigurados = pingProperties.getHosts();

        if (hostsConfigurados != null && !hostsConfigurados.isEmpty()) {
            for (String url : hostsConfigurados) {
                pingService.executarPingESalvar(url.trim());
            }
        }
    }
}