package garcia.carlosdamian.practica9_garciacarlosdamian


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import garcia.carlosdamian.practica9_garciacarlosdamian.databinding.ActivityMainBinding


class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TareaAdapter
    private lateinit var viewModel: TareaViewModel
    var tareaEdit = Tarea()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TareaViewModel::class.java]

        viewModel.listaTareas.observe(this) { tareas ->
            setupRecyclerView(tareas)
        }

        binding.btnAgregarTarea.setOnClickListener {
            val tarea = Tarea(
                titulo = binding.etTitulo.text.toString(),
                descripcion = binding.etDescripcion.text.toString()
            )
            viewModel.agregarTareas(tarea)
            binding.etTitulo.setText("")
            binding.etDescripcion.setText("")
        }

        binding.btnActualizarTarea.setOnClickListener {
            tareaEdit.titulo = binding.etTitulo.text.toString()
            tareaEdit.descripcion = binding.etDescripcion.text.toString()
            viewModel.actualizarTareas(tareaEdit)
        }
    }

    fun setupRecyclerView(listaTareas: List<Tarea>){
        adapter = TareaAdapter(listaTareas, ::borrarTarea, ::actualizarTarea)
        binding.rvTareas.adapter = adapter
    }

    fun borrarTarea(id: String) {
        viewModel.borrarTareas(id)
    }

    fun actualizarTarea(tarea: Tarea) {
        tareaEdit = tarea
        binding.etTitulo.setText(tareaEdit.titulo)
        binding.etDescripcion.setText(tareaEdit.descripcion)
    }
}