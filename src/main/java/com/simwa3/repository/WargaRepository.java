package com.simwa3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simwa3.model.WargaModel;

@Repository
public interface WargaRepository extends JpaRepository<WargaModel, Integer> {
	WargaModel findByCodeWarga(String code_warga);
}
