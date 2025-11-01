package com.simwa3.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.midtrans.Midtrans;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;
import com.simwa3.model.IuranModel;
import com.simwa3.repository.IuranRepository;

@Service
public class IuranService {
	
    @Value("${midtrans.server-key}")
    private String serverKey;
    
    @Value("${midtrans.is_prodaction}")
    private Boolean is_prodaction;
	
    @Autowired
	private IuranRepository iuranRepo;
	
	public String prosesPembayaran(String namaWarga, String codeWarga, String nomerHp, List<String> selectedBulan, Integer tahun,  Integer total) throws MidtransError {
		Midtrans.serverKey = serverKey;
		Midtrans.isProduction = is_prodaction;
		
		String randomChar = UUID.randomUUID().toString().substring(0, 8);
		String orderId = codeWarga + "-" + randomChar;
		
		//Mapping Transaction Detail
        Map<String, Object> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", orderId);
        transactionDetails.put("gross_amount", total);
        
        //Mapping Customer Detail
        Map<String, Object> customerDetails = new HashMap<>();
        customerDetails.put("first_name", namaWarga);
        customerDetails.put("phone", nomerHp);
		
        //Mapping Item Detail
        long tarifPerBulan = 20000;
        int min = 10000000;
        int max = 99999999;
        int randomNumber = (int) (Math.random() * (max - min + 1) + min);
        String randomCode = String.valueOf(randomNumber);
        List<Map<String, Object>> itemDetails = new java.util.ArrayList<>();
        for (String bulanIndex : selectedBulan) {
        	Map<String, Object> item = new HashMap<>();
        	 item.put("id", codeWarga + "-" + randomCode);
             item.put("price", tarifPerBulan);
             item.put("quantity", 1);
             item.put("name", "Iuran " + bulanIndex + " " + tahun);
             itemDetails.add(item);
        }
        
        //Gabungkan Semua Mapping
        Map<String, Object> params = new HashMap<>();
        params.put("transaction_details", transactionDetails);
        params.put("customer_details", customerDetails);
        params.put("item_details", itemDetails);
        
        String transactionToken = SnapApi.createTransactionToken(params);
        
        // Simpan ke database
        try {
            IuranModel iuran = new IuranModel();
            iuran.setOrderId(orderId);
            iuran.setCodeWarga(codeWarga);
            iuran.setNamaWarga(namaWarga);
            iuran.setBulan(String.join(",", selectedBulan));
            iuran.setTahun(tahun);
            iuran.setStatus("pending");
            iuran.setTotal(Long.valueOf(total));
            iuranRepo.save(iuran);
		} catch (Exception e) {
			System.out.println("Error insert DB Iuran:"+e);
		}


        return transactionToken;
		
	}
	
    public void updateStatus(String orderId, String status, String paymentType) {
    	IuranModel transaksi = iuranRepo.findByOrderId(orderId);
        if (transaksi != null) {
            transaksi.setStatus(status);
            transaksi.setPaymentType(paymentType);
            iuranRepo.save(transaksi);
        }
    }

}
