package com.simwa3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.simwa3.model.WargaModel;
import com.simwa3.repository.WargaRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private WargaRepository wargaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		WargaModel warga = wargaRepository.findByCodeWarga(username);
		if (warga == null) {
			throw new UsernameNotFoundException("Warga not found with code: " + username);
		}

		return User.builder().username(warga.getCodeWarga()).password(warga.getPasswordWarga())
				.roles(warga.getRoleWarga()) // contoh: ADMIN, USER, KETUA_RT, dsb
				.build();
	}
}
