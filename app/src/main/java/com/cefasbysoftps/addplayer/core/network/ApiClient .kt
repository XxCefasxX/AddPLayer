import okhttp3.OkHttpClient

object ApiClient {

    private const val BASE_URL = "http://10.0.2.2/"
//    private const val BASE_URL = "https://testing.soft-ps.com/"
//    private const val BASE_URL = "http://192.168.1.74/"

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val reportApi: ReportApi by lazy{
        retrofit.create(ReportApi::class.java)
    }


    private val okHttpClient = if (true) {
        UnsafeOkHttpClient.getUnsafeOkHttpClient()
    } else {
        OkHttpClient()
    }

    private val retrofit by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                retrofit2.converter.gson.GsonConverterFactory.create()
            )
            .build()
    }
}
