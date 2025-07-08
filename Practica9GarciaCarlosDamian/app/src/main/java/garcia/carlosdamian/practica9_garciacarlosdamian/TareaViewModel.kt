package garcia.carlosdamian.practica9_garciacarlosdamian

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class TareaViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _listaTareas = MutableLiveData<List<Tarea>>(emptyList())
    val listaTareas: LiveData<List<Tarea>> = _listaTareas

    init {
        obtenerTareas()
    }

    fun obtenerTareas() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultado = db.collection("tareas").get().await()
                val tareas = resultado.documents.mapNotNull {
                    val tarea = it.toObject(Tarea::class.java)
                    tarea?.id = it.id // Asigna el ID del documento a la tarea
                    tarea
                }
                _listaTareas.postValue(tareas)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun agregarTareas(tarea: Tarea) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Generamos un nuevo ID único para la tarea
                val newId = UUID.randomUUID().toString()
                tarea.id = newId
                db.collection("tareas").document(newId).set(tarea).await()

                // Actualizamos la lista local
                val listaActual = _listaTareas.value.orEmpty().toMutableList()
                listaActual.add(tarea)
                _listaTareas.postValue(listaActual)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun actualizarTareas(tarea: Tarea) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("tareas").document(tarea.id).update(tarea.toMap()).await()

                // Actualizamos la lista local
                val listaActual = _listaTareas.value.orEmpty().map {
                    if (it.id == tarea.id) tarea else it
                }
                _listaTareas.postValue(listaActual)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun borrarTareas(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("tareas").document(id).delete().await()

                // Actualizamos la lista local
                val listaActual = _listaTareas.value.orEmpty().filter { it.id != id }
                _listaTareas.postValue(listaActual)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}