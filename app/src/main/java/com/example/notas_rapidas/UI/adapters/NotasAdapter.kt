package com.example.notas_rapidas.UI.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notas_rapidas.R
import com.example.notas_rapidas.models.Notas


class NotasAdapter(private val context: Context, private val notasList: List<Notas>) :
    RecyclerView.Adapter<NotasAdapter.NotasViewHolder>() {

    inner class NotasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleOutput: TextView = itemView.findViewById(R.id.titleoutput)
        val descriptionOutput: TextView = itemView.findViewById(R.id.descriptionoutput)
        val btnEliminarNota = itemView.findViewById<ImageButton>(R.id.btnEliminar)
        val btnOpenColorPicker = itemView.findViewById<ImageButton>(R.id.btnOpenColorPicker)


        fun bind(nota: Notas) {
            titleOutput.text = nota.titulo
            descriptionOutput.text = nota.descripcion


            itemView.setBackgroundColor(nota.color)  // Cambiar el color de fondo de la nota

            //Configurar los botones

            btnEliminarNota.setOnClickListener {
                (context as OnNotaClickListener).oneNoteDelete(nota, adapterPosition)
            }


            btnOpenColorPicker.setOnClickListener {
                (context as OnNotaClickListener).dialogColorPicker(nota)
            }

            itemView.setOnClickListener {
                (context as OnNotaClickListener).oneNoteEdit(nota)
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false)
        return NotasViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
        val nota = notasList[position]
        holder.titleOutput.text = nota.titulo
        holder.descriptionOutput.text = nota.descripcion
        holder.bind(nota)


    }

    override fun getItemCount(): Int {
        return notasList.size
    }


    public interface OnNotaClickListener {
        fun oneNoteDelete(nota: Notas, position: Int)
        fun oneNoteEdit(nota: Notas)
        fun dialogColorPicker(nota: Notas)
    }
}
