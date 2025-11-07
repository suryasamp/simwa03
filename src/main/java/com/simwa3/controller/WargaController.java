package com.simwa3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.simwa3.dto.DTOWarga;
import com.simwa3.model.WargaModel;
import com.simwa3.repository.WargaRepository;
import com.simwa3.service.WargaService;

@Controller
@RequestMapping("/warga")
public class WargaController {

	@Autowired
	private WargaService wargaService;

	@Autowired
	private WargaRepository wargaRepo;

	@GetMapping
	public String index(Model model) {
		List<WargaModel> wargaList = wargaRepo.findAll();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Username: " + auth.getName());
		System.out.println("Authorities: " + auth.getAuthorities());
		
		WargaModel wargaLogin = wargaRepo.findByCodeWarga(auth.getName());
		
		model.addAttribute("wargaList", wargaList);
		model.addAttribute("wargaLogin", wargaLogin);
		return "warga/index"; // templates/page/index.html
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("title", "Create Name");
		return "warga/create";
	}

	@PostMapping("/store")
	public String store(@ModelAttribute WargaModel warga, @RequestParam("avatar") MultipartFile avatar,
			@RequestParam("uploadKK") MultipartFile uploadKK, Model model, RedirectAttributes redirectAttributes) {

		wargaService.storeDataWarga(warga, avatar, uploadKK);
		redirectAttributes.addFlashAttribute("message", "Berhasil menyimpan data warga: " + warga.getNamaWarga());
		return "redirect:/warga";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		WargaModel warga = wargaService.getWargaById(id);
		System.out.println("COntroller: blok = " + warga.getBlok());
		model.addAttribute("warga", warga);
		return "warga/edit";
	}

	@PostMapping("/update/{id}")
	public String update(@PathVariable Integer id, WargaModel warga, @RequestParam("avatar") MultipartFile avatar,
			@RequestParam("uploadKK") MultipartFile uploadKK, Model model, RedirectAttributes redirectAttributes) {

		System.out.println("Controller: Masuk Update");
		System.out.println("Controller: Role:"+warga.getRoleWarga());
		System.out.println("Controller: Jabatan:"+warga.getJabatan());
		warga.setId(id);
		wargaService.storeDataWarga(warga, avatar, uploadKK);
		redirectAttributes.addFlashAttribute("message", "Berhasil update data warga: " + warga.getNamaWarga());
		return "redirect:/warga";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

		WargaModel warga = wargaService.getWargaById(id);
		if (warga == null) {
			redirectAttributes.addFlashAttribute("message",
					"Gagal menghapus: Data warga dengan ID " + id + " tidak ditemukan.");
			return "redirect:/warga";
		}
		wargaService.deleteWargaById(id);
		String name = warga.getNamaWarga() != null ? warga.getNamaWarga() : "Warga ID " + id;
		redirectAttributes.addFlashAttribute("message", "Berhasil Delete data warga: " + name);
		return "redirect:/warga";
	}
}
