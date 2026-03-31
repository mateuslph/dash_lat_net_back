package com.dash_lat_net.controller;

import com.dash_lat_net.entity.PingLog;
import com.dash_lat_net.repository.PingLogRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/metrics")
@CrossOrigin
public class MetricsController {

    private final PingLogRepository repository;

    public MetricsController(PingLogRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/latest")
    public List<PingLog> ultimo() {
        return repository.findTop100ByOrderByIdDesc();
    }
}