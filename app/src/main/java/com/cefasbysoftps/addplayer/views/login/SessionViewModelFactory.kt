import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SessionViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dataStore = SessionDataStore(context)

        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {
            return SessionViewModel(dataStore) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}
