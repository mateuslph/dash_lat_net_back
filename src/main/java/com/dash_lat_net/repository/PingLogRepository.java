package com.dash_lat_net.repository;

import com.dash_lat_net.entity.PingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PingLogRepository extends JpaRepository<PingLog, Long> {

    // Conta quantos registros existem no dia
    long countByDataRegistro(LocalDate dataRegistro);

    // Remove todos os registros de um dia específico
    void deleteByDataRegistro(LocalDate dataRegistro);

    // Retorna os dias distintos com registros (ordenado do mais recente)
    @Query("SELECT DISTINCT p.dataRegistro FROM PingLog p ORDER BY p.dataRegistro DESC")
    List<LocalDate> findDiasAtivos();

    // Últimos 100 registros (para dashboard)
    List<PingLog> findTop100ByOrderByIdDesc();

    // Histórico por host (opcional, mas MUITO útil pro gráfico)
    List<PingLog> findByHostOrderByIdDesc(String host);

    // Últimos registros de um host específico (limite)
    List<PingLog> findTop50ByHostOrderByIdDesc(String host);
}