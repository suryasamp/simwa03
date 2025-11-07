package com.simwa3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import com.simwa3.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        
            .authorizeHttpRequests(auth -> auth
                // halaman publik
                .requestMatchers("/login", "/css/**", "/js/**", "/img/**", "/", "/view/pdf/**", "/uploads/**",  "/view/pdf/**", "/midtrans/notification").permitAll()
                // halaman admin
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // sisanya butuh login
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/process-login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            // agar bisa tampil di iframe (PDF viewer)
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    // konfigurasi resource handler untuk folder upload
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = "file:///C:/simwa3/uploads/";
        registry.addResourceHandler("/uploads/**").addResourceLocations(uploadPath);
    }
    
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}
