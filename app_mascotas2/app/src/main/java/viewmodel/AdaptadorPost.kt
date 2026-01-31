package viewmodel


import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import conexion.PostViewHolder
import conexion.UsuarioViewHolder
import es.maestre.app_mascotas.databinding.ActivityIniciarSesionBinding
import es.maestre.app_mascotas.databinding.ActivityInicioBinding
import es.maestre.app_mascotas.databinding.ItemPostBinding
import model.Imagen
import model.Usuario

class AdaptadorPost(private val usuarios: List<Usuario>, private val imagenes: List<Imagen>) :
    RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        return PostViewHolder(
            layoutInflater.inflate(es.maestre.app_mascotas.R.layout.item_post, parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        // Obtiene el usuario actual según la posición de la lista
        val usuario = usuarios.getOrNull(position)

        //Usa ViewBinding para acceder al componente del xml
        val binding = ItemPostBinding.bind(holder.itemView)
        //Configuracion de los botones de me gusta o no me gusta
        val chip = binding.chip

        chip.isChecked = false // El botón empieza desactivado
        chip.isChipIconVisible = true // Se muestra el icono principal

        chip.setOnClickListener {
            if (chip.isChecked) {

                chip.isChipIconVisible = false // Escondemos el otro icono.
                chip.isCheckedIconVisible = true // Mostramos el icono
            } else {

                chip.isChipIconVisible = true // Vuelve a mostrar el otro icono.
                chip.isCheckedIconVisible = false // Escondemos el icono
            }
        }
        // Si el usuario existe, pone su nombre en el TextView del post
        if (usuario != null) {
            holder.tvUsuarioPost.text = usuario.nombre
        } else {
            holder.tvUsuarioPost.text = "Usuario Anónimo"
        }
        // Busca dentro de la lista de imágenes aquella que pertenezca al email de este usuario
        //y tambien que sea post
        val imagenCorrecta = imagenes.find { foto ->
            foto.emailPropietario == usuario?.email && foto.tipo == "post"
        }


        if (imagenCorrecta != null) {
            //Construye la url de Google Drive
            val idDrive = imagenCorrecta.url
            val urlCompleta = "https://drive.google.com/uc?export=download&id=$idDrive"

            Glide.with(holder.itemView.context)
                .load(urlCompleta) //La imagen que se va a poner
                .centerCrop() //Que este centrada la imagen
                .into(holder.imgPost) //Lugar donde se va poner la imagen
        }
    }
    //Esto lo que hace es indicar el tamaño de la lista del post que es igual al total de usuarios
    override fun getItemCount(): Int = usuarios.size
}