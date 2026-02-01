package es.maestre.app_mascotas

import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import viewmodel.AdaptadorUsuario
import es.maestre.app_mascotas.databinding.ActivityInicioBinding
import model.Usuario
import viewmodel.AdaptadorPost
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

class InicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInicioBinding
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val imagenViewModel: ImagenViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Aqui es donde recupera el correo de la otra pantalla
        val miEmail = intent.getStringExtra("EMAIL_LOGUEADO") ?: ""

            val provider = conexion.ImagenesProvider
            //Para insertar las fotos de los usuarios de prueba
            provider.insertarFotoPorEmail(
                imagenViewModel,
                "carlos@test.com",
                "1X9ZlgrC2ewj2OKRPXdQ1ti95vO4Yb-oN",
                "16D3ww-6Zjtk6bjLczfF5lFlceiC6FJ21",
                listOf(
                    "1yjK8EQzjx0IV0YGa3CHmjJrEl4b8XaIr",
                    "1O0TCzBm51XvBYldRJfDY9853tXfmiPyX",
                    "16ZsXnusG5Y3mHm2cMoUjUJrljGfBzs8G"
                )
            )

            provider.insertarFotoPorEmail(
                imagenViewModel,
                "lucia@test.com",
                "1rQAJSRuDyW4ID_3DBQ_7LlCYo0CQUpDJ",
                "1D4p3LIn0cp-STJd8MsBDrkGLfMmb4dmO",
                listOf("1YhBZZLU48xuTtJaTcdXBQzx62Fgd2qXZ", "1D8ViXWthY28jPE-x9JDIfF7Sh4lc2DKy", "1UOmjHXaI3GYO8Sm_dWpDmeY_LNfs1Ifz")
            )
            provider.insertarFotoPorEmail(
                imagenViewModel,
                "marcos@test.com",
                "1980-V8WAjo6vKaNwE10n4MSgJpZHoyLI",
                "1JBAHWNopKF0HUokmTWXUsHVPkfjEYL9_",
                listOf("1WPB8pHI05DGZUwF9tLuj6J7MD5UoCCTp", "1cCUcxN6EUBal0fEsX4feaYXgRbJvwGK6", "14vE7NpZyMzXCAGN63Zf6rHeLVRlAiGGE")
            )

        initRecyclerView()
        observeUsuarios()
        observePosts()
        //Cuando haces clik en un boton del  menu
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            //Comprueba cual icono a pulsado
            when (item.itemId) {
                //Si el usuario a pulsado inicio
                R.id.nav_inicio -> {
                    true
                }
                //Si el usuario a pulsado Perfil
                R.id.nav_perfil -> {
                    //Cambia a la pantalla de Perfil
                    val intent = Intent(this, PerfilActivity::class.java)
                    //Lleva el email a otra pantalla
                    intent.putExtra("EMAIL_LOGUEADO", miEmail)
                    //Lanza la pantalla de Perfil
                    startActivity(intent)
                    true
                }
                //Si el usuario a pulsado Buscar
                R.id.nav_buscar -> {
                    //Cambia a la pantalla de Buscar
                    val intent = Intent(this, BuscarActivity::class.java)
                    //Lleva el email a otra pantalla
                    intent.putExtra("EMAIL_LOGUEADO", miEmail)
                    //Lanza la pantalla de Buscar
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun initRecyclerView() {
        //Se coloca el RecyclerView de forma Horizontal
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvUsuarios.layoutManager = manager

        //Lo que hace es que puedas deslizar el elemento
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvUsuarios)
        binding.rvPost.layoutManager = LinearLayoutManager(this)
    }

    private fun observeUsuarios() {
        val miEmail = intent.getStringExtra("EMAIL_LOGUEADO")
        //Es la lista de todos los usuarios que hay en la base de datos
        usuarioViewModel.data.observe(this) { listaUsuarios ->
            //Le asigna las fotos de perfil al usuario correspondiente
            imagenViewModel.getImagenes("perfil").observe(this) { listaFotos ->


                val listaSinMi = ArrayList<Usuario>() // Lista filtrada
                val emailsYaAgregados = ArrayList<String>() // Para evitar duplicados


                for (usuario in listaUsuarios) {
                    //Filtra para no mostrar el ususario con el que has iniciado y evita correos repetidos
                    if (usuario.email != miEmail && !emailsYaAgregados.contains(usuario.email)) {
                        listaSinMi.add(usuario)
                        emailsYaAgregados.add(usuario.email)
                    }
                }
                //Comprobar si la lista contiene datos
                if (listaSinMi.isNotEmpty()) {

                    val listalimitada = listaSinMi
                    //Le pasamos  por parametro la lista de personas (listalimitada) y la lista de sus fotos (listaFotos)
                    binding.rvUsuarios.adapter = AdaptadorUsuario(listalimitada, listaFotos)
                } else {
                    //Si la lista esta vacia
                    Toast.makeText(this, "No hay otros usuarios", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observePosts() {
        val miEmail = intent.getStringExtra("EMAIL_LOGUEADO")

        //Es la lista de todos los usuarios que hay en la base de datos
        usuarioViewModel.data.observe(this) { listaUsuarios ->
            //Le asigna las fotos de post al usuario correspondiente
            imagenViewModel.getImagenes("post").observe(this) { fotosPosts ->

                val postsLimpios = ArrayList<Usuario>() //Lista de los usuarios que saldran en los post
                val emailsVistos = ArrayList<String>() //Lista para comprobar que el usuario no salga repetido

                for (usuario in listaUsuarios) {
                    //Filtra para no mostrar el ususario con el que has iniciado y evita correos repetidos
                    if (usuario.email != miEmail && !emailsVistos.contains(usuario.email)) {
                        postsLimpios.add(usuario)
                        emailsVistos.add(usuario.email)
                    }
                }
                //Comprobar si la lista contiene datos
                if (postsLimpios.isNotEmpty()) {
                    // Le pasamos por parametro la lista de usuarios filtrada y la lista de fotos de posts
                    binding.rvPost.adapter = AdaptadorPost(postsLimpios, fotosPosts)
                } else {
                    //Si trae la lista vacia
                    Toast.makeText(this, "No hay publicaciones de otros usuarios", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


