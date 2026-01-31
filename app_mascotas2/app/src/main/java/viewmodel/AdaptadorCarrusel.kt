package viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.maestre.app_mascotas.R
import model.Imagen

class AdaptadorCarrusel(private val imagenes: List<Imagen>) :
    RecyclerView.Adapter<AdaptadorCarrusel.CarruselViewHolder>() {

    class CarruselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //Es el xml donde van  las fotos del carrusel (aqui solo es una foto)
        val imgFoto: ImageView = view.findViewById(R.id.imgFotoSola)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarruselViewHolder {
        //Este es el conjunto de fotos del carrusel que es el ReciclerView
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_galeria, parent, false)
        return CarruselViewHolder(vista)
    }

    override fun onBindViewHolder(holder: CarruselViewHolder, position: Int) {
        //Recorre la lista de fotos y pone la foto que toca segun el orden
        //Devuelve un objeto imagen
        val foto = imagenes[position]
        //Aqui llama a la url de google drive que tenga asignada esa foto
        val urlCompleta = "https://drive.google.com/uc?export=view&id=" + foto.url

        Glide.with(holder.itemView.context)
            .load(urlCompleta) //Aqui se pone la foto que se va a poner
            .centerCrop() //Que este centrada
            .into(holder.imgFoto) //Lugar donde va ir la foto en xml
    }

    override fun getItemCount(): Int = imagenes.size
}