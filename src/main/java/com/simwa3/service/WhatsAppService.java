package com.simwa3.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    private final String API_URL = "https://api.fonnte.com/send"; // Contoh pakai Fonnte
//    private final String TOKEN = "Xx1DmFAoBeYJrZ21JhXYniMRqXp";
    private final String TOKEN = "7BRw7erSbBHe6qdV3Nb9";

    public void sendMessage(String nomorTujuan, String pesan) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", TOKEN);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            String body = "target=" + nomorTujuan + "&message=" + pesan;
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            System.out.println("WA Response Code: " + responseCode);
        } catch (Exception e) {
            System.out.println("Gagal kirim WA: " + e.getMessage());
        }
    }
}

