package conexion

import android.content.Context
import model.Imagen
import model.Usuario
import viewmodel.ImagenViewModel
import viewmodel.UsuarioViewModel

object ImagenesProvider {

    //Son los usuarios de prueba que se crearan al ejecutar el programa
    fun crearUsuariosDePrueba(usuarioViewModel: UsuarioViewModel) {

        val listaManual = listOf(
            Usuario(
                nombre = "Carlos",
                apellido = "Maestre",
                contrasena = "123",
                email = "carlos@test.com",
                nombremascota = "Tobi",
                tipoanimal = "gato"
            ),
            Usuario(
                nombre = "Lucía",
                apellido = "Gómez",
                contrasena = "123",
                email = "lucia@test.com",
                nombremascota = "Misi",
                tipoanimal = "perro"
            ),
            Usuario(
                nombre = "Marcos",
                apellido = "Pérez",
                contrasena = "123",
                email = "marcos@test.com",
                nombremascota = "Roco",
                tipoanimal = "pajaro"
            ),

        )

        //Aqui se esta insertando el usuario en la base de datos
        listaManual.forEach { usuario ->
            usuarioViewModel.insert(usuario)
        }
    }
    //Insertar las fotos del tipo de foto que sea (perfil,post,carrusel) y el correo que tenga para relacionarlo con ese usuario
    fun insertarFotoPorEmail(viewModel: ImagenViewModel, email: String,urlPerfil:String,urlPost: String, urlCarrusel: List<String> ) {

        val imgPerfil = Imagen(url = urlPerfil, tipo = "perfil", emailPropietario = email)
        viewModel.insertarImagen(imgPerfil)


        val imgMuro = Imagen(url = urlPost, tipo = "post", emailPropietario = email)
        viewModel.insertarImagen(imgMuro)


        for (idDrive in urlCarrusel) {
            val imgPost = Imagen(url = idDrive, tipo = "carrusel", emailPropietario = email)
            viewModel.insertarImagen(imgPost)
        }
    }
}
