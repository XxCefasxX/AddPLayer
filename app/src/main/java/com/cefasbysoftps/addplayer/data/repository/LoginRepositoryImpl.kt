class LoginRepositoryImpl(
    private val api: AuthApi
) : LoginRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Result<LoginResponse> {
        return try {
            val response = api.login(
                LoginRequest(email, password)
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
