package com.simwa3.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.simwa3.Simwa3Application;
import com.simwa3.dto.DTOWarga;
import com.simwa3.model.WargaModel;
import com.simwa3.repository.WargaRepository;

@Service
public class WargaService {

	private final Simwa3Application simwa3Application;

	@Autowired
	private WargaRepository wargaRepo;

	WargaService(Simwa3Application simwa3Application) {
		this.simwa3Application = simwa3Application;
	}

	@Value("${file.upload-dir}")
	private String uploadRoot;

	public WargaModel storeDataWarga(WargaModel warga, MultipartFile avatar, MultipartFile uploadKK) {
		System.out.println("====================");
		System.out.println("Service : Nama Warga=" + warga.getNamaWarga());
		System.out.println("Service : Nomer Hp=" + warga.getNomerHp());
		System.out.println("Service : Blok=" + warga.getBlok());
		System.out.println("Service : Nomer Rumah=" + warga.getNomerRumah());
		System.out.println("Service : Role=" + warga.getRoleWarga());
		System.out.println("Service : Jabatan=" + warga.getJabatan());
	
	    WargaModel target;

	    if (warga.getId() != null) {
	        // MODE UPDATE
	        target = wargaRepo.findById(warga.getId())
	                .orElseThrow(() -> new RuntimeException("Data warga dengan ID " + warga.getId() + " tidak ditemukan"));
	    } else {
	        // MODE INSERT
	        target = new WargaModel();
	        target.setPasswordWarga(new BCryptPasswordEncoder().encode(warga.getPasswordWarga()));
	    }

	    target.setNamaWarga(warga.getNamaWarga());
	    target.setNomerHp(warga.getNomerHp());
	    target.setBlok(warga.getBlok());
	    target.setNomerRumah(warga.getNomerRumah());
	    target.setRoleWarga(warga.getRoleWarga());
	    target.setJabatan(warga.getJabatan());
	    target.setCodeWarga(warga.getBlok() + warga.getNomerRumah());

		try {
			// =================== Avatar ===================
			if (avatar != null && !avatar.isEmpty()) {
				// Validasi ekstensi sederhana
				if (!avatar.getOriginalFilename().toLowerCase().endsWith(".jpg")
						&& !avatar.getOriginalFilename().toLowerCase().endsWith(".png")) {
					throw new RuntimeException("Avatar harus JPG atau PNG");
				}

				String avatarFilename = System.currentTimeMillis() + "_" + avatar.getOriginalFilename();
				Path avatarPath = Paths.get(uploadRoot, "avatars", avatarFilename);
				Files.createDirectories(avatarPath.getParent());
				avatar.transferTo(avatarPath.toFile());

				if (target.getAvatarPath() != null) {
					Path oldAvatarPath = Paths.get(uploadRoot, "avatars", target.getAvatarPath());
					Files.deleteIfExists(oldAvatarPath);
				}

				 target.setAvatarPath(avatarFilename);
				System.out.println("Avatar tersimpan di: " + avatarPath.toAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Gagal menyimpan Avatar!");
		}

		try {
			// =================== KK PDF ===================
			if (uploadKK != null && !uploadKK.isEmpty()) {
				if (!uploadKK.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
					throw new RuntimeException("KK harus file PDF");
				}

				String kkFilename = System.currentTimeMillis() + "_" + uploadKK.getOriginalFilename();
				Path kkPath = Paths.get(uploadRoot, "kk", kkFilename);
				Files.createDirectories(kkPath.getParent());
				uploadKK.transferTo(kkPath.toFile());

				if (target.getKkPath() != null) {
					Path oldKKPath = Paths.get(uploadRoot, "kk", target.getKkPath());
					Files.deleteIfExists(oldKKPath);
				}

				target.setKkPath(kkFilename);
				System.out.println("KK tersimpan di: " + kkPath.toAbsolutePath());
			}
			System.out.println("====================");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Gagal menyimpan KK!");
		}

		return wargaRepo.save(target);
	}

	// Get Data Warga By ID
	public WargaModel getWargaById(Integer id) {
		return wargaRepo.findById(id).orElse(null); // atau bisa lempar exception kalau tidak ketemu
	}

	// Delete Data Warga By ID
	public void deleteWargaById(Integer id) {
		wargaRepo.deleteById(id);
	}
}
