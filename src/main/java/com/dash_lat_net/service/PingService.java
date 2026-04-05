package com.dash_lat_net.service;

import com.dash_lat_net.config.PingProperties;
import com.dash_lat_net.dto.PingResponseDTO;
import com.dash_lat_net.entity.PingLog;
import com.dash_lat_net.repository.PingLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PingService {

    private final PingLogRepository repository;
    private final PingProperties properties;

    public PingService(PingLogRepository repository, PingProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    /**
     * Busca os logs para o Dashboard do React
     */
    public List<PingResponseDTO> findAllLogs() {
        // Busca todos e converte para DTO
        return repository.findAll().stream()
                .map(log -> new PingResponseDTO(
                        log.getHost(),
                        log.isReachable(),
                        log.getLatency(),
                        log.getDataRegistro() != null ? log.getDataRegistro().toString() : "",
                        log.getHoraRegistro() != null ? log.getHoraRegistro().toString() : ""
                ))
                .collect(Collectors.toList());
    }

    public List<String> getHosts() {
        return properties.getHosts();
    }

    /**
     * Executa o Ping utilizando o endereço vindo da Entity e SALVA no banco
     */
    @Transactional
    public PingResponseDTO executarPingESalvar(String endereco) {
        boolean reachable = false;
        Long latency = null;

        try {
            ProcessBuilder pb = buildPingCommand(endereco);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            // Regex para capturar latência em Português (tempo=) ou Inglês (time=)
            Pattern pattern = Pattern.compile("(?:tempo|time)\\s*[=<]\\s*(\\d+)\\s*ms", Pattern.CASE_INSENSITIVE);

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    reachable = true;
                    latency = Long.parseLong(matcher.group(1));
                    break;
                }
            }
            process.waitFor();
        } catch (Exception e) {
            System.err.println("Erro ao executar ping para " + endereco + ": " + e.getMessage());
        }

        // Persiste o resultado no banco de dados
        salvarNoBanco(endereco, reachable, latency);

        // Retorna o DTO para o frontend
        return new PingResponseDTO(endereco, reachable, latency);
    }

    private void salvarNoBanco(String host, boolean reachable, Long latency) {
        // Cria a nova entidade com os dados obtidos
        PingLog log = new PingLog(host, reachable, latency);

        System.out.println("Hibernate: Gravando log -> Host: " + host + " | Latência: " + latency + "ms");

        // Salva efetivamente no PostgreSQL
        repository.save(log);
    }

    private ProcessBuilder buildPingCommand(String host) {
        String os = System.getProperty("os.name").toLowerCase();
        // Ajusta o comando conforme o Sistema Operacional
        return os.contains("win")
                ? new ProcessBuilder("ping", "-n", "1", host)
                : new ProcessBuilder("ping", "-c", "1", host);
    }
}