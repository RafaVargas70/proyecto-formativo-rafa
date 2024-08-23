package Modelo

import java.sql.DriverManager
import java.sql.Connection

class conexion {

    fun StringConection(): Connection?
    {
        try {
            val ip = "jdbc:oracle:thin:@172.20.10.2:1521:xe"
            val user = "SYSTEM"
            val password = "desarrollo"

            val strConnection = DriverManager.getConnection(ip,user,password)
            return  strConnection
        }
        catch (e:Exception)
        {
            println("Error: $e")
            return null
        }
    }
}