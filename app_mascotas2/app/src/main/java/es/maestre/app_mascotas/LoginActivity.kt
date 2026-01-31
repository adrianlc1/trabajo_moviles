package es.maestre.app_mascotas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import viewmodel.UsuarioViewModel
import es.maestre.app_mascotas.databinding.ActivityIniciarSesionBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIniciarSesionBinding
    val viewModel: UsuarioViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Crea los usuarios de prueba
        conexion.ImagenesProvider.crearUsuariosDePrueba(viewModel)
        binding.btnLogin.setOnClickListener {
            //Obtiene los textos de los campos de texto y los guarda en una variable
            val email = binding.etEmail.text.toString().trim()
            val contrasena = binding.etPassword.text.toString().trim()

            // Verifica si los campos están vacíos
            if (email.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_LONG).show()

            }
            //Comprueba en la base de datos si existe ese usuario
            viewModel.login(email, contrasena).observe(this) { usuario ->
                if (usuario != null) {
                    //Si existe, cambia de pantalla a el Inicio
                    val intent = Intent(this, InicioActivity::class.java)
                    //Se lleva estos datos a la otra pantalla
                    intent.putExtra("nombreUsuario", usuario.nombre)
                    intent.putExtra("EMAIL_LOGUEADO", usuario.email)
                    startActivity(intent)
                } else {
                    //Si la consulta falla (login)
                    Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_LONG).show()
                }
            }
        }
            //Para que te rediriga a la pantalla de registro cuando apretes al boton.
            binding.tvRegister.setOnClickListener {
                val intent = Intent(this, RegistroActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
