package conexion

import androidx.lifecycle.LiveData
import model.Imagen

class ImagenRepository(
    private val imagenDao: ImagenDAO
) {

    // Llama al DAO para insertar la imagen
    suspend fun insertarImagen(imagen: Imagen) {
        imagenDao.insert(imagen)
    }

    // Llama al DAO para obtener las imagenes filtradas
    fun getImagenes(tipo: String): LiveData<List<Imagen>> {
        return imagenDao.getImagenesPorTipo(tipo)
    }

    // Llama al DAO para obtener la imagen por el email y tipo
    fun getImagenesPorEmailYTipo(email: String?, tipo: String): LiveData<List<Imagen>> {
        val emailSeguro = email ?: ""
        return imagenDao.getImagenesPorEmailYTipo(emailSeguro, tipo)
    }
}