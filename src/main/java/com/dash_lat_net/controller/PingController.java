package com.dash_lat_net.controller;

import com.dash_lat_net.dto.PingResponseDTO;
import com.dash_lat_net.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ping")
@CrossOrigin(origins = "http://localhost:5173")
public class PingController {

    @Autowired
    private PingService pingService;

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