# 🏠 SIMWA — Sistem Informasi Manajemen Warga

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.java.com/)
[![Spring MVC](https://img.shields.io/badge/Spring-MVC-brightgreen.svg)](https://spring.io/)
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-336791.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**SIMWA** adalah aplikasi berbasis **Spring MVC ** yang dirancang untuk membantu pengelolaan data warga dan pembayaran iuran secara **efisien, transparan, dan mudah digunakan**.  
Pembayaran iuran dapat dilakukan **secara manual**, baik melalui **transfer bank, tunai, maupun QRIS**, tanpa integrasi gateway eksternal.

---

## 🚀 Fitur Utama

- 👥 **Manajemen Data Warga (CRUD)**
- 💰 **Pencatatan Pembayaran Iuran Manual**
- 🧾 **Riwayat Iuran dan Status Pembayaran**
- 📊 **Dashboard Admin** untuk memantau total iuran & saldo kas
- ⚙️ **AJAX & jQuery** untuk tampilan interaktif dan dinamis
- 🧩 **Upload Bukti Pembayaran (opsional)**
- 🗃️ **Integrasi Database PostgreSQL**

---

## 🧱 Teknologi yang Digunakan

| Kategori | Teknologi |
|-----------|------------|
| **Backend** | Java, Spring MVC, Spring Boot |
| **Frontend** | HTML, CSS, Bootstrap, jQuery, AJAX |
| **Database** | PostgreSQL |
| **Version Control** | Git & GitHub |

---

## ⚙️ Cara Instalasi

1. **Clone repository**
   ```bash
   git clone https://github.com/suryasamp/simwa.git
   cd simwa
   ```

2. **Konfigurasi `application.properties`**
   ```properties
   # Konfigurasi Database
   spring.datasource.url=jdbc:postgresql://localhost:5432/simwa_db
   spring.datasource.username=postgres
   spring.datasource.password=your_password

   # Optional JPA settings
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

   # Path folder upload (bisa diubah sesuai server)
   file.upload-dir=C:/simwa3/uploads

   # Multipart file size limit
   spring.servlet.multipart.max-file-size=10MB
   spring.servlet.multipart.max-request-size=20MB
   ```

3. **Jalankan aplikasi**
   ```bash
   mvn spring-boot:run
   ```
   Lalu buka di browser: [http://localhost:8080](http://localhost:8080)

---

## 💳 Alur Pembayaran Manual

1. Warga melakukan pembayaran iuran **tunai**, **transfer**, atau **QRIS** ke kas RT.  
2. Admin atau bendahara dapat mencatat transaksi tersebut di menu **Manajemen Iuran**.  
3. Bukti pembayaran (jika ada) dapat diunggah untuk transparansi.  
4. Sistem akan otomatis menghitung total kas dan status iuran warga.

---

## 📸 Tampilan

| Login Page |
|-------------|
| ![Login](docs/img/login.png) |


---

## 🌟 Dukungan

Jika **SIMWA** membantu kamu membangun sistem pengelolaan kas warga atau administrasi RT/RW,  
beri ⭐ di repo ini — dukungan kecilmu membantu pengembangan berkelanjutan 🙌

---

### 🔗 Kontak
📧 Email: [surya.samp@gmail.com]  
📍 Developer: [Surya Adimulya Pratama](https://github.com/suryasamp)

---
