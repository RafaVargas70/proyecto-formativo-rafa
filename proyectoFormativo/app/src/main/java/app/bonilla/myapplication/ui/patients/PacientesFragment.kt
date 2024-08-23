package rafa.vargas.myapplication.ui.patients

import Modelo.conexion
import Modelo.pacientes
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import rafa.vargas.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class PacientesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_nuevo_paciente, container, false)

        val txtNombre = root.findViewById<EditText>(R.id.txtNombre)
        val txtApellido = root.findViewById<EditText>(R.id.txtApellido)
        val txtEdad = root.findViewById<EditText>(R.id.txtEdad)
        val txtEnfermedad = root.findViewById<EditText>(R.id.txtEnfermedad)
        val txtMedicamentos = root.findViewById<EditText>(R.id.txtMedicamentos)
        val txtIngreso = root.findViewById<EditText>(R.id.txtIngreso)
        val txtHoraMedicamentos = root.findViewById<EditText>(R.id.txtHoraMedicamentos)
        val txtCuarto = root.findViewById<EditText>(R.id.txtCuarto)
        val txtCama = root.findViewById<EditText>(R.id.txtCama)
        val btnAgregarPaciente = root.findViewById<Button>(R.id.btnAgregarPaciente)


        fun clear()
        {
            txtNombre.setText("")
            txtApellido.setText("")
            txtEdad.setText("")
            txtEnfermedad.setText("")
            txtMedicamentos.setText("")
            txtIngreso.setText("")
            txtHoraMedicamentos.setText("")
            txtCuarto.setText("")
            txtCama.setText("")
        }

        fun obtenerPacientes(): List<pacientes> {
            val objCon = conexion().StringConection()
            val statement = objCon?.createStatement()
            val resulSet = statement?.executeQuery("SELECT * FROM Pacientes")!!

            val listaPacientes = mutableListOf<pacientes>()

            while (resulSet.next()) {
                val uuid = resulSet.getString("uuid")
                val nombre = resulSet.getString("nombre ")
                val apellido = resulSet.getString("apellido")
                val edad = resulSet.getInt("edad")
                val enfermedad = resulSet.getString("enfermedad")
                val medicamentos = resulSet.getString("medicamentos")
                val ingreso = resulSet.getString("AdmmissionDate")
                val horaMedicamentos = resulSet.getString("horaMedicamentos")
                val cuarto = resulSet.getInt("cuarto")
                val cama = resulSet.getInt("cama")

                val values = pacientes(uuid, nombre, apellido, edad, enfermedad, cuarto,cama,medicamentos,ingreso,horaMedicamentos)
                listaPacientes.add(values)
            }

            return listaPacientes
        }

        btnAgregarPaciente.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    var validation = true

                    if (txtNombre.text.isEmpty() || txtApellido.text.isEmpty() || txtEdad.text.isEmpty() || txtEnfermedad.text.isEmpty() || txtMedicamentos.text.isEmpty() || txtIngreso.text.isEmpty() || txtHoraMedicamentos.text.isEmpty()) {

                        withContext(Dispatchers.Main)
                        {
                            Toast.makeText(this@PacientesFragment.context, "Complete los campos", Toast.LENGTH_SHORT).show()
                            validation = false
                        }

                    } else {

                        if (txtNombre.text.contains("[0-9]".toRegex())) {
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(this@PacientesFragment.context, "El título no puede contener números", Toast.LENGTH_SHORT).show()
                                validation = false
                            }
                        }

                        if (txtApellido.text.contains("[0-9]".toRegex())) {
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(this@PacientesFragment.context, "El nombre del autor no puede contener números", Toast.LENGTH_SHORT).show()
                                validation = false
                            }
                        }

                        if (txtIngreso.text.matches(Regex("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}"))) {}
                        else
                        {
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(this@PacientesFragment.context, "La fecha de admisión es inválida", Toast.LENGTH_SHORT).show()
                                validation = false
                            }
                        }

                        if(txtHoraMedicamentos.text.matches(Regex("[0-9]{1,2}:[0-9]{2}"))){}
                        else{
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(this@PacientesFragment.context, "La hora de la medicina es inválida", Toast.LENGTH_SHORT).show()
                                validation = false
                            }
                        }

                        if (txtEdad.text.contains("[a-zA-Z]".toRegex())) {
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(this@PacientesFragment.context, "La edad no puede contener letras", Toast.LENGTH_SHORT).show()
                                validation = false
                            }
                        }

                        if (txtCama.text.contains("[a-zA-Z]".toRegex())) {
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(this@PacientesFragment.context, "El número de cama no puede contener letras", Toast.LENGTH_SHORT).show()
                                validation = false
                            }
                        }

                        if (txtCuarto.text.contains("[a-zA-Z]".toRegex())) {
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(this@PacientesFragment.context, "El número de cuarto no puede contener letras", Toast.LENGTH_SHORT).show()
                                validation = false
                            }
                        }

                    }
                    if (validation) {
                        GlobalScope.launch(Dispatchers.IO)
                        {
                            val objCon = conexion().StringConection()

                            val nuevoPaciente =
                                objCon?.prepareStatement("INSERT INTO Pacientes (uuid, nombre, apellido, edad, enfermedad, cuarto, cama, medicamentos, ingreso, horaMedicamentos) VALUES (?, ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?)")!!

                            nuevoPaciente.setString(1, UUID.randomUUID().toString())
                            nuevoPaciente.setString(2, txtNombre.text.toString())
                            nuevoPaciente.setString(3, txtApellido.text.toString())
                            nuevoPaciente.setString(4, txtEdad.text.toString())
                            nuevoPaciente.setString(5,  txtEnfermedad.text.toString())
                            nuevoPaciente.setString(6, txtCuarto.text.toString())
                            nuevoPaciente.setString(7, txtCama.text.toString())
                            nuevoPaciente.setString(8, txtMedicamentos.text.toString())
                            nuevoPaciente.setString(9, txtIngreso.text.toString())
                            nuevoPaciente.setString(10, txtHoraMedicamentos.text.toString())

                            nuevoPaciente.executeUpdate()

                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@PacientesFragment.context,
                                    "Se agregó el paciente correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            clear()
                        }
                    }

                }catch(e:Exception)
                {
                    println("Error: $e")
                }



            }
        }

        return root
    }
}