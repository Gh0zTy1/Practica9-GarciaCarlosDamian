package garcia.carlosdamian.practica9_garciacarlosdamian


import java.util.UUID

data class Tarea(
    var id: String = "",
    var titulo: String = "",
    var descripcion: String = ""
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "titulo" to titulo,
            "descripcion" to descripcion
        )
    }
}