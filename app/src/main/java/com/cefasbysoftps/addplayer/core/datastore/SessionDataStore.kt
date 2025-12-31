import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

val Context.sessionDataStore by preferencesDataStore(name = "session_prefs")

class SessionDataStore(private val context: Context) {

    private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")

    val isLoggedIn: Flow<Boolean> = context.sessionDataStore.data.map { prefs ->
        prefs[KEY_IS_LOGGED_IN] ?: false
    }

    suspend fun setLoggedIn(value: Boolean) {
        context.sessionDataStore.edit { prefs ->
            prefs[KEY_IS_LOGGED_IN] = value
        }
    }
}