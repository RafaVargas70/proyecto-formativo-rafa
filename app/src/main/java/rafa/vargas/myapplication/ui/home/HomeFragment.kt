package rafa.vargas.myapplication.ui.home

import Modelo.conexion
import Modelo.pacientes
import RecyclerViewHelper.Adaptador
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rafa.vargas.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        fun getPatients(): List<pacientes> {
            val objCon = conexion().StringConection()
            val statement = objCon?.createStatement()
            val resulSet = statement?.executeQuery("SELECT * FROM pacientes")!!

            val listPatients = mutableListOf<pacientes>()

            while (resulSet.next()) {
                val uuid = resulSet.getString("uuid")
                val nombre = resulSet.getString("nombre")
                val apellido = resulSet.getString("apellido")
                val edad = resulSet.getInt("edad")
                val enfermedad = resulSet.getString("enfermedad")
                val cuarto = resulSet.getInt("cuarto")
                val cama = resulSet.getInt("cama")
                val medicamentos = resulSet.getString("medicamentos")
                val ingreso = resulSet.getString("ingreso")
                val horaMedicamentos = resulSet.getString("horaMedicamentos")

                val values = pacientes(uuid, nombre, apellido, edad, enfermedad, cuarto,cama,medicamentos,ingreso,horaMedicamentos)
                listPatients.add(values)
            }

            return listPatients
        }

        val rcvPacientes = root.findViewById<RecyclerView>(R.id.rcvPatients)
        rcvPacientes.layoutManager = LinearLayoutManager(this.context)

        GlobalScope.launch(Dispatchers.IO)
        {
            val patients = getPatients()
            withContext(Dispatchers.Main)
            {
                val adapter = Adaptador(patients)
                rcvPacientes.adapter = adapter
            }
        }
        return root
    }
}