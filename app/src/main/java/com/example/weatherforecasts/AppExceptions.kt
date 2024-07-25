package com.example.weatherforecasts

import java.io.IOException

open class AppExceptions (message: String?) : IOException(message)

class LoadDataException(message: String?) : AppExceptions(message)