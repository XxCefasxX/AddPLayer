class LoginUseCase(
    private val repository: LoginRepositoryImpl
) {

    operator fun invoke(username: String, password: String): User? {
        return repository.login(username, password)
    }
}
