package com.example.notas_rapidas.UI.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notas_rapidas.R
import com.example.notas_rapidas.UI.adapters.NotasAdapter
import com.example.notas_rapidas.models.Notas
import com.google.android.material.button.MaterialButton
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), NotasAdapter.OnNotaClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var notasAdapter: NotasAdapter
    private var notasList = mutableListOf<Notas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        notasAdapter = NotasAdapter(this, notasList)
        recyclerView.adapter = notasAdapter

        clickListeners()
        cargarNotas()

    }

    private fun clickListeners() {
        val btnAgregarNotas = findViewById<MaterialButton>(R.id.btnAgregarNotas)
        btnAgregarNotas.setOnClickListener {
            startActivity(Intent(this, AgregarNotaActivity::class.java))
        }
    }
    //
    private fun cargarNotas() {
        val sharedPreferences = getSharedPreferences("NotasPrefs", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("notas", null)
        val type = object : TypeToken<MutableList<Notas>>() {}.type
        notasList = if (json == null) mutableListOf() else gson.fromJson(json, type)
        refrescarNotas()
    }

    // Método para refrescar las notas en el RecyclerView
    private fun refrescarNotas() {
        notasAdapter = NotasAdapter(this, notasList)
        recyclerView.adapter = notasAdapter
    }

    // Implementación del método para eliminar una nota
    override fun oneNoteDelete(nota: Notas, position: Int) {
        notasList.removeAt(position)
        notasAdapter.notifyItemRemoved(position)
        guardarNotas()
        Toast.makeText(this, "Nota eliminada", Toast.LENGTH_SHORT).show()
    }

    // Implementación del método para editar una nota
    override fun oneNoteEdit(nota: Notas) {
        val intent = Intent(this, AgregarNotaActivity::class.java)
        intent.putExtra("nota_titulo", nota.titulo)
        intent.putExtra("nota_descripcion", nota.descripcion)
        intent.putExtra("position", notasList.indexOf(nota))
        startActivity(intent)
    }

    override fun dialogColorPicker(nota: Notas) {
        openColorPickerDialog(nota)
    }

    // Guardar las notas en SharedPreferences
    private fun guardarNotas() {
        val sharedPreferences = getSharedPreferences("NotasPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonString = gson.toJson(notasList)
        editor.putString("notas", jsonString)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        cargarNotas()
    }
    // Método para abrir el diálogo de selección de color

    private fun openColorPickerDialog(nota: Notas) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_color_picker, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Selecciona un color")

        val alertDialog = dialogBuilder.create()

        // Manejar los clics en los botones de colores
        dialogView.findViewById<Button>(R.id.color1).setOnClickListener {
            nota.color = Color.parseColor("#FF0000")  // Rojo
            alertDialog.dismiss()

            // Notificar al adapter que la nota ha cambiado
            notasAdapter.notifyDataSetChanged()  // Refrescar el RecyclerView
            guardarNotas()  // Guardar las notas actualizadas
        }

        dialogView.findViewById<Button>(R.id.color2).setOnClickListener {
            nota.color = Color.parseColor("#00FF00")  // Verde
            alertDialog.dismiss()

            notasAdapter.notifyDataSetChanged()
            guardarNotas()
        }

        dialogView.findViewById<Button>(R.id.color3).setOnClickListener {
            nota.color = Color.parseColor("#0000FF")  // Azul
            alertDialog.dismiss()

            notasAdapter.notifyDataSetChanged()
            guardarNotas()
        }

        dialogView.findViewById<Button>(R.id.color4).setOnClickListener {
            nota.color = Color.parseColor("#FFFF00")  // Amarillo
            alertDialog.dismiss()

            notasAdapter.notifyDataSetChanged()
            guardarNotas()
        }
        dialogView.findViewById<Button>(R.id.color5).setOnClickListener {
            nota.color = Color.parseColor("#FF00FF")  // Morado
            alertDialog.dismiss()

            notasAdapter.notifyDataSetChanged()
            guardarNotas()
        }
        dialogView.findViewById<Button>(R.id.color6).setOnClickListener {
            nota.color = Color.parseColor("#00FFFF")  // Cyan
            alertDialog.dismiss()

            notasAdapter.notifyDataSetChanged()
            guardarNotas()
        }
        dialogView.findViewById<Button>(R.id.color7).setOnClickListener {
            nota.color = Color.parseColor("#FFA500")  // Naranja
            alertDialog.dismiss()

            notasAdapter.notifyDataSetChanged()
            guardarNotas()
        }
        dialogView.findViewById<Button>(R.id.color8).setOnClickListener {
            nota.color = Color.parseColor("#9557c2")  // Lila
            alertDialog.dismiss()

            notasAdapter.notifyDataSetChanged()
            guardarNotas()
        }
        dialogView.findViewById<Button>(R.id.color9).setOnClickListener {
            nota.color = Color.parseColor("#915e17")  // Cafe
            alertDialog.dismiss()

            notasAdapter.notifyDataSetChanged()
            guardarNotas()
        }
        dialogView.findViewById<Button>(R.id.color10).setOnClickListener {
            nota.color = Color.parseColor("#ffffff")  // blanco
            alertDialog.dismiss()

            notasAdapter.notifyDataSetChanged()
            guardarNotas()
        }

        alertDialog.show()
    }

}
