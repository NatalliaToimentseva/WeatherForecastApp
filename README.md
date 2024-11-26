# Weather Forecast (XML version)
An application for displaying the weather forecast for a day and for three days.<br />

Two fragments are displayed on one screen. The lower fragment is represented by a View Pager.<br />
It is possible to display the forecast based on user location or user input. The default location is Minsk.<br />

The application requests permissions and checks for the Internet and GPS.<br />

![WeatherForecastGen](https://github.com/user-attachments/assets/6be7614b-a03d-401b-b240-0c932cf389f5)

## Technology stack used in development:<br />
* language - Kotlin<br />
* Single activity app
* UI - XML <br />
* Architecture - Clean Architecture, UI layer - MVVM<br />
* Navigation - View Pager <br />
* Dependency injection - Hilt<br />
* Asynchrony - Сoroutines and Kotlin Flow<br />
* Network - Retrofit2, Picasso, parser - GSON<br />
* RecyclerView with 2 ViewHolder, ListAdapter, DiffUtil<br />
* Firebase: Crachlytics, Perfomance<br />
* Permission Internet, Fine_Location, Coars_Location, Access_Network_State <br />
* FusedLocationProviderClient, LocationManager, ConnectivityManager <br />
