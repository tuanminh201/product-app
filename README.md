# ProductApp – Android Application using Kotlin & Jetpack Compose

ProductApp is a modern Android application designed to showcase a list of products retrieved from a remote API. It allows users to view, filter, and favorite items through an intuitive and responsive UI.

## 📱 Application Features

### 1. Product Overview Screen
The main screen displays a list of products with the following features:
- Header section with title and subtitle loaded from API.
- Filter tabs: "All", "Available", "Favorited".
- Pull-to-refresh functionality.
- Dynamic product list using Jetpack Compose's `LazyColumn`.

![Overview](public/assets/screenshots/ProductOverview.png)

### 2. Product Details Screen
Detailed view of a selected product:
- Image, name, price, rating, release date.
- Short and long descriptions.
- Toggle button to add/remove from favorites.

![Details](public/assets/screenshots/ProductDetails.png)

### 3. Favorites Tab
Displays products marked as favorites.

![Favorites](public/assets/screenshots/Favourites.png)

### 4. Available Tab
Filters the list to show only products that are available.

![Available](public/assets/screenshots/Available.png)

---

## 🧱 Project Structure

```
com.example.productapp/
├── data/
│   └── ProductAPI.kt                # Retrofit API setup
├── model/
│   ├── HeaderResponse.kt
│   ├── Product.kt
│   └── ProductResponse.kt          # API data models
├── navigation/
│   ├── AppNavigation.kt            # Compose Navigation graph
│   └── Nav.kt                      # Screen route definitions
├── ui/
│   ├── components/                 # Reusable UI components
│   ├── screens/                    # ProductOverview and ProductDetails screens
│   └── theme/                      # App theme settings
├── viewmodel/
│   └── ProductViewmodel.kt         # ViewModel managing UI state
└── MainActivity.kt                 # Entry point, sets up Compose content
```

---

## 🛠 Technologies Used

- **Kotlin** – Core programming language
- **Jetpack Compose** – Modern declarative UI toolkit for Android
- **MVVM Architecture** – Separation of concerns using ViewModel
- **Retrofit** – HTTP client for making API requests to:
  ```
  https://app.check24.de/products-test.json
  ```
- **Coil** – Lightweight image loading for Jetpack Compose
- **Kotlin Coroutines + StateFlow** – Asynchronous state management

---

## 🔄 Core Functionality

- **Filter Management:** Filters are dynamically populated from the API and translated to English for UI.
- **Reactive UI:** All UI state is driven by `StateFlow` objects in the `ProductViewModel`.
- **Favorites:** Users can favorite/unfavorite products. Favorites are managed locally and shown in a dedicated tab.
- **Error Handling:** Simulated error mechanism to test error states, with retry logic in place.

---

## ⚠️ Limitations

- Product images are currently hardcoded (static URL), though image URLs are expected in actual implementation.
- Favorite products are not persisted beyond app lifecycle (in-memory only).

---

## ✅ Getting Started

1. Clone the repository.
2. Open in Android Studio.
3. Sync Gradle and run the app on an emulator or device.

---

## 📃 License

This project is provided for educational and demonstration purposes. Usage beyond this scope should include proper attribution.

