package com.simwa3.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simwa3.model.SaldoModel;
import com.simwa3.repository.SaldoRepository;


@Service
public class SaldoService {
	
	@Autowired
	private SaldoRepository saldoRepo;
	
	
    public void tambahSaldoDariIuran(BigDecimal total) {
        SaldoModel saldo = saldoRepo.findCurrentSaldo();

        if (saldo == null) {
            saldo = new SaldoModel();
            saldo.setTotalSaldo(total);
        } else {
            saldo.setTotalSaldo(saldo.getTotalSaldo().add(total));
        }

        saldo.setUpdatedAt(LocalDateTime.now());
        saldoRepo.save(saldo);
    }
	
    public void kurangiSaldo(BigDecimal total) {
        SaldoModel saldo = saldoRepo.findCurrentSaldo();

        if (saldo != null) {
            if (saldo.getTotalSaldo().compareTo(total) >= 0) {
                saldo.setTotalSaldo(saldo.getTotalSaldo().subtract(total));
                saldo.setUpdatedAt(LocalDateTime.now());
                saldoRepo.save(saldo);
            } else {
                throw new RuntimeException("Saldo tidak mencukupi untuk pengeluaran ini.");
            }
        } else {
            throw new RuntimeException("Saldo belum tersedia.");
        }
    }
    

    public SaldoModel getSaldo() {
        return saldoRepo.findCurrentSaldo();
    }


}
