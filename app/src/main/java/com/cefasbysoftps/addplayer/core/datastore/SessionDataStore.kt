import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

val Context.sessionDataStore by preferencesDataStore(name = "session_prefs")

class SessionDataStore(private val context: Context) {


    private val KEY_USER_ID = intPreferencesKey("user_id")
    private val KEY_NAME = stringPreferencesKey("name")
    private val KEY_LAST_NAME = stringPreferencesKey("last_name")
    private val KEY_EMAIL = stringPreferencesKey("email")


    val user: Flow<User?> = context.sessionDataStore.data.map { prefs ->
        val id = prefs[KEY_USER_ID] ?: return@map null

        User(
            userID = id,
            name = prefs[KEY_NAME] ?: "",
            lastName = prefs[KEY_LAST_NAME] ?: "",
            email = prefs[KEY_EMAIL] ?: ""
        )
    }

    private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")

    val isLoggedIn: Flow<Boolean> = context.sessionDataStore.data.map { prefs ->
        prefs[KEY_IS_LOGGED_IN] ?: false
    }

    suspend fun saveUser(user: User) {
        context.sessionDataStore.edit { prefs ->
            prefs[KEY_USER_ID] = user.userID
            prefs[KEY_NAME] = user.name
            prefs[KEY_LAST_NAME] = user.lastName
            prefs[KEY_EMAIL] = user.email
        }
    }
    suspend fun clearSession() {
        context.sessionDataStore.edit { it.clear() }
    }

}