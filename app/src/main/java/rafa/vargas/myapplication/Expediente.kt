package rafa.vargas.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class Expediente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_expediente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val lblUUID = findViewById<TextView>(R.id.lblExpedienteUUID)
        val lblNombre = findViewById<TextView>(R.id.lblExpedienteNombre)
        val lblApellidos = findViewById<TextView>(R.id.lblExpedienteApellidos)
        val lblEdad = findViewById<TextView>(R.id.lblExpedienteEdad)
        val lblEnfermedad = findViewById<TextView>(R.id.lblExpedienteEnfermedad)
        val lblCuarto = findViewById<TextView>(R.id.lblExpedienteCuarto)
        val lblCama = findViewById<TextView>(R.id.lblExpedienteCama)
        val lblMedicamentos = findViewById<TextView>(R.id.lblExpedienteMedicamentos)
        val lblAdmision = findViewById<TextView>(R.id.lblExpedienteAdmision)
        val lblHoraMedicamentos = findViewById<TextView>(R.id.lblExpedienteHoraMedicamentos)

        lblUUID.text = intent.getStringExtra("uuid")
        lblNombre.text = intent.getStringExtra("nombre")
        lblApellidos.text = intent.getStringExtra("apellido")
        lblEdad.text = intent.getIntExtra("edad",0).toString()
        lblEnfermedad.text = intent.getStringExtra("enfermedad")
        lblCuarto.text = intent.getIntExtra("cuarto",0).toString()
        lblCama.text = intent.getIntExtra("cama",0).toString()
        lblMedicamentos.text = intent.getStringExtra("medicamentos")
        lblAdmision.text = intent.getStringExtra("ingreso")
        lblHoraMedicamentos.text = intent.getStringExtra("horaMedicamentos")

    }
}