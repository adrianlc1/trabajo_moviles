package es.maestre.app_mascotas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import es.maestre.app_mascotas.databinding.ActivityPerfilBinding
import model.Imagen
import model.Usuario
import viewmodel.AdaptadorCarrusel
import viewmodel.AdaptadorPost
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

class PerfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding

    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val imagenViewModel: ImagenViewModel by viewModels()

    private var usuarioSesion: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperamos el email que se pasó desde la pantalla de Login
        val emailLogueado = intent.getStringExtra("EMAIL_LOGUEADO") ?: ""

        // Para que el RecyclerView sea horizontal
        binding.rvCarruselFotos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //Observamos los datos del usuario buscando por su email
        usuarioViewModel.getUsuarioByEmail(emailLogueado).observe(this) { usuarioEncontrado ->
            if (usuarioEncontrado != null) {
                //Guardamos el usuario en la variable de la linea 27
                usuarioSesion = usuarioEncontrado
                //Ponemos los nombres en los campos de texto con el usuario correspondiente (linea 42)
                binding.tvNombreMascotaPerfil.text = usuarioEncontrado.nombremascota
                binding.tvNombreDuenoPerfil.text = usuarioEncontrado.nombre + " " + usuarioEncontrado.apellido

                //Comprueba si los rasgos estan vacios
                if (binding.etPersonalidad.text.isNullOrEmpty()) {
                    //Pone en los campos de texto lo que el usuario tiene guardado en la base de datos y si no tiene nada en la base de datos
                    // pone el campo de texto vacio.
                    binding.etPersonalidad.setText(usuarioEncontrado.personalidad ?: "")
                    binding.etHabitos.setText(usuarioEncontrado.habitosgustos ?: "")
                    binding.etRasgos.setText(usuarioEncontrado.rasgosFisicos ?: "")
                }
            }
        }
        //Cuando apretas el boton de  cerrar sesión vuelve a la pantalla del login
        binding.btnCerrarSesionTop.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnGuardarTodo.setOnClickListener {
            val usuarioParaActualizar = usuarioSesion
            if (usuarioParaActualizar != null) {
                //Actualiza los rasgos con lo que haya puesto el usuario en el campo de texto.
                usuarioParaActualizar.personalidad = binding.etPersonalidad.text.toString()
                usuarioParaActualizar.habitosgustos = binding.etHabitos.text.toString()
                usuarioParaActualizar.rasgosFisicos = binding.etRasgos.text.toString()

                //Llama al ViewModel para guardar los rasgos nuevos en la base de datos
                usuarioViewModel.update(usuarioParaActualizar)

                Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error: Usuario no cargado", Toast.LENGTH_SHORT).show()
            }
        }
        //Observa la foto de perfil
        imagenViewModel.getImagenes("perfil").observe(this) { listaFotos ->
            //Hace un bucle con todas las fotos de perfil
            for (foto in listaFotos) {
                // Busca la foto que pertenezca al usuario logueado
                if (foto.emailPropietario == emailLogueado) {
                    //Construye la url de la foto de google drive
                    val urlDrive = "https://docs.google.com/uc?export=view&id=" + foto.url
                    Glide.with(this)
                        .load(urlDrive) //Indica la foto que se va a poner
                        .circleCrop() // Le pone en forma circular
                        .into(binding.ivPerfilUsuario) //En que lugar se va a colocar la foto
                }
            }

        }
        //Observa las fotos del tipo carrusel
        imagenViewModel.getImagenes("carrusel").observe(this) { todasLasFotos ->
            //Aqui va a guardar las fotos que le pertenecen al usuario logueado
            val misFotos = ArrayList<Imagen>()
            // Hace un bucle con todas las fotos de carrusel
            for (foto in todasLasFotos) {
                // Busca la foto que pertenezca al usuario logueado
                if (foto.emailPropietario.trim() == emailLogueado.trim()) {
                    misFotos.add(foto) //Añade la foto a el ArrayList
                }
            }
            //Comprueba si la lista esta vacia
            if (misFotos.isNotEmpty()) {
                //Le pasa al Adaptador la lista de fotos de tipo carrusel de ese usuario y le pone limite de fotos a 3.
                binding.rvCarruselFotos.adapter = AdaptadorCarrusel(misFotos.take(3))
            }
        }
        //Guarda el email que tenia la pantalla anterior
        val miEmail = intent.getStringExtra("EMAIL_LOGUEADO")
        //Para detectar cual es el icono del menu
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            //Comprueba cual icono a pulsado
            when (item.itemId) {
                //Si el boton que se apretado es el de Inicio
                R.id.nav_inicio -> {
                    //Cambia a la pantalla de Inicio
                    val intent = Intent(this, InicioActivity::class.java)
                    //Lleva el email a otra pantalla
                    intent.putExtra("EMAIL_LOGUEADO", miEmail)
                    //Lanza la pantalla de Inicio
                    startActivity(intent)
                    finish()
                    true
                }
                //Si el boton que se apretado es el de Perfil
                R.id.nav_perfil -> {
                    true
                }
                //Si el boton que se apretado es el de Busqueda
                R.id.nav_buscar -> {
                    //Cambia a la pantalla de Busqueda
                    val intent = Intent(this, BuscarActivity::class.java)
                    //Lleva el email a otra pantalla
                    intent.putExtra("EMAIL_LOGUEADO", miEmail)
                    //Lanza la pantalla de Busqueda
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}









