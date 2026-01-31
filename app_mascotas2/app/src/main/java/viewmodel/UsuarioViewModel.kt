package viewmodel

import android.R.attr.data
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import conexion.AppDataBase
import conexion.UsuarioDAO
import conexion.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Imagen
import model.Usuario

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UsuarioRepository
    // Lista observable de todos los usuarios de la base de datos
    val data: LiveData<List<Usuario>>

    init {
        //Obtiene el DAO
        val UsuarioDAO = AppDataBase.getDatabase(application.applicationContext).usuarioDAO()
        //Inicializa con todos los usuarios registrados
        data = UsuarioDAO.getAllUsuarios()
        //Inicializa el repositorio
        repository = UsuarioRepository(UsuarioDAO)

    }
    // Devuelve la lista completa de usuarios
    private fun getAllUsuario(): LiveData<List<Usuario>>{
        return repository.getAllUsuarios()
    }
    //Busca un usuario especifico por el id del usuario
    fun getUsuarioById(id:Int): LiveData<Usuario>{
        return repository.getUsuarioById(id)
    }
    //Inserta el usuario en la base de datos
    fun insert(usuario: Usuario) = viewModelScope.launch {
        repository.insert(usuario)
    }
    //Actualiza el usuario en la base de datos
    fun update(usuario: Usuario) = viewModelScope.launch {
        repository.update(usuario)
    }
    //Borra un usuario de la base de datos
    fun delete(usuario:Usuario) = viewModelScope.launch {
        repository.delete(usuario)
    }
    //Consulta si la contraseña y el correo coinciden con algun usuario de la base de datos
    fun login(email:String, contrasena: String): LiveData<Usuario?>{
        return repository.login(email,contrasena)
    }
    //Filtra por el tipo (perro,etc..)
    fun buscarPosts(tipo: String): LiveData<List<Usuario>> {
        return repository.getPostsByAnimal(tipo)
    }
    //Obtiene los datos de un usuario usando su correo
    fun getUsuarioByEmail(email: String): LiveData<Usuario> {
        return repository.getUsuarioByEmail(email)
    }
}