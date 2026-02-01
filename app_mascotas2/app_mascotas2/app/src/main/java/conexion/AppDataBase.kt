package conexion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.Imagen
import model.Usuario
import kotlin.jvm.java

@Database(entities = [Usuario::class,Imagen::class], version = 4)
abstract class AppDataBase : RoomDatabase() {

    //Metodos abstractos para obtener los DAO de cada entidad
    abstract fun usuarioDAO(): UsuarioDAO
    abstract fun imagenDAO(): ImagenDAO
    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        // Para obtener la base de datos
        fun getDatabase(context: Context): AppDataBase {
            //Si instance no es nulo,lo devuelve, si no es nulo entra.
            return INSTANCE ?: synchronized(this) {
                // Construye la base de datos con el nombre "usuarios.db3"
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "usuarios.db3"
                )
                    .fallbackToDestructiveMigration() // Borra y recrea la DB si cambia la versión
                    .build()
                INSTANCE = instance //Guarda la instancia creada
                instance // Devuelve la instancia
            }
        }
    }
}