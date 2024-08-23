package RecyclerViewHelper

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rafa.vargas.myapplication.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val lblNombre = view.findViewById<TextView>(R.id.lblNombre)
    val btnEliminar = view.findViewById<ImageView>(R.id.btnEliminar)
    val btnEditar = view.findViewById<ImageView>(R.id.btnEditar)
    val btnVerInformacion = view.findViewById<Button>(R.id.informaciondelpa)
}
