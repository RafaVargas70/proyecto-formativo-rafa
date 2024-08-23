package Modelo

data class pacientes(
    val uuid: String,
    var nombre: String,
    var apellido: String,
    var edad: Int,
    var enfermedad: String,
    var cuarto: Int,
    var cama: Int,
    var medicamentos: String,
    var ingreso: String,
    var horaMedicamentos: String
)
