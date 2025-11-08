package com.simwa3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simwa3.model.NotifikasiModel;
import com.simwa3.model.WargaModel;
import com.simwa3.repository.NotifikasiRepository;
import com.simwa3.repository.WargaRepository;


@ControllerAdvice
public class GlobalController {

    @Autowired
    private WargaRepository wargaRepository;
    
    @Autowired
    private NotifikasiRepository notifikasiRepo;

    @ModelAttribute("avatarPath")
    public void getAvatarPath(Model model, WargaModel wargaModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = authentication.getName();
            wargaModel = wargaRepository.findByCodeWarga(username);
            if (wargaModel != null) {
                String avatar = wargaModel.getAvatarPath();
                if (avatar != null && !avatar.isEmpty()) {
                    model.addAttribute("avatarPath", avatar);
                    return;
                }
            }
        }
        System.out.println("Avatar path loaded: " + model.getAttribute("avatarPath"));
        model.addAttribute("avatarPath");
        return;
    }
    
    @ModelAttribute
    public void globalAttributes(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
    		WargaModel wargaLogin = wargaRepository.findByCodeWarga(authentication.getName());
    		String jabatan = wargaLogin.getJabatan();
    		String penerima = jabatan;
    		Long jumlahNotifBaru = notifikasiRepo.countByPenerimaAndDibacaFalse(penerima);
    		List<NotifikasiModel> listNotifBaru = notifikasiRepo.findByPenerimaOrderByCreatedAtDesc(penerima).stream().limit(5).toList();
    		
    		model.addAttribute("jumlahNotifBaru", jumlahNotifBaru);
    		model.addAttribute("listNotifBaru", listNotifBaru);
    	}
    }
}
