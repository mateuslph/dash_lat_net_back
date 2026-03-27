package com.dash_lat_net.service;

import com.dash_lat_net.dto.PingResponseDTO;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PingService {

    public PingResponseDTO pingHost(String host) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("ping", "-n", "1", host);
            Process process = processBuilder.start();

            // Usando o charset padrão do sistema para evitar letras estranhas
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean reachable = false;
            Long latency = null;

            // Regex melhorada: aceita "tempo=15ms", "time = 15ms", "tempo < 1ms", etc.
            Pattern pattern = Pattern.compile("(?:tempo|time)\\s*[=<]\\s*(\\d+)\\s*ms", Pattern.CASE_INSENSITIVE);

            System.out.println("--- Iniciando Ping para " + host + " ---");

            while ((line = reader.readLine()) != null) {
                // Imprime a linha exata que o Windows devolveu para o Java ver
                System.out.println("Console do Windows: " + line);

                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    reachable = true;
                    latency = Long.parseLong(matcher.group(1));
                    System.out.println("LATÊNCIA ENCONTRADA: " + latency + "ms");
                }
            }

            process.waitFor();
            return new PingResponseDTO(host, reachable, latency);

        } catch (Exception e) {
            System.err.println("Erro crítico ao rodar o ping: " + e.getMessage());
            return new PingResponseDTO(host, false, null);
        }
    }
}