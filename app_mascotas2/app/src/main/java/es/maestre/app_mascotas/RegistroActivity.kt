package es.maestre.app_mascotas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.maestre.app_mascotas.databinding.ActivityRegisterViewBinding
import model.Usuario
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterViewBinding
    private val viewModel: UsuarioViewModel by viewModels()

    private val imagenViewModel: ImagenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configuracion al hacer clic en el botón de registrar
        binding.btnRegistrar.setOnClickListener {
            //Coge los datos que se han puesto en el campo de texto y las guarda en variables
            val nombre = binding.etNombre.text.toString().trim()
            val apellido = binding.etApellido.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val mascota = binding.etMascota.text.toString().trim()
            //Obtiene el tipo de animal al seleccionarlo con el desplegable
            val tipoAnimal = binding.spinnerTipoAnimal.selectedItem.toString()

            //Comprueba que se han rellenado todos los campos y si estan vacios detiene la creacion del usuario
            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //Crea el objeto usuario con la información de antes
            val usuario = Usuario(
                nombre = nombre,
                apellido = apellido,
                email = email,
                contrasena = password,
                nombremascota = mascota,
                tipoanimal = tipoAnimal
            )

            //Llama al viewModel para guardar el usuario en la base de datos
            viewModel.insert(usuario)
            //Accede a ImagenesProvider
            val provider = conexion.ImagenesProvider
            //Dependiendo del tipo de animal que eligas guardara  en la base de datos unas fotos o otras (perfil,post,carrusel)
            when (tipoAnimal) {
                "Gato" -> {

                    provider.insertarFotoPorEmail(
                        imagenViewModel,
                        email,
                        "1JAJd6ghMD8T4qjIwrRQYwR1Jbz_LmgqW",
                        "16RAUAHOqDCoJ_5RLH8nGm6WnLj5hAKvi",
                        listOf("1Jv2yiblR4sNO8F4q9t7fKmL8nRzUZzhf", "1cEsx9qUvJkZmhKwJhQ9wWFj11rOsD8jl", "15wtEgKFqDo9fYwW07uca84krXUTa_b74")
                    )
                }

                "Perro" -> {
                    provider.insertarFotoPorEmail(
                        imagenViewModel,
                        email,
                        "1jiCL7p12ZzZh0-6AoImELCplrD4BI-4j",
                        "1yXo6VTPpg7MVv-qNCu5ZuC6WVM-Jv0za",
                        listOf("1YphqLdqxcfZaiLCkn6t1DMNtw86Ra4YP", "1MyteWduj6q83sMvcuuP61ZZCI1M_Esj0")
                    )
                }

                "Pájaro" -> {
                    provider.insertarFotoPorEmail(
                        imagenViewModel,
                        email,
                        "1Hwntn1crt80uD-c5zGq99pKzvGAeu4JN",
                        "1xHwp_EIiwm69z3Rzc8bRIPiwUFENkKFF",
                        listOf("1J-yECQPOdNfxUmtJEfx-wW8tKiFvMVwe","1tdnGfMEXAs6WBwyij1eNMyAF9XrxER1u")
                    )
                }
            }
            //Muestra el mensaje de exito
            Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()

            //Cambiamos de pantalla a la del Login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
    }
}
