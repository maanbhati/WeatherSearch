# WeatherSearch
### A simple Weather Search app, performed the search by city name, using clean Architecture approach with MVVM.
The App is using the [Open Weather Api] for searching weather data by it's name.<br/>
Get Your [Api Key] for running the WeatherSearch, Assign your api key to APP_ID constant within WeatherSearch app.

### Build With 🏗️
- [Kotlin] - Programming language for Android
- [Hilt-Dagger] - Standard library to incorporate Dagger dependency injection into an Android application.
- [Retrofit] -  A type-safe HTTP client for Android and Kotlin.
- [Room] - SQLite object mapping library.
- [Coroutines] - For asynchronous calls
- [FlowData] - Data objects that notify views when the state changes.
- [ViewModel] - Stores UI-related data that isn't destroyed on UI changes.
- [ViewBinding] - Generates a binding class for activity xml layout file.
- [Compose UI] - Jetpack Compose UI framework for writing composable UI for Android app's.
- [Glide] - An image loading library for compose UI Android focused on smooth scrolling
- [MockK]- For Kotlin Unit testing
- [Modularization]- App is using network module as modularization approach in Android.
- [Clean Architecture]- App is using clean architecture approach as described in Android doc.

  [ViewModel]: <https://developer.android.com/topic/libraries/architecture/viewmodel>
  [Hilt-Dagger]: <https://dagger.dev/hilt/>
  [DataStore]: <https://developer.android.com/topic/libraries/architecture/datastore>
  [ViewBinding]: <https://developer.android.com/topic/libraries/view-binding>
  [Compose UI]: <https://developer.android.com/jetpack/androidx/releases/compose-ui>
  [FlowData]: <https://developer.android.com/kotlin/flow>
  [Retrofit]: <https://square.github.io/retrofit/>
  [ViewModel]: <https://developer.android.com/topic/libraries/architecture/viewmodel>
  [Glide]: <https://bumptech.github.io/glide/int/compose.html>
  [Kotlin]: <https://kotlinlang.org>
  [Coroutines]: <https://kotlinlang.org/docs/coroutines-overview.html>
  [MVVM (Model View View-Model)]: <https://developer.android.com/jetpack/guide#recommended-app-arch>
  [Open Weather Api]: <https://openweathermap.org/current>
  [Api Key]:  <https://home.openweathermap.org/api_keys>
  [Room]: <https://developer.android.com/training/data-storage/room/>
  [MockK]:  <https://mockk.io/>
  [Modularization]: <https://developer.android.com/topic/modularization>
  [Clean Architecture]: <https://developer.android.com/topic/architecture>

### Project Architecture

This app uses [MVVM (Model View View-Model)] architecture.

![alt text](https://github.com/maanbhati/WeatherSearch/blob/main/mvvm_architecture.png?raw=true)

### App Screen shot

Weather Search Screen:
![alt text](https://github.com/maanbhati/WeatherSearch/blob/main/Weather_data_for_searched_city.png?raw=true)
