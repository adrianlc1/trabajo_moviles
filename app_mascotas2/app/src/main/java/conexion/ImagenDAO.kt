package conexion

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.Imagen

@Dao
interface ImagenDAO {
    @Insert  // Insertar una imagen
    suspend fun insert(imagen: Imagen)

    //Obtiene las imagenes por el tipo (perfil, post, carrusel) y las guarda en un LiveData
    @Query("SELECT * FROM imagenes WHERE  tipo = :tipoImg")
    fun getImagenesPerfil(tipoImg: String): LiveData<List<Imagen>>

    //Filtra imagenes por el email del dueño y el tipo
    @Query("SELECT * FROM imagenes WHERE emailPropietario = :email AND tipo = :tipo")
    fun getImagenesPorEmailYTipo(email: String, tipo: String): LiveData<List<Imagen>>

    //Obtiene todas las imagenes que coinciden con el tipo (perfil, post, carrusel)
    @Query("SELECT * FROM imagenes WHERE tipo = :tipo")
    fun getImagenesPorTipo(tipo: String): LiveData<List<Imagen>>
}