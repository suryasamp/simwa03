package com.simwa3.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.simwa3.model.IuranKasModel;
import com.simwa3.model.WargaModel;
import com.simwa3.repository.IuranKasRepository;
import com.simwa3.repository.WargaRepository;
import com.simwa3.service.IuranKasService;

@Controller
@RequestMapping("/iuran_kas")
public class IuranKasController {
	
	@Autowired
	private WargaRepository wargaRepo;
	
	@Autowired
	private IuranKasService iuranKasSevice;
	
	@Autowired
	private IuranKasRepository iuranKasRepo;

	@GetMapping
	public String index(Model model) {
		
		//Cek identias yg login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		WargaModel wargaLogin = wargaRepo.findByCodeWarga(auth.getName());
		
		//Ambil list data iuran berdasaran code warga
		List<IuranKasModel> listIuran = iuranKasRepo.findByCodeWargaOrderByUpdatedAtDesc(auth.getName());
		
		model.addAttribute("wargaLogin", wargaLogin);
		model.addAttribute("listIuran", listIuran);
		return "/management_iuran/iuran_kas/index"; // templates/management_iuran/iuran_kas/index.html
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		WargaModel wargaLogin = wargaRepo.findByCodeWarga(auth.getName());
		List<String> bulanList = List.of("Jan", "Feb", "Mar",
				"Apr", "Mei", "Jun", "Jul", "Agu", "Sep",
				"Okt", "Nov", "Des");
		model.addAttribute("bulanList", bulanList);
		model.addAttribute("wargaLogin", wargaLogin);
		return "management_iuran/iuran_kas/create";
	}

	@PostMapping("/store")
	public String store(@ModelAttribute IuranKasModel iuranKasModel, Model model, RedirectAttributes redirect) {
		System.out.println("==== Controller IuranKas =====");
		String no_bendahara = wargaRepo.findByJabatan("bendahara").getNomerHp();
		System.out.println("no_bendahara:"+no_bendahara);
		try {
			iuranKasSevice.storeDataIuranKas(iuranKasModel,no_bendahara);
			
			redirect.addFlashAttribute("message", "success");
		} catch (Exception e) {
			System.out.println("Error Stor Data Iuran Kas:"+e);
			redirect.addFlashAttribute("message", "error");
		}
		return "redirect:/iuran_kas";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("title", "Edit Name");
		model.addAttribute("id", id);
		return "management_iuran/iuran_kas/edit";
	}

	@PostMapping("/update/{id}")
	public String update(@PathVariable Long id, @RequestParam String name) {
		// Update logic here
		return "redirect:/management_iuran/iuran_kas";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		// Delete logic here
		return "redirect:/management_iuran/iuran_kas";
	}
	
	@GetMapping("/detail/{orderId}")
	public String showIuranDetail(@PathVariable String orderId, Model model) {
		// 1. Ambil Data Detail
        // Panggil service untuk mencari data iuran lengkap berdasarkan orderId
         IuranKasModel iuranDetail = iuranKasRepo.findByOrderId(orderId);
        
        // 2. Tambahkan Data ke Model
        // Contoh: Jika data tidak ditemukan
         if (iuranDetail == null) {
        	 return "redirect:/management_iuran/iuran_kas"; // Ganti dengan halaman error Anda
         }

        // Untuk contoh ini, kita asumsikan data berhasil diambil dan ditambahkan ke model
        model.addAttribute("iuran", iuranDetail); 
        model.addAttribute("testOrderId", orderId); // Hanya untuk pengujian
        
        // 3. Kembalikan Nama View
        // Controller akan mencari template Thymeleaf di /resources/templates/iuran/detail.html
        return "/management_iuran/iuran_kas/detail";
	}
}
