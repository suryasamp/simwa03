package com.simwa3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simwa3.model.IuranKasModel;

@Repository
public interface IuranKasRepository extends JpaRepository<IuranKasModel, Long> {
	
	//get list data bedasarkan Code Warga
	List<IuranKasModel> findByCodeWargaOrderByUpdatedAtDesc(String name);
	
}


