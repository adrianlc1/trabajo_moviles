package conexion

import androidx.lifecycle.LiveData
import model.Imagen
import model.Usuario

class UsuarioRepository(
    private val usuarioDAO: UsuarioDAO

    ) {

    //Llama al DAO para obtener todos los usuarios
    fun getAllUsuarios(): LiveData<List<Usuario>> {
        return usuarioDAO.getAllUsuarios()
    }
    //Llama al DAO para insertar
     suspend fun insert(usuario: Usuario) {
        usuarioDAO.insert(usuario)
    }

    //Llama al DAO para update
    suspend fun update(usuario: Usuario){
        usuarioDAO.update(usuario)
    }

    //Llama al DAO para delete
    suspend fun delete(usuario: Usuario){
        usuarioDAO.delete(usuario)
    }
    //Llama al DAO para buscar un usuario por su id
    fun getUsuarioById(id: Int): LiveData<Usuario>{
        return usuarioDAO.getUsuarioById(id)
    }
    //Llama al DAO para comprobar si el correo y la contraseña existe en la base de datos
    fun login(email: String, contrasena: String): LiveData<Usuario?> {
        return usuarioDAO.login(email, contrasena)
    }

    //Llama al DAO para obtener el usuario que tenga tipo de animal especifico
    fun getPostsByAnimal(tipo: String):  LiveData<List<Usuario>> {
        return usuarioDAO.buscarUsuariosPorAnimal(tipo)
    }

    //Llama al DAO para obtener el usuario mediante su correo
    fun getUsuarioByEmail(email: String): LiveData<Usuario> {
        return usuarioDAO.getUsuarioByEmail(email)
    }
}