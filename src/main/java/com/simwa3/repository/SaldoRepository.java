package com.simwa3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.simwa3.model.SaldoModel;

public interface SaldoRepository extends JpaRepository<SaldoModel, Long> {
	
    @Query("SELECT s FROM SaldoModel s ORDER BY s.id ASC LIMIT 1")
    SaldoModel findCurrentSaldo();
}