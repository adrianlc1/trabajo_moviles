package model

import androidx. room. ColumnInfo
import androidx. room. Entity
import androidx. room. PrimaryKey

@Entity (tableName = "usuario")
data class Usuario (
    @PrimaryKey (autoGenerate = true) @ColumnInfo (name = "id_usuario") var
    idAbogado: Int = 0,
    @ColumnInfo (name = "nombre") var nombre: String,
    @ColumnInfo (name = "apellido") var apellido: String,
    @ColumnInfo (name = "contrasena") var contrasena: String,
    @ColumnInfo (name = "email") var email: String,
    @ColumnInfo (name = "nombremascota") var nombremascota: String,
    @ColumnInfo (name = "tipoanimal") var tipoanimal: String,
    @ColumnInfo (name = "personalidad")var personalidad: String? = null,
    @ColumnInfo (name = "habitosgustos")var habitosgustos: String? = null,
    @ColumnInfo (name = "rasgosFisicos")var rasgosFisicos: String? = null
    ): java.io. Serializable
