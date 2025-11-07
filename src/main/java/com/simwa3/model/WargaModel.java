package com.simwa3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "warga")
public class WargaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nama_warga")
	private String namaWarga;

	@Column(name = "nomer_hp")
	private String nomerHp;

	private String blok;

	@Column(name = "nomer_rumah")
	private String nomerRumah;

	@Column(name = "code_warga")
	private String codeWarga;

	@Column(name = "password_warga")
	private String passwordWarga;

	@Column(name = "role_warga")
	private String roleWarga;

	private String jabatan;

	@Column(name = "avatar_path")
	private String avatarPath;

	@Column(name = "kk_path")
	private String kkPath;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNamaWarga() {
		return namaWarga;
	}

	public void setNamaWarga(String namaWarga) {
		this.namaWarga = namaWarga;
	}

	public String getNomerHp() {
		return nomerHp;
	}

	public void setNomerHp(String nomerHp) {
		this.nomerHp = nomerHp;
	}

	public String getBlok() {
		return blok;
	}

	public void setBlok(String blok) {
		this.blok = blok;
	}

	public String getNomerRumah() {
		return nomerRumah;
	}

	public void setNomerRumah(String nomerRumah) {
		this.nomerRumah = nomerRumah;
	}

	public String getCodeWarga() {
		return codeWarga;
	}

	public void setCodeWarga(String codeWarga) {
		this.codeWarga = codeWarga;
	}

	public String getPasswordWarga() {
		return passwordWarga;
	}

	public void setPasswordWarga(String passwordWarga) {
		this.passwordWarga = passwordWarga;
	}

	public String getRoleWarga() {
		return roleWarga;
	}

	public void setRoleWarga(String roleWarga) {
		this.roleWarga = roleWarga;
	}

	public String getJabatan() {
		return jabatan;
	}

	public void setJabatan(String jabatan) {
		this.jabatan = jabatan;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	public String getKkPath() {
		return kkPath;
	}

	public void setKkPath(String kkPath) {
		this.kkPath = kkPath;
	}

}
