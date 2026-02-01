package viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import conexion.AppDataBase
import conexion.ImagenRepository
import kotlinx.coroutines.launch
import model.Imagen

class ImagenViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ImagenRepository

    init {
        //Obtiene la instancia de la base de datos
        val dao = AppDataBase.getDatabase(application).imagenDAO()
        //Inicializa el repositorio
        repository = ImagenRepository(dao)
    }

    //Guardar una imagen en la base de datos
    fun insertarImagen(imagen: Imagen) = viewModelScope.launch {
        repository.insertarImagen(imagen)
    }
    //Para obtener las imagenes según el tipo que sean (perfil,carrusel,post)
    fun getImagenes(tipo: String): LiveData<List<Imagen>> {
        return repository.getImagenes(tipo)
    }
    //Para obtener las imagenes espeficas por el correo del usuario  y el tipo de imagen
    fun getImagenesPorEmailYTipo(email: String, tipo: String): LiveData<List<Imagen>> {
        return repository.getImagenesPorEmailYTipo(email, tipo)
    }

}
