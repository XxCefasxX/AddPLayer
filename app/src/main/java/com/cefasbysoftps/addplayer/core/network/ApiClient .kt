object ApiClient {

    private const val BASE_URL = "http://10.0.2.2/"

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    private val retrofit by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                retrofit2.converter.gson.GsonConverterFactory.create()
            )
            .build()
    }
}
