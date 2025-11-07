package com.simwa3.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "iuran_kas")
public class IuranKasModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="order_id")
	private String orderId;
	
	@Column(name="code_warga")
	private String codeWarga;
	
	@Column(name="nama_warga")
	private String namaWarga;
	
	@Column(name = "bulan")
	private String bulan;
	
	@Column(name="tahun")
	private Integer tahun;
	
	@Column(name="status")
	private String status;
	
	@Column(name="payment_type")
	private String paymentType;
	
	@Column(name="total")
	private BigDecimal total;
	
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime updatedAt;

	//GET SET
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCodeWarga() {
		return codeWarga;
	}

	public void setCodeWarga(String codeWarga) {
		this.codeWarga = codeWarga;
	}

	public String getNamaWarga() {
		return namaWarga;
	}

	public void setNamaWarga(String namaWarga) {
		this.namaWarga = namaWarga;
	}

	public String getBulan() {
		return bulan;
	}

	public void setBulan(String bulan) {
		this.bulan = bulan;
	}

	public Integer getTahun() {
		return tahun;
	}

	public void setTahun(Integer tahun) {
		this.tahun = tahun;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}



	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	

	


}
