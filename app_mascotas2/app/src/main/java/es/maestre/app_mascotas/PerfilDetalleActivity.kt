package es.maestre.app_mascotas

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import es.maestre.app_mascotas.databinding.ActivityPerfilDetalleBinding
import viewmodel.AdaptadorCarrusel
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

class PerfilDetalleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilDetalleBinding
    private val imagenViewModel: ImagenViewModel by viewModels()
    private val usuarioViewModel: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Recuperamos el email
        val emailUsuario = intent.getStringExtra("EMAIL") ?: ""
        //Recuperamos la foto de perfil
        val fotoPerfilId = intent.getStringExtra("FOTO_URL")


        //Observamos el email del usuario que queremos ver
        usuarioViewModel.getUsuarioByEmail(emailUsuario).observe(this) { usuario ->
            if (usuario != null) {
                //Pone los datos en el textView
                binding.tvNombreDuenoDetalle.text = usuario.nombre + " " + usuario.apellido
                binding.tvNombreMascotaDetalle.text = usuario.nombremascota

                binding.tvPersonalidadDetalle.text = usuario.personalidad ?: "Sin definir"
                binding.tvHabitosDetalle.text = usuario.habitosgustos ?: "Sin definir"
                binding.tvRasgosDetalle.text = usuario.rasgosFisicos ?: "Sin definir"
            }
        }
        //Comprobamos si el ID de la foto de perfil no es nulo
        if (!fotoPerfilId.isNullOrEmpty()) {
            //Construye la url de la foto de google drive
            val urlCompleta = "https://drive.google.com/uc?export=download&id=$fotoPerfilId"
            Glide.with(this)
                .load(urlCompleta) //Indica la foto que se va a poner
                .circleCrop() // Le pone en forma circular
                .into(binding.ivPerfilUsuarioDetalle) //En que lugar se va a colocar la foto
        }
        //Llamamos a esa funcion
        setupCarruselPersonalizado(emailUsuario)
        //Cuando apretes el boton de volver te llevara al activity de inicio
        binding.btnCerrarDetalle.setOnClickListener {
            finish()
        }
    }

    private fun setupCarruselPersonalizado(email: String) {
        //Para configurar el carrusel de forma Horizontal
        binding.rvCarruselFotosDetalle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //Consulta el viewModel por la fotos de tipo carrusel y el correo que tenga el usuario
        imagenViewModel.getImagenesPorEmailYTipo(email, "carrusel").observe(this) { fotosFiltradas ->
            //Asigna al adaptador  la lista de fotos filtradas
            binding.rvCarruselFotosDetalle.adapter = AdaptadorCarrusel(fotosFiltradas.take(3))
        }
    }
}