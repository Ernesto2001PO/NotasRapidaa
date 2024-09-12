package com.example.notas_rapidas.models

import android.graphics.Color


data class Notas(
    var titulo: String = "",
    var descripcion: String = "",
    var color: Int = Color.WHITE  // Aquí defines el campo 'color', con valor inicial blanco


)
