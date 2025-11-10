package com.simwa3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simwa3.model.NotifikasiModel;

@Repository
public interface NotifikasiRepository extends JpaRepository<NotifikasiModel, Long> {
    
	List<NotifikasiModel> findByPenerimaOrderByCreatedAtDesc(String penerima);
   
	Long countByPenerimaAndDibacaFalse(String penerima);
	
	NotifikasiModel findByOrderId(String name);
    
}
