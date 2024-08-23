package RecyclerViewHelper

import Modelo.conexion
import Modelo.pacientes
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import rafa.vargas.myapplication.Expediente
import rafa.vargas.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Adaptador(private var Data: List<pacientes>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_pacientes, parent, false)

        return ViewHolder(view)
    }

    fun ActualizarPantalla(Nombre: String,
                     LastNombre: String,
                     Edad: Int,
                     Enfermedad: String,
                     NumCuarto: Int,
                     NumCama: Int,
                     Medicamentos: String,
                     ingreso: String,
                     horaMedicamentos: String,
                     UUID: String){
        val index = Data.indexOfFirst { it.uuid == UUID }
        Data[index].nombre = Nombre
        Data[index].apellido = LastNombre
        Data[index].edad = Edad
        Data[index].enfermedad = Enfermedad
        Data[index].cuarto = NumCuarto
        Data[index].cama = NumCama
        Data[index].medicamentos = Medicamentos
        Data[index].ingreso = ingreso
        Data[index].horaMedicamentos = horaMedicamentos
        notifyDataSetChanged()
    }

    fun UpdateList(newList: List<pacientes>)
    {
        Data = newList
        notifyDataSetChanged()
    }

    fun editar(
        Nombre: String,
        Apellido: String,
        Edad: Int,
        Enfermedad: String,
        NumCuarto: Int,
        NumCama: Int,
        Medicamentos: String,
        ingreso: String,
        horaMedicamentos: String,
        UUID: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val objCon = conexion().StringConection()

            val actuPacientes =
                objCon?.prepareStatement("UPDATE pacientes SET nombre= ?,  apellido = ?, edad = ?, enfermedad = ?, cuarto = ?, cama = ?, medicamentos = ?, ingreso = ?, horaMedicamentos = ?   WHERE uuid = ?")!!
            actuPacientes.setString(1, Nombre)
            actuPacientes.setString(2, Apellido)
            actuPacientes.setString(3, Edad.toString())
            actuPacientes.setString(4, Enfermedad)
            actuPacientes.setString(5, NumCuarto.toString())
            actuPacientes.setString(6, NumCama.toString())
            actuPacientes.setString(7, Medicamentos)
            actuPacientes.setString(8, ingreso)
            actuPacientes.setString(9, horaMedicamentos)
            actuPacientes.setString(10, UUID)

            actuPacientes.executeUpdate()

            withContext(Dispatchers.Main){
                ActualizarPantalla(
                    Nombre,
                    Apellido,
                    Edad,
                    Enfermedad,
                    NumCuarto,
                    NumCama,
                    Medicamentos,
                    ingreso,
                    horaMedicamentos,
                    UUID)
            }
        }
    }

        fun eliminar(Nombre: String, posicion: Int) {
            val dataList = Data.toMutableList()
            dataList.removeAt(posicion)

            GlobalScope.launch(Dispatchers.IO) {
                val objCon = conexion().StringConection()

                val borrarPaciente = objCon?.prepareStatement("DELETE FROM pacientes WHERE nombre = ?")!!
                borrarPaciente.setString(1, Nombre)
                borrarPaciente.executeUpdate()

                val commit = objCon.prepareStatement("commit")
                commit.executeUpdate()
            }

            Data = dataList.toList()

            notifyItemRemoved(posicion)
            notifyDataSetChanged()
        }

        override fun getItemCount() = Data.size

        override fun onBindViewHolder(holder: ViewHolder, posicion: Int) {

            val patient = Data[posicion]

            holder.lblNombre.text = patient.nombre

            holder.btnEliminar.setOnClickListener {

                val context = holder.itemView.context

                val builder = AlertDialog.Builder(context)
                builder.setMessage("¿Desea eliminar el paciente?")

                builder.setPositiveButton("Aceptar") { dialog, which ->
                    eliminar(patient.nombre, posicion)
                    Toast.makeText(context, "Paciente eliminado correctamente", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }

            holder.btnEditar.setOnClickListener {
                val context = holder.itemView.context

                val layout = LinearLayout(context)
                layout.orientation = LinearLayout.VERTICAL

                val txt1 = EditText(context)
                layout.addView(txt1)
                txt1.setText(patient.nombre )
                val txt2 = EditText(context)
                layout.addView(txt2)
                txt2.setText(patient.apellido)
                val txt3 = EditText(context)
                layout.addView(txt3)
                txt3.setText(patient.edad.toString())
                val txt4 = EditText(context)
                layout.addView(txt4)
                txt4.setText(patient.enfermedad)
                val txt5 = EditText(context)
                txt5.setText(patient.cuarto.toString())
                layout.addView(txt5)
                val txt6 = EditText(context);
                txt6.setText(patient.cama.toString())
                layout.addView(txt6)
                val txt7 = EditText(context)
                txt7.setText(patient.medicamentos)
                layout.addView(txt7)
                val txt8 = EditText(context)
                txt8.setText(patient.ingreso)
                layout.addView(txt8)
                val txt9 = EditText(context)
                txt9.setText(patient.horaMedicamentos)
                layout.addView(txt9)

                val uuid = patient.uuid

                val builder = AlertDialog.Builder(context)
                builder.setView(layout)
                builder.setTitle("Editar paciente")


                builder.setPositiveButton("Aceptar") { dialog, which ->

                    editar(txt1.text.toString(),txt2.text.toString(),txt3.text.toString().toInt(),txt4.text.toString(),txt5.text.toString().toInt(),txt6.text.toString().toInt(),txt7.text.toString(),txt8.text.toString(),txt9.text.toString(),uuid)
                    Toast.makeText(context, "Paciente editado correctamente", Toast.LENGTH_SHORT).show()

                }

                builder.setNegativeButton("Cancelar") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }

            holder.btnVerInformacion.setOnClickListener{

                    val context = holder.itemView.context

                    val Expediente = Intent(context,Expediente::class.java)
                    Expediente.putExtra("uuid", patient.uuid)
                    Expediente.putExtra("nombre", patient.nombre)
                    Expediente.putExtra("apellido", patient.apellido)
                    Expediente.putExtra("edad", patient.edad)
                    Expediente.putExtra("enfermedad", patient.enfermedad)
                    Expediente.putExtra("cuarto", patient.cuarto)
                    Expediente.putExtra("cama", patient.cama)
                    Expediente.putExtra("medicamentos", patient.medicamentos)
                    Expediente.putExtra("ingreso", patient.ingreso )
                    Expediente.putExtra("horaMedicamentos", patient.horaMedicamentos)

                    context.startActivity(Expediente)


            }
        }
}
