package com.dash_lat_net.controller;

import com.dash_lat_net.dto.PingResponseDTO;
import com.dash_lat_net.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ping")
@CrossOrigin(origins = "http://localhost:5173") // Permite o seu React consumir a API
public class PingController {

    @Autowired
    private PingService pingService;

    // Rota que você pode chamar no navegador ou no Postman para testar
    @GetMapping("/{host}")
    public PingResponseDTO getPing(@PathVariable String host) {
        return pingService.pingHost(host);
    }
}