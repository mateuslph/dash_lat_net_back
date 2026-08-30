package com.dash_lat_net.controller;

import com.dash_lat_net.dto.PingResponseDTO;
import com.dash_lat_net.service.PingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ping")
@CrossOrigin(origins = "http://localhost:5173")
public class PingController {

    private final PingService pingService;

    public PingController(PingService pingService) {
        this.pingService = pingService;
    }

    @GetMapping("/logs")
    public List<PingResponseDTO> getAllLogs() {
        return pingService.findAllLogs();
    }

    @GetMapping("/hosts")
    public List<String> getHosts() {
        return pingService.getHosts();
    }

    @GetMapping("/{host}")
    public PingResponseDTO ping(@PathVariable String host) {
        return pingService.executarPingESalvar(host);
    }
}