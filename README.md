# 🛒 Swipe Store - Android App (Jetpack Compose + Clean Architecture + Hilt)

A modern Android application built with **Jetpack Compose**, following **Clean Architecture**, and powered by **Dagger Hilt**, **Room**, and **WorkManager** for offline-first data handling.

This project is a solution to the **Swipe Android Assignment**, implementing both online and offline functionality with automatic synchronization.

---

## 🚀 Features

✅ Product listing fetched from the API  
✅ Add product via API (POST request)  
✅ Offline mode with local Room database  
✅ Automatic sync using WorkManager when internet is restored  
✅ Search products by name or type using Kotlin Flow  
✅ Shimmer loading effect while fetching data  
✅ Beautiful UI using Material 3 components  
✅ Clean Architecture (Data → Domain → Presentation)  
✅ Dependency Injection with Dagger Hilt  

---

## 🧱 Architecture Overview

This app is built using **MVVM** with **Clean Architecture** principles:

```
data/
 ├── local/          # Room Database (Dao, Entities, Database)
 ├── remote/         # Retrofit ApiService
 ├── repoImp/        # Repository Implementations
domain/
 ├── model/          # Business Models
 ├── repository/     # Repository Interfaces
 ├── connectivity/   # Connectivity Observer Interface
presentation/
 ├── viewModel/      # ViewModels using Hilt
 ├── ui/             # Compose Screens
 ├── components/     # Reusable UI Composables
 ├── navigation/     # Navigation Setup
di/
 ├── AppModule.kt    # Provides Dependencies
 └── MyApplication.kt # Hilt Application class
 ├── ProductionWorker # Worker Class

```

---

## ⚙️ Tech Stack

| Category | Libraries |
|-----------|------------|
| **Language** | Kotlin |
| **UI** | Jetpack Compose, Material 3 |
| **Architecture** | MVVM + Clean Architecture |
| **Dependency Injection** | Dagger Hilt |
| **Database** | Room |
| **Networking** | Retrofit + OkHttp |
| **Async / Coroutines** | Kotlin Coroutines + Flow |
| **Background Tasks** | WorkManager (with Hilt integration) |
| **Image Loading** | Coil |
| **Connectivity** | Custom ConnectivityObserver |
| **State Handling** | Sealed class Resource<T> |

---

## 🌐 APIs Used

**Base URL:**  
```
https://app.getswipe.in/api/public/
```

| Endpoint | Method | Description |
|-----------|---------|-------------|
| `/get` | GET | Fetch all products |
| `/add` | POST | Add new product (multipart/form-data) |

---

## 🧠 Offline Flow

```
1️⃣ User adds a product when offline
2️⃣ Product stored in Room (Pending table)
3️⃣ ConnectivityObserver detects internet restored
4️⃣ WorkManager auto-syncs data with API
5️⃣ On success, product removed from pending table
```

---

## 🧩 Major Components

- **ConnectivityObserver** → Detects internet status  
- **WorkManager + HiltWorker** → Uploads pending data  
- **ProductRepository** → Unified data source (API + Room)  
- **OfflineFuncRepo** → Handles pending product operations  
- **MainViewModel** → Business logic + UI state management  
- **Room Entities + Dao** → Local data caching  

---

## 📱 Screens

- **Home Screen:** Displays product list + Search bar + Shimmer loader  
- **Add Product Screen:** Add new product with image + Validation  
- **Pending Screen:** Shows unsynced offline products  
- **Search Screen:** Filter by name or product type  

---

## 🛠️ How to Run

1. Clone the repo  
   ```bash
   git clone https://github.com/Vishalkumar800/Swipe-Store.git
   ```
2. Open in **Android Studio (Giraffe or newer)**  
3. Build the project (Gradle will auto-download dependencies)  
4. Run on an emulator or physical device (min SDK 24)  

---

## 🧑‍💻 Author

**Vishal**  
📍 India  
💼 Android Developer | Jetpack Compose Enthusiast  
