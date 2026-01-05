interface LoginRepository {
    suspend fun login(email: String, password: String): Result<LoginResponse>
}
