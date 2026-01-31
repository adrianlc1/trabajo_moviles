package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imagenes")
data class Imagen(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val url: String,
    val tipo: String,
    val emailPropietario: String
)