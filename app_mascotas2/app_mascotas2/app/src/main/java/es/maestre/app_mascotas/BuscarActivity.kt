package es.maestre.app_mascotas

import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import es.maestre.app_mascotas.databinding.ActivityBuscarBinding
import model.Imagen
import model.Usuario
import viewmodel.AdaptadorPost
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

class BuscarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuscarBinding
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val imagenViewModel: ImagenViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuscarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Aqui es donde recupera el correo de la otra pantalla
        val miEmail = intent.getStringExtra("EMAIL_LOGUEADO") ?: ""

        //Para que el RecyclerView lo muestre como lista Vertical
        binding.rvResultadosBusqueda.layoutManager = LinearLayoutManager(this)

        // Evento al pulsar el botón de buscar
        binding.btnBuscar.setOnClickListener {
            val texto = binding.etBuscar.text.toString() // Obtenemos lo que escribió en el campo de texto
            if (texto.isNotEmpty()) {
                hacerBusqueda(texto) // Si hay texto, ejecutamos la función de búsqueda
            } else {
                //Si no hay texto mostramos el mensaje
                Toast.makeText(this, "Escribe un animal", Toast.LENGTH_SHORT).show()
            }
        }
        //Cuando haces clik en un boton del  menu
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            //Comprueba cual icono a pulsado
            when (item.itemId) {
                //Si el usuario a pulsado inicio
                R.id.nav_inicio -> {
                    //Cambia a la pantalla de inicio
                    val intent = Intent(this, InicioActivity::class.java)
                    //Lleva el email a otra pantalla
                    intent.putExtra("EMAIL_LOGUEADO", miEmail)
                    //Lanza la pantalla de Inicio
                    startActivity(intent)
                    //Cierra la pantalla
                    finish()
                    true
                }
                //Si el usuario a pulsado Perfil
                R.id.nav_perfil -> {
                    //Cambia a la pantalla de perfil
                    val intent = Intent(this, PerfilActivity::class.java)
                    //Lleva el email a otra pantalla
                    intent.putExtra("EMAIL_LOGUEADO", miEmail)
                    //Lanza la pantalla de Perfil
                    startActivity(intent)
                    true
                }
                //Si el usuario a pulsado Buscar (no hara nada)
                R.id.nav_buscar -> {
                    true
                }
                else -> false
            }
        }
    }
    private fun hacerBusqueda(animal: String) {
        binding.rvResultadosBusqueda.adapter = null
        // Observamos todos los usuarios disponibles en la base de datos
        usuarioViewModel.data.observe(this) { listaUsuarios ->
            //Aqui sacamos las fotos que sean de tipo post para mostrarlas
            imagenViewModel.getImagenes("post").observe(this) { todasLasFotos ->
                val usuariosFiltrados = ArrayList<Usuario>() // Lista para guardar todos los usuarios
                val emailsProcesados = ArrayList<String>() //Lista para guardar los emails que ya hemos añadido
                for (u in listaUsuarios) {
                    //Pone el tipo de animal de en minuscula y quitando los espacios
                    val tipoMascotaEnBD = u.tipoanimal.lowercase().trim()

                    //Comprueba si el tipo de animal que se ha puesto en el campo de
                    // texto coincide con algun tipo de animal que este guardado en la base de datos
                    if(tipoMascotaEnBD.contains(animal)){
                        //Comprueba si el usuario no ha sido añadido antes
                        if (!emailsProcesados.contains(u.email)) {

                            usuariosFiltrados.add(u) //Lo añade a la lista
                            emailsProcesados.add(u.email) //Lo añade a la lista de email que ya se han añadido
                        }
                    }
                }
                // Comprueba que la lista no este vacia
                if (usuariosFiltrados.isNotEmpty()) {
                    //Asigna el adaptador con la lista de Usuarios y la lista de las fotos del tipo post
                    binding.rvResultadosBusqueda.adapter = AdaptadorPost(usuariosFiltrados, todasLasFotos)
                } else {
                    Toast.makeText(this, "No hay resultados", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


