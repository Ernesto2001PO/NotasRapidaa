package com.example.notas_rapidas.UI.Activities


import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notas_rapidas.R
import com.example.notas_rapidas.models.Notas
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class AgregarNotaActivity : AppCompatActivity() {

    private lateinit var tituloEntrada: EditText
    private lateinit var descripcionEntrada: EditText
    private lateinit var btnGuardar: MaterialButton
    private var isEditMode = false  // Variable para indicar si estamos en modo edición
    private var notaPosition: Int = -1  // Almacenar la posición de la nota que estamos editando



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nota)

        tituloEntrada = findViewById(R.id.tituloEntrada)
        descripcionEntrada = findViewById(R.id.descripcionEntrada)
        btnGuardar = findViewById(R.id.btnGuardar)





        // Cargar los datos de la nota si se están editando
        cargarDatosNotaSiExisten()


        // Guardar la nota al hacer clic en el botón
        btnGuardar.setOnClickListener {
            guardarNota()
        }

    }



    private fun cargarDatosNotaSiExisten() {
        // Comprobar si se han pasado datos para editar la nota
        val titulo = intent.getStringExtra("nota_titulo")
        val descripcion = intent.getStringExtra("nota_descripcion")
        notaPosition = intent.getIntExtra("position", -1)

        // Si se recibieron los datos, estamos en modo edición
        if (titulo != null && descripcion != null) {
            tituloEntrada.setText(titulo)  // Cargar el título actual en el campo de texto
            descripcionEntrada.setText(descripcion)  // Cargar la descripción actual en el campo de texto
            isEditMode = true  // Cambiar a modo edición
        }
    }


    private fun guardarNota() {
        val titulo = tituloEntrada.text.toString()
        val descripcion = descripcionEntrada.text.toString()

        if (titulo.isNotEmpty() && descripcion.isNotEmpty()) {
            val sharedPreferences = getSharedPreferences("NotasPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            val gson = Gson()
            val json = sharedPreferences.getString("notas", null)
            val type = object : TypeToken<MutableList<Notas>>() {}.type
            val notasList: MutableList<Notas> =
                if (json == null) mutableListOf() else gson.fromJson(json, type)

            if (isEditMode && notaPosition != -1) {
                // Si estamos en modo edición, actualizar la nota existente
                notasList[notaPosition] = Notas(titulo, descripcion)
                Toast.makeText(this, "Nota actualizada", Toast.LENGTH_SHORT).show()
            } else {
                // Si no estamos en modo edición, guardar una nueva nota
                val nuevaNota = Notas(titulo, descripcion)
                notasList.add(nuevaNota)
                Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show()
            }

            // Guardar la lista actualizada de notas en SharedPreferences
            val jsonString = gson.toJson(notasList)
            editor.putString("notas", jsonString)
            editor.apply()

            // Finalizar la actividad
            finish()
        } else {
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}









