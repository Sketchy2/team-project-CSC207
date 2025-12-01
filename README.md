# Weather2Wear

A Weather & Wardrobe Recommendation Application  
*Team TUT0101-11 – CSC207 Team Project*

**Weather2Wear** is a smart application designed to help users decide what to wear based on real-time weather conditions. By integrating accurate weather forecasting with a personalized wardrobe manager, the app solves the daily problem of under-dressing or over-dressing for the weather.

**Core Features:**
*   **Weather Search:** Instantly fetch current weather conditions for any city worldwide.
*   **Intelligent Recommendations:** Receive outfit suggestions tailored to temperature, precipitation, and wind speed.
*   **Multi-Day Forecast:** Plan ahead with a 7-day weather forecast including daily highs, lows, and conditions.
*   **Wardrobe Management:** Save favorite outfits with their weather context and manage a list of favorite cities for quick access.

---

## 👥 Team Members & Responsibilities

| Member | User Story | Responsibility |
| :--- | :--- | :--- |
| **Parker** | UC1 | **View Current Weather:** Fetch and display real-time weather data (temp, humidity, wind) for a searched city. |
| **Carl** | UC2 | **Outfit Recommendation:** Generate specific clothing item suggestions based on weather thresholds. |
| **Elizabeth** | UC3 | **Favorite Locations:** Implement saving, loading, and persistent storage of favorite cities. |
| **Ellen** | UC4 | **Multi-Day Forecast:** Retrieve and display a 7-day weather forecast (integrated with UC1). |
| **Chengcheng** | UC5 | **Save Outfits:** Allow users to save recommended outfits with associated weather metadata. |
| **Mitchell** | UC6 | **Manage Saved Items:** Functionality to view, edit, and delete saved outfits and locations. |

---

## 📜 User Stories

### **UC1 – View Current Weather**
*As a user, I want to search for a city and see the current weather (temperature, humidity, wind speed, condition) so I know exactly what to expect before going outside.*

### **UC2 – Outfit Recommendation**
*As a user, I want the system to recommend an appropriate outfit (e.g., "Heavy winter coat" vs. "T-shirt") based on the current temperature and weather conditions.*

### **UC3 – Favorite Locations**
*As a user, I want to save my frequently visited cities so I can check their weather quickly without typing the name every time.*

### **UC4 – Multi-Day Forecast**
*As a user, I want to see a weekly forecast (daily highs, lows, precipitation) to plan my outfits for the upcoming week.*

### **UC5 – Save Favorite Outfits**
*As a user, I want to save a recommended outfit along with its weather profile and location so I can remember what worked well for specific conditions.*

### **UC6 – Manage Saved Items**
*As a user, I want to view my list of saved outfits and locations, and edit or delete them as my preferences change.*

---

## 🔌 APIs Used

We use the **Open-Meteo API** for all weather and location data. It requires no API key and provides high-precision data.

### 1. **Geocoding API**
*   **Endpoint:** `https://geocoding-api.open-meteo.com/v1/search`
*   **Purpose:** Converts a user-entered city name (e.g., "London") into geographic coordinates (latitude, longitude) and resolves the correct country code (e.g., "GB").

### 2. **Weather Forecast API**
*   **Endpoint:** `https://api.open-meteo.com/v1/forecast`
*   **Purpose:** Fetches detailed weather data using the coordinates from the Geocoding API.
*   **Data Points Used:**
    *   **Current:** `temperature_2m`, `apparent_temperature`, `relative_humidity_2m`, `wind_speed_10m`, `precipitation`, `weather_code`.
    *   **Daily (Forecast):** `temperature_2m_max`, `temperature_2m_min`, `precipitation_sum`, `wind_speed_10m_max`, `weather_code`.

---

## 🎬 Application Demo

### 🌤️ Search Weather & Get Outfit Recommendations (UC1 & UC2)
![Weather2Wear Demo](docs/gifs/demo.gif)

**Features demonstrated:**
- Entering a city name
- Fetching current weather data
- Displaying multi-day forecast
- Generating outfit recommendations based on:
    - temperature
    - precipitation
    - wind
    - seasonal/weather trends

---

### 💾 Saved Items Dashboard (UC3 & UC5)
![Saved Items Demo](docs/gifs/save_items.gif)

**Features demonstrated:**
- Saving favourite locations
- Loading all saved items
- Saving recommended outfits
- Editing or deleting outfits
- Viewing outfits with associated weather profiles and locations
