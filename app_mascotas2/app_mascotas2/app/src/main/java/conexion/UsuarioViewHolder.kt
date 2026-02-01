package conexion

import es.maestre.app_mascotas.R
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // Referencia al TextView para el nombre del usuario
    val nombre: TextView = view.findViewById(R.id.tvNombre)

    // Referencia al ImageView para la foto de perfil
    val imagenPerfil: ImageView = view.findViewById(R.id.imgUsuario)

}