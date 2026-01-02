class FakeUserLocalDataSource {

    private val fakeUser = mapOf(
        "admin" to "1234",
        "Luis" to "lsui1"
    )

    fun validateCredentials(username: String, password: String): Boolean {
        return fakeUser[username] == password
    }

//    fun getUser(username: String): User {
//        return User(
//            id = "1",
//            name = username
//        )
//    }
}
