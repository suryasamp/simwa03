package com.simwa3.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifikasi")
public class NotifikasiModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipe; // contoh: "pembayaran_iuran"
    private String pesan;
    private String penerima; // "bendahara", atau code user jika multi-user
    private Boolean dibaca = false;

    private LocalDateTime createdAt = LocalDateTime.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipe() {
		return tipe;
	}

	public void setTipe(String tipe) {
		this.tipe = tipe;
	}

	public String getPesan() {
		return pesan;
	}

	public void setPesan(String pesan) {
		this.pesan = pesan;
	}

	public String getPenerima() {
		return penerima;
	}

	public void setPenerima(String penerima) {
		this.penerima = penerima;
	}

	public Boolean getDibaca() {
		return dibaca;
	}

	public void setDibaca(Boolean dibaca) {
		this.dibaca = dibaca;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
}
