package com.simwa3.controller;

import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simwa3.service.IuranService;

@RestController
@RequestMapping("/midtrans")
public class MidtransNotificationController {
	
    @Autowired
    private IuranService iuranService;
    
    @Value("${midtrans.server-key}")
    private String serverKey;

    @PostMapping("/notification")
    public String handleNotification(@RequestBody Map<String, Object> payload) {
        System.out.println("=== Notifikasi dari Midtrans ===");
        String orderId = (String) payload.get("order_id");
        String statusCode = (String) payload.get("status_code");
        String grossAmount = (String) payload.get("gross_amount");
        String receivedSignature = (String) payload.get("signature_key");
        String transactionStatus = (String) payload.get("transaction_status");
        String paymentType = (String) payload.get("payment_type");
        
        System.out.println("orderId:"+orderId);
        System.out.println("statusCode:"+statusCode);
        System.out.println("grossAmount:"+grossAmount);
        System.out.println("receivedSignature:"+receivedSignature);
        System.out.println("transactionStatus:"+transactionStatus);
        System.out.println("paymentType:"+paymentType);
        
        
        String sha512Hash = DigestUtils.sha512Hex(orderId+statusCode+grossAmount+serverKey);
        System.out.println("sha512Hash:"+sha512Hash);
        if(receivedSignature.equals(sha512Hash)) {
        	iuranService.updateStatus(orderId, transactionStatus, paymentType);
        	System.out.println("update data iuran berhasil");
        }else {
        	System.out.println("Invalid Signature Key");
        }
        

        System.out.println("=========================");
        return "OK";
    }

	

}
