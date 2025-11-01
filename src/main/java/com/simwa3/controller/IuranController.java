package com.simwa3.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.midtrans.httpclient.error.MidtransError;
import com.simwa3.model.IuranModel;
import com.simwa3.model.WargaModel;
import com.simwa3.repository.IuranRepository;
import com.simwa3.repository.WargaRepository;
import com.simwa3.service.IuranService;
import com.simwa3.service.MidtransService;

@Controller
@RequestMapping("/iuran")
public class IuranController {
	
	
	@Autowired
	private WargaRepository wargaRepo;
	
	@Autowired
	private IuranRepository iuranRepo;
	
	@Autowired
	private IuranService iuranService;
	

	@GetMapping
	public String index(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<IuranModel> listIuran = iuranRepo.findByCodeWargaOrderByUpdatedAtDesc(auth.getName());
		System.out.println("listIuran:"+listIuran);
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(new JavaTimeModule()); 
	    String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(listIuran);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("listIuran JSON:\n" + json);
		
		model.addAttribute("listIuran", listIuran);
		return "/iuran/index";
	}
	@GetMapping("/create")
	public String create(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		WargaModel wargaLogin = wargaRepo.findByCodeWarga(auth.getName());
		
		List<String> bulanList = List.of("Jan", "Feb", "Mar",
				"Apr", "Mei", "Jun", "Jul", "Agu", "Sep",
				"Okt", "Nov", "Des");
		
		System.out.println("wargaLogin:"+wargaLogin);
		model.addAttribute("midtransClientKey", MidtransService.getClientKey());
		model.addAttribute("bulanList", bulanList);
		model.addAttribute("wargaLogin", wargaLogin);
		return "iuran/create";
	}
	
	@PostMapping("/payment")
	@ResponseBody
    public Map<String, Object> handlePayment(@RequestBody Map<String, Object> payload) throws MidtransError {
    	
        String namaWarga = (String) payload.get("namaWarga");
        String codeWarga = (String) payload.get("codeWarga");
        String nomerHp = (String) payload.get("nomerHp");
        List<String> selectedBulan = (List<String>) payload.get("selectedBulan");
        Integer tahun = Integer.parseInt((String) payload.get("tahun"));
        Integer total = Integer.parseInt((String) payload.get("total"));

        System.out.println("=== Data diterima ===");
        System.out.println("Nama Warga: " + namaWarga);
        System.out.println("Kode Warga: " + codeWarga);
        System.out.println("Nomor HP: " + nomerHp);
        System.out.println("Bulan dipilih: " + selectedBulan);
        System.out.println("Bulan tahun: " + tahun);
        System.out.println("Total: " + total);
        System.out.println("=====================");
        
        String token =iuranService.prosesPembayaran(namaWarga, codeWarga, nomerHp, selectedBulan, tahun,  total);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
		return response;
    	
    }
    
	@GetMapping("/success")
	public String paymentSuccess(Model model,RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("successMessage", "Pembayaran Iuran Berhasil!");
		redirectAttributes.addFlashAttribute("errorMessage", null);
	    return "redirect:/iuran";
	}
	
	@GetMapping("/error")
	public String paymentError(Model model,RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("successMessage", null);
		redirectAttributes.addFlashAttribute("errorMessage", "Terjadi kesalahan dalam pembayaran!");
	    return "redirect:/iuran";
	}
	

	@PostMapping
	public String store(@RequestParam String name, Model model) {
		model.addAttribute("message", "Saved: " + name);
		return "redirect:/iuran";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("title", "Edit Name");
		model.addAttribute("id", id);
		return "iuran/edit";
	}

	@PostMapping("/update/{id}")
	public String update(@PathVariable Long id, @RequestParam String name) {
		// Update logic here
		return "redirect:/iuran";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		// Delete logic here
		return "redirect:/iuran";
	}
	
	
	
}