class LoginRepositoryImpl(
    private val localDataSource: FakeUserLocalDataSource
) {

    fun login(username: String, password: String): User? {
        val isValid = localDataSource.validateCredentials(username, password)
        return if (isValid) {
            localDataSource.getUser(username)
        } else {
            null
        }
    }
}
