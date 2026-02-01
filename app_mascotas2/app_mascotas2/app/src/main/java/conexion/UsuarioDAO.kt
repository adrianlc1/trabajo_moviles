package conexion

import androidx. lifecycle. LiveData
import androidx. room. Dao
import androidx. room. Delete
import androidx. room. Insert
import androidx. room. Query
import androidx. room. Update
import model.Imagen
import model.Usuario


@Dao
interface UsuarioDAO {
    @Insert // Inserta un nuevo usuario
    suspend fun insert(usuario: Usuario)

    // Devuelve todos los usuarios
    @Query("SELECT * FROM usuario")
    fun getAllUsuarios(): LiveData<List<Usuario>>
    //Busca por el id
    @Query("SELECT * FROM usuario WHERE id_usuario =:id")
    fun getUsuarioById(id: Int): LiveData<Usuario>
    //Consulta si el correo y la contraseña existen en la base de datos
    @Query("SELECT * FROM usuario WHERE email = :email AND contrasena = :contrasena")
    fun login(email: String, contrasena: String) : LiveData<Usuario?>

    // Actualiza los datos de un usuario existente
    @Update
    suspend fun update(usuario: Usuario)

    // Elimina un usuario
    @Delete
    suspend fun delete(usuario: Usuario)
    // Filtra según el tipo de animal
    @Query("SELECT * FROM usuario WHERE tipoanimal = :tipo")
    fun buscarUsuariosPorAnimal(tipo: String): LiveData<List<Usuario>>

    // Obtiene un único usuario por email
    @Query("SELECT * FROM usuario WHERE email = :email LIMIT 1")
    fun getUsuarioByEmail(email: String): LiveData<Usuario>
}