package br.com.alunos.odontofast

import java.sql.Connection
import java.sql.DriverManager

object DatabaseHelper {

    private const val USERNAME = "rm554024@fiap.com.br"
    private const val PASSWORD = "180995"
    private const val URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL"

    fun getConnection(): Connection? {
        return try {
            DriverManager.getConnection(URL, USERNAME, PASSWORD)
        } catch (e: Exception) {
            e.printStackTrace()
            null // Retorna null em caso de erro
        }
    }
}
