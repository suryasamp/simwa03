package com.simwa3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simwa3.model.IuranModel;

@Repository
public interface IuranRepository extends JpaRepository<IuranModel, Long> {
    IuranModel findByOrderId(String orderId);
	List<IuranModel> findByCodeWargaOrderByUpdatedAtDesc(String name);
}