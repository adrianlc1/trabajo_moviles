package viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import es.maestre.app_mascotas.R
import androidx.recyclerview.widget.RecyclerView
import conexion.UsuarioViewHolder
import model.Imagen
import model.Usuario
import com.bumptech.glide.Glide
class AdaptadorUsuario(private val usuarios: List<Usuario>,private val imagenes: List<Imagen>) :
    RecyclerView.Adapter<UsuarioViewHolder>() {

    private var data: List<Usuario>
    init {
        data = usuarios
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //Crea la vista usando  xml  y lo mete en el ViewHolder
        return UsuarioViewHolder(
            layoutInflater.inflate(R.layout.item_usuario, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        // Obtiene el usuario de la lista según la posición
        val usuario = usuarios[position]
        //Pone el nombre de usuario en el TextView que guarda en el ViewModel
        holder.nombre.text = usuario.nombre
        //La variable donde se va a guardar la url de la foto
        var idLimpio: String? = null
        //Busca la imagen que sea de tipo perfil y el correo del dueño a la que esta relacionada esa foto
        val imagenCorrecta = imagenes.find { foto ->
            foto.emailPropietario == usuario.email && foto.tipo == "perfil"
        }
        //Comprueba si hay imagenes para el usuario
        if (imagenCorrecta != null) {
            //Limpia la url (esto lo hago porque las imagenes no me cargaban)
            idLimpio = imagenCorrecta.url.substringBefore("/")
            //Construye la url de la foto de Google drive
            val driveUrl = "https://drive.google.com/uc?export=download&id=${idLimpio}"

            Glide.with(holder.itemView.context)
                .load(driveUrl) // La imagen que se va a poner
                .placeholder(R.drawable.ic_launcher_foreground) //Imagen que sale cuando esta cargando la foto
                .circleCrop() //Hace la foto redonda
                .into(holder.imagenPerfil) // En que sitio se va a poner
        } else {
            //Si el usuario  no tiene foto pondra una imagen por defecto
            holder.imagenPerfil.setImageResource(R.drawable.ic_launcher_foreground)
        }
        //Cuando tocamos un usuario de la lista (Esta en Inicio donde estan circulos de arriba con las fotos de los usuarios)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = android.content.Intent(context, es.maestre.app_mascotas.PerfilDetalleActivity::class.java)

            //Pasamos todos los datos del usuario a la siguiente pantalla
            intent.putExtra("NOMBRE_DUENO", usuario.nombre)
            intent.putExtra("NOMBRE_MASCOTA", usuario.nombremascota)
            intent.putExtra("EMAIL", usuario.email)
            intent.putExtra("ANIMAL", usuario.tipoanimal)
            intent.putExtra("FOTO_URL", idLimpio)
            //Arranca la nueva pantalla
            context.startActivity(intent)
        }
    }

    //Lo que hace es decirle al RecyclerView cuantos usuarios hay que poner
    override fun getItemCount(): Int {
        return data.size
    }
}
