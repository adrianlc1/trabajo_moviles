package conexion

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.maestre.app_mascotas.R

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    //Busca y guarda la referencia del TextView que es donde ira el nombre del usuario
    val tvUsuarioPost: TextView = view.findViewById(R.id.tvUsuarioPost)

    //Busca y guarda la referencia del ImagenView que es donde cargara la foto.
    val imgPost: ImageView = view.findViewById(R.id.imgPost)

}