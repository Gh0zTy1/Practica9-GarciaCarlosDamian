package garcia.carlosdamian.practica9_garciacarlosdamian

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import garcia.carlosdamian.practica9_garciacarlosdamian.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TareaAdapter
    private lateinit var viewModel: TareaViewModel
    private var tareaEdit: Tarea? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TareaViewModel::class.java]

        setupRecyclerView()

        viewModel.listaTareas.observe(this) { tareas ->
            adapter.actualizarLista(tareas)
        }

        binding.btnAgregarTarea.setOnClickListener {
            val titulo = binding.etTitulo.text.toString()
            val descripcion = binding.etDescripcion.text.toString()

            if (validarCampos(titulo, descripcion)) {
                val tarea = Tarea(titulo = titulo, descripcion = descripcion)
                viewModel.agregarTareas(tarea)
                limpiarCampos()
            }
        }

        binding.btnActualizarTarea.setOnClickListener {
            val titulo = binding.etTitulo.text.toString()
            val descripcion = binding.etDescripcion.text.toString()

            if (validarCampos(titulo, descripcion)) {
                tareaEdit?.let {
                    it.titulo = titulo
                    it.descripcion = descripcion
                    viewModel.actualizarTareas(it)
                    limpiarCampos()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = TareaAdapter(emptyList(), ::borrarTarea, ::prepararActualizacion)
        binding.rvTareas.adapter = adapter
        binding.rvTareas.layoutManager = LinearLayoutManager(this)
    }

    private fun validarCampos(titulo: String, descripcion: String): Boolean {
        if (titulo.isBlank()) {
            binding.etTitulo.error = "El título es obligatorio"
            return false
        }
        if (descripcion.isBlank()) {
            binding.etDescripcion.error = "La descripción es obligatoria"
            return false
        }
        return true
    }

    private fun limpiarCampos() {
        binding.etTitulo.setText("")
        binding.etDescripcion.setText("")
        binding.etTitulo.error = null
        binding.etDescripcion.error = null
        tareaEdit = null
        // Cambiar visibilidad de botones
        binding.btnAgregarTarea.visibility = View.VISIBLE
        binding.btnActualizarTarea.visibility = View.GONE
    }

    private fun borrarTarea(id: String) {
        viewModel.borrarTareas(id)
        Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show()
    }

    private fun prepararActualizacion(tarea: Tarea) {
        tareaEdit = tarea
        binding.etTitulo.setText(tarea.titulo)
        binding.etDescripcion.setText(tarea.descripcion)
        // Cambiar visibilidad de botones
        binding.btnAgregarTarea.visibility = View.GONE
        binding.btnActualizarTarea.visibility = View.VISIBLE
    }
}
