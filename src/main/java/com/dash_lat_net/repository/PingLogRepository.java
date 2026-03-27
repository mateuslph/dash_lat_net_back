package com.dash_lat_net.repository;

import com.dash_lat_net.entity.PingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PingLogRepository extends JpaRepository<PingLog, Long> {

    // Conta quantos registros já foram salvos em uma data específica
    long countByDataRegistro(LocalDate dataRegistro);

    // Traz uma lista das datas que o sistema efetivamente rodou, da mais nova para a mais velha
    @Query("SELECT DISTINCT p.dataRegistro FROM PingLog p ORDER BY p.dataRegistro DESC")
    List<LocalDate> findDiasAtivos();

    // Apaga todos os registros de uma data específica
    @Modifying
    @Transactional
    @Query("DELETE FROM PingLog p WHERE p.dataRegistro = :data")
    void deleteByDataRegistro(LocalDate data);
}