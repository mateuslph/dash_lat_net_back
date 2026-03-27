/*package com.dash_lat_net.service;

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
*/
package com.dash_lat_net.service;

import com.dash_lat_net.dto.PingResponseDTO;
import com.dash_lat_net.entity.PingLog;
import com.dash_lat_net.repository.PingLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PingService {

    @Autowired
    private PingLogRepository repository;

    // Variável para garantir que a limpeza de 30 dias rode apenas 1 vez por dia
    private LocalDate ultimaLimpezaExecutada = null;

    public PingResponseDTO pingHost(String host) {
        try {
            ProcessBuilder pb = new ProcessBuilder("ping", "-n", "1", host);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean reachable = false;
            Long latency = null;

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

            // === NOVA LÓGICA DE BANCO DE DADOS AQUI ===
            LocalDate hoje = LocalDate.now();

            // 1. Verifica se precisa limpar dias antigos (Mais de 30 dias ATIVOS)
            if (!hoje.equals(ultimaLimpezaExecutada)) {
                limparDiasAntigos();
                ultimaLimpezaExecutada = hoje;
            }

            // 2. Conta os registros de hoje. Se o sistema reiniciar, ele conta os que já existem.
            long registrosHoje = repository.countByDataRegistro(hoje);

            // 3. Só salva se ainda não bateu os 1.000 registros no dia
            if (registrosHoje < 1000) {
                PingLog log = new PingLog(host, reachable, latency);
                repository.save(log);
            }
            // ==========================================

            return new PingResponseDTO(host, reachable, latency);

        } catch (Exception e) {
            System.err.println("Erro crítico: " + e.getMessage());
            return new PingResponseDTO(host, false, null);
        }
    }

    private void limparDiasAntigos() {
        // Pega todos os dias em que o sistema gerou dados
        List<LocalDate> diasAtivos = repository.findDiasAtivos();

        // Se tiver mais de 30 dias registrados, apaga do 31º em diante
        if (diasAtivos.size() > 30) {
            List<LocalDate> diasParaApagar = diasAtivos.subList(30, diasAtivos.size());
            for (LocalDate dataAntiga : diasParaApagar) {
                repository.deleteByDataRegistro(dataAntiga);
                System.out.println("Limpando dados antigos do dia: " + dataAntiga);
            }
        }
    }
}