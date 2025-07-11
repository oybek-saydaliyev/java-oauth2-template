# Spring Boot OAuth2 + JWT Auth (Google)

## 🛠 Texnologiyalar:
- Spring Boot 3.x
- Spring Security (OAuth2 Client)
- JWT (jjwt)
- Google Cloud OAuth
- Swagger / Postman (test uchun)

---

## 🔧 Custom qilingan classlar:

| Class | Vazifasi |
|-------|----------|
| `CustomAuthorizationRequestResolver.java` | Googlega `prompt=select_account` qo‘shish uchun — foydalanuvchi har safar akkaunt tanlashini majbur qiladi |
| `SecurityConfig.java` | OAuth2 login sozlamalari, JWT bilan himoyalash, custom resolver ulash uchun |

---

## 🔐 Kirish URL’lari:

| Nomi | URL | Maqsad |
|------|-----|--------|
| Login boshlanishi | `/oauth2/authorization/google` | Google akkaunt tanlash oynasi ochiladi |
| Redirect URL | `/login/oauth2/code/google` | Google login bo‘lgandan keyin qaytadi |
| Swagger | `/swagger-ui.html` | API test qilish uchun |
| JWT bilan endpoint | har qanday `/api/...` | Token orqali test qilish mumkin |

---

## ☁️ Google Cloud’dan nimalar olinadi:

Google Console → [console.cloud.google.com](https://console.cloud.google.com)

1. **Client ID**  
2. **Client Secret**  
3. `Authorized redirect URI`:  
http://localhost:7070/login/oauth2/code/google


4. `JavaScript Origin`:  
http://localhost:7070

yaml
---

## 📦 Token ishlatish:

Foydalanuvchi login qilgach, server JWT qaytaradi:
