package com.simwa3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.simwa3.model.IuranKasModel;
import com.simwa3.model.NotifikasiModel;
import com.simwa3.model.WargaModel;
import com.simwa3.repository.IuranKasRepository;
import com.simwa3.repository.NotifikasiRepository;
import com.simwa3.repository.WargaRepository;

@Controller
@RequestMapping("/notifikasi")
public class NotifikasiController {

	@Autowired
	private NotifikasiRepository notifikasiRepo;

	@Autowired
	private IuranKasRepository iuranKasRepo;

	@Autowired
	private WargaRepository wargaRepo;

	@GetMapping
	public String index(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		WargaModel wargaLogin = wargaRepo.findByCodeWarga(auth.getName());
		String codeWarga = wargaLogin.getCodeWarga();
		List<NotifikasiModel> listNotif = notifikasiRepo.findByPenerimaOrderByCreatedAtDesc(codeWarga);
		model.addAttribute("listNotif", listNotif);
		return "notifikasi/index";
	}

	@GetMapping("/baca/{id}")
	public String bacaNotifikasi(@PathVariable Long id, Model model) {
		NotifikasiModel notif = notifikasiRepo.findById(id).orElse(null);
		
		//update status notif jika sudah di baca
		if (notif != null) {
			
			//get order_id
			String orderId = notif.getOrderId();
			
			//update status jika sudah di baca
			notif.setDibaca(true);
			notifikasiRepo.save(notif);
			return "redirect:/iuran_kas/detail/" + orderId;
		}
		
		return "redirect:/notifikasi";
	}

	/* ===== approve pembayaran ===== */
	@PostMapping("/settlement/{orderId}")
	public String setSettlement(@PathVariable String orderId, RedirectAttributes redirect) {
		IuranKasModel iuran = iuranKasRepo.findByOrderId(orderId);
		NotifikasiModel notif = new NotifikasiModel();
		NotifikasiModel notif_data = notifikasiRepo.findByOrderId(orderId);
		
		if (iuran != null) {
			//update status iuran repo
			iuran.setStatus("settlement");
			iuranKasRepo.save(iuran);
			
			//kirim notif baru ke pengirim
			notif.setOrderId(orderId);
			notif.setTipe("Iuran Kas");
			notif.setPengirim(notif_data.getPenerima());
			notif.setPenerima(notif_data.getPengirim());
			notif.setPesan("Pembayaran Iuran dengan Order ID = " + orderId + " Telah di Konfirmasi oleh Bendahara silakan periksa status pembayaran");
			notifikasiRepo.save(notif);
			redirect.addFlashAttribute("message", "success");

		}
		return "redirect:/notifikasi";
	}
}
