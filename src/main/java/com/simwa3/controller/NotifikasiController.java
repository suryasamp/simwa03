package com.simwa3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simwa3.model.NotifikasiModel;
import com.simwa3.repository.NotifikasiRepository;

@Controller
@RequestMapping("/notifikasi")
public class NotifikasiController {

    @Autowired
    private NotifikasiRepository notifikasiRepo;

    @GetMapping
    public String index(Model model) {
        List<NotifikasiModel> listNotif = notifikasiRepo.findByPenerimaOrderByCreatedAtDesc("bendahara");
        model.addAttribute("listNotif", listNotif);
        return "notifikasi/index";
    }

    @PostMapping("/baca/{id}")
    public String baca(@PathVariable Long id) {
        NotifikasiModel notif = notifikasiRepo.findById(id).orElse(null);
        if (notif != null) {
            notif.setDibaca(true);
            notifikasiRepo.save(notif);
        }
        return "redirect:/notifikasi";
    }
}

