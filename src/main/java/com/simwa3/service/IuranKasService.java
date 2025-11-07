package com.simwa3.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simwa3.controller.IuranKasController;
import com.simwa3.model.IuranKasModel;
import com.simwa3.model.NotifikasiModel;
import com.simwa3.repository.IuranKasRepository;
import com.simwa3.repository.NotifikasiRepository;

@Service
public class IuranKasService {

	@Autowired
	private IuranKasRepository iuranKasRepo;
	
	@Autowired
	private NotifikasiRepository notifRepo;
	
	@Autowired
	private WhatsAppService waService;

	public IuranKasModel storeDataIuranKas(IuranKasModel iuranKas, String no_bendahara) {

		/*===== Insert Data Iuran Kas =====*/
		IuranKasModel entity = new IuranKasModel();
		String randomChar = UUID.randomUUID().toString().substring(0, 8);
		String blok = iuranKas.getCodeWarga();
		String orderId = blok + "-" + randomChar;
		entity.setOrderId(orderId);
		entity.setNamaWarga(iuranKas.getNamaWarga());
		entity.setCodeWarga(iuranKas.getCodeWarga());
		entity.setBulan(iuranKas.getBulan());
		entity.setTahun(iuranKas.getTahun());
		entity.setStatus("pending");
		entity.setTotal(iuranKas.getTotal());
		iuranKasRepo.save(entity);
		
		/*===== Send Notif ke WA Bendahara =====*/
		String pesan = "🔔 *Notifikasi Pembayaran Iuran Kas Baru*\n\n" 
				+ "Dari: *" + iuranKas.getNamaWarga() + "*\n"
				+ "Blok: *" + iuranKas.getCodeWarga() + "*\n"
				+ "Periode: *" + iuranKas.getBulan() + "*\n"
				+ "Tahun: *" + iuranKas.getTahun() + "*\n"
				+ "Jumlah: *Rp " + iuranKas.getTotal() + "*\n\n"
				+ "Silakan cek mutasi rekening RT untuk verifikasi pembayaran.";

		String nomorBendahara = no_bendahara;
		waService.sendMessage(nomorBendahara, pesan);
		
		/*===== Send Notif ke Bendahara =====*/
	    NotifikasiModel notif = new NotifikasiModel();
	    notif.setTipe("pembayaran_iuran");
	    notif.setPenerima("bendahara");
	    notif.setPesan("Warga " + iuranKas.getNamaWarga() +
	        " melakukan pembayaran iuran kas periode " + iuranKas.getBulan() +
	        " " + iuranKas.getTahun() + " sebesar Rp " + iuranKas.getTotal());
	    notifRepo.save(notif);

		return entity;
	}

}
