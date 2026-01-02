import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("Auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}
