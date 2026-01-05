import android.os.Message

data class LoginResponse(
  val success: Boolean,
    val user:User,
  val message: String
)
