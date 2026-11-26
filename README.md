# Jetpack Compose Learning

Small practice projects for learning Jetpack Compose. Each folder is a separate
Android Studio project — open the folder itself in Android Studio to run it.

| Project | What it covers |
|---------|----------------|
| [LoginRegisterBasicTextFieldNoParams](LoginRegisterBasicTextFieldNoParams) | Login and Register screens with `OutlinedTextField`, password toggle, and screen switching without Composable parameters |
| [LoginRegisterWithBasicTextField](LoginRegisterWithBasicTextField) | Login, Register and Home screens with custom `BasicTextField` inputs, validation, and a `Canvas` wave background |
| [WeatherApp](WeatherApp) | Weather and 7-day forecast from the Open-Meteo API with Retrofit, `ViewModel`, `StateFlow`, loading / error / retry states, city search and pull-to-refresh |
| [ProductListingApp](ProductListingApp) | Product shop with `LazyVerticalGrid`, Navigation Compose with arguments, search / category filter / sort, Coil images, and a cart shared across screens |

## LoginRegisterBasicTextFieldNoParams

A Login screen and a Register screen built with Jetpack Compose and Material 3.
[See the project details →](LoginRegisterBasicTextFieldNoParams)

| Login | Register |
|:-----:|:--------:|
| <img src="LoginRegisterBasicTextFieldNoParams/screenshots/login.png" width="260" alt="Login screen"> | <img src="LoginRegisterBasicTextFieldNoParams/screenshots/register.png" width="260" alt="Register screen"> |

## LoginRegisterWithBasicTextField

Login, Register and Home screens with custom text fields built from `BasicTextField`.
[See the project details →](LoginRegisterWithBasicTextField)

| Login | Register |
|:-----:|:--------:|
| <img src="LoginRegisterWithBasicTextField/screenshots/login.png" width="260" alt="Login screen"> | <img src="LoginRegisterWithBasicTextField/screenshots/register.png" width="260" alt="Register screen"> |

## WeatherApp

Current weather and a 7-day forecast for any city, loaded from the free Open-Meteo API.
[See the project details →](WeatherApp)

| Weather | Search | Error |
|:-------:|:------:|:-----:|
| <img src="WeatherApp/screenshots/weather.png" width="220" alt="Weather screen"> | <img src="WeatherApp/screenshots/search.png" width="220" alt="City search"> | <img src="WeatherApp/screenshots/error.png" width="220" alt="Error screen with Retry"> |

## ProductListingApp

A product catalogue from the free Fake Store API, with a detail screen and a shared cart.
[See the project details →](ProductListingApp)

| Products | Sort | Detail | Cart |
|:--------:|:----:|:------:|:----:|
| <img src="ProductListingApp/screenshots/products.png" width="200" alt="Product grid"> | <img src="ProductListingApp/screenshots/sort.png" width="200" alt="Sort menu"> | <img src="ProductListingApp/screenshots/detail.png" width="200" alt="Product detail"> | <img src="ProductListingApp/screenshots/cart.png" width="200" alt="Cart"> |
