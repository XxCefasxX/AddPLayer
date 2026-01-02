class LoginUseCase(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<LoginResponse> {
        return repository.login(email, password)
    }
}
